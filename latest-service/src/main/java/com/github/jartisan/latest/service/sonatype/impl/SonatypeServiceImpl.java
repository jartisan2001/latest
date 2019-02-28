package com.github.jartisan.latest.service.sonatype.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.github.jartisan.latest.configuration.GitHubConfig;
import com.github.jartisan.latest.dao.sonatype.entity.Checkinfo;
import com.github.jartisan.latest.dao.sonatype.entity.Favorite;
import com.github.jartisan.latest.dao.sonatype.entity.FavoriteLog;
import com.github.jartisan.latest.dao.sonatype.entity.Java;
import com.github.jartisan.latest.dao.sonatype.mapper.CheckinfoMapper;
import com.github.jartisan.latest.dao.sonatype.mapper.FavoriteLogMapper;
import com.github.jartisan.latest.dao.sonatype.mapper.FavoriteMapper;
import com.github.jartisan.latest.dao.sonatype.mapper.JavaMapper;
import com.github.jartisan.latest.global.consts.Const;
import com.github.jartisan.latest.global.entity.Dependency;
import com.github.jartisan.latest.global.entity.GithubInfo;
import com.github.jartisan.latest.global.enums.Language;
import com.github.jartisan.latest.global.exception.BaseException;
import com.github.jartisan.latest.global.utils.GithubUtil;
import com.github.jartisan.latest.global.utils.SonatypeUtil;
import com.github.jartisan.latest.global.utils.ThreadUtil;
import com.github.jartisan.latest.global.utils.dingding.DingtalkChatbotClient;
import com.github.jartisan.latest.global.utils.dingding.message.MarkdownMessage;
import com.github.jartisan.latest.service.sonatype.SonatypeService;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

/***
 * 
 * @author jalen
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SonatypeServiceImpl implements SonatypeService {
	
	@Autowired
    private FreeMarkerConfigurer freemarkerConfigurer;  
    /***
     * Freemarker 原生的配置类型  
     */
    private Configuration freeMarkerCfg;  
	
	private static Logger log = LoggerFactory.getLogger(SonatypeServiceImpl.class);
	private static Pattern p = Pattern.compile("\n");
	
	private static String  pattern = "yyyy-MM-dd";
	
	@Value("${dingding.token}")
	public String chatbotWebhookAdmin;
	
	private DingtalkChatbotClient client = new DingtalkChatbotClient();
	
	
	@Autowired
	private GitHubConfig gitHubConfig;
	
	@Autowired
	private FavoriteMapper favoriteMapper;
	
	@Autowired
	private CheckinfoMapper checkinfoMapper;
	
	@Autowired
	private FavoriteLogMapper favoriteLogMapper;
	
	@Autowired
	private JavaMapper javaMapper;

	@Override
	public List<Favorite> listAll(String checkType) throws BaseException {
		return favoriteMapper.listAll(checkType);
	}

	
	@Override
	public Favorite saveFavorite(Dependency dependency) throws BaseException {
		Favorite favorite = new Favorite();
		favorite.setName(dependency.getName());
		favorite.setLanguage(dependency.getLanguage());
		//默认
		favorite.setDeleted(0);
		//GithubInfo
		GithubInfo info =GithubUtil.getProjectInfo(dependency.getGithubApi(),gitHubConfig.getToken());
		favorite.setStarCount(info.getStarCount());
		favorite.setWatcherCount(info.getWatcherCount());
		favorite.setForkCount(info.getForkCount());
		//LastVersion
		favorite.setLastVersion(GithubUtil.getLastTag(dependency.getGithubApi(),gitHubConfig.getToken()));
		favorite.setCheckType(dependency.getCheckType());
		favorite.setGithubApi(dependency.getGithubApi());
		
		favoriteMapper.insert(favorite);
		log.info("saveFavorite : {}",favorite);
		if(Language.JAVA.getName().equals(dependency.getLanguage())) {
			Java java = new Java();
			java.setFavoriteId(favorite.getId());
			java.setArtifactId(dependency.getArtifactId());
			java.setGroupId(dependency.getGroupId());
			javaMapper.insert(java);
			log.info("saveFavorite : {}",java);
		}
		return favorite;
	}
	
	
	@Override
	public void discover(String checkType) throws BaseException {
		List<Favorite> likes =listAll(checkType);
		StopWatch stopwatch = new StopWatch("checklist");
		for(Favorite favorite : likes){
			stopwatch.start(favorite.getName());
			//MAVEN中央仓库 API 获取版本
			if(Const.CHECK_TYPE_MAVEN.equals(favorite.getCheckType())) {
				Java java = javaMapper.selectByFavoriteId(favorite.getId());
				List<Dependency> dependencys =SonatypeUtil.listReleases(java.getGroupId(), java.getArtifactId());
				if(dependencys.isEmpty()){
					log.error("SonatypeUtil.listReleases : {} , {}",java.getGroupId(), java.getArtifactId());
					stopwatch.stop();
					continue;
				}
				isUpadtedByMaven(favorite, dependencys);
			}
			//GITHUB API 获取版本
			if(Const.CHECK_TYPE_GITHUB.equals(favorite.getCheckType())) {
				Checkinfo checkinfo =checkinfoMapper.selectByFavoriteId(favorite.getId());
				String version ="unknow";
				if(null==checkinfo) {
					stopwatch.stop();
					continue;
				}
				switch (checkinfo.getCheckType()) {
				case Const.CHECK_GITHUB_TAG:
					version = GithubUtil.getLastTag(favorite.getGithubApi(),gitHubConfig.getToken());
					break;
				case Const.CHECK_GITHUB_RELEASE:
					version = GithubUtil.getLastRelease(favorite.getGithubApi(),gitHubConfig.getToken());	
					break;
				default:
					version = GithubUtil.getLastTag(favorite.getGithubApi(),gitHubConfig.getToken());
					break;
				}
				//判断是否更新
				isUpadtedByGithub(favorite,version);
			}
			log.info("github limit sleep 3 s....");
			ThreadUtil.safeSleep(3000);
			stopwatch.stop();
			
		}
		log.info("TaskCount : {} , TotalTimeSeconds : {} s",stopwatch.getTaskCount(),stopwatch.getTotalTimeSeconds());
	}
	
	
	private void isUpadtedByMaven(Favorite favorite, List<Dependency> dependencys) {
		
		List<Dependency> newDependcecys = newDependency(favorite, dependencys);
		
		//无更新
		if(newDependcecys.isEmpty()){
			log.info("[{}]->[无更新]->[当前版本:{}]",favorite.getName(),dependencys.get(0).getVersion());
		}else {
			boolean result = updated(favorite,newDependcecys);
			log.info("[{}]->[发现更新]->[当前版本:{}]-->[{}]",favorite.getName(),favorite.getLastVersion(),versionStr(newDependcecys));
			if(!result) {
				sendFailureMessage(favorite,versionStr(newDependcecys));
				log.error("{}_{} updated is failure !:",favorite.getName(),newDependcecys);
			}
		}
	}


	private List<Dependency> newDependency(Favorite favorite, List<Dependency> dependencys) {
		String today = DateFormatUtils.format(new Date(),pattern);
		List<Dependency> newDependency = Lists.newArrayList();
		for(Dependency dependency : dependencys) {
			//今天有更新
			if(today.equals(DateFormatUtils.format(dependency.getUpdatedTimestamp(), pattern))) {
				if(!newDependency.contains(dependency)) {
					newDependency.add(dependency);	
				}
			}
			List<String>  flogs = favoriteLogMapper.selectByVersions(favorite.getId());
			
			if(!flogs.contains(dependency.getVersion())) {
				if(!newDependency.contains(dependency)) {
					newDependency.add(dependency);	
				}
			}
		}
		return newDependency;
	}


	private void isUpadtedByGithub(Favorite favorite, String version) {
		
		List<Dependency> newDependencys = Lists.newArrayList();
		Dependency dependency = new Dependency();
		dependency.setVersion(version);
		newDependencys.add(dependency);
		
		//无更新
		if(Objects.equal(version, favorite.getLastVersion())){
			log.info("[{}]->[无更新]->[当前版本:{}]",favorite.getName(),version);
		}else {
			boolean result = updated(favorite,newDependencys);
			log.info("[{}]->[发现更新]->[当前版本:{}]-->[{}]",favorite.getName(),favorite.getLastVersion(),version);
			if(!result) {
				sendFailureMessage(favorite,version);
				log.error("{}_{} updated is failure !:",favorite.getName(),version);
			}
		}
	}
	
	boolean updated(Favorite favorite,List<Dependency> newDependencys) {
		boolean result = true;
		try {
			//获取上一次更新记录
			FavoriteLog lastLog = favoriteLogMapper.selectByLastVersion(favorite.getId());
			//获取GithubInfo
			GithubInfo githubInfo = GithubUtil.getProjectInfo(favorite.getGithubApi(),gitHubConfig.getToken());
			
			if(null==lastLog) {
				githubInfo.setLastUpdated(new Date());				
			}else {
				githubInfo.setLastUpdated(lastLog.getCreatedDate());	
			}
			
			List<String>  flogs = favoriteLogMapper.selectByVersions(favorite.getId());
			
			List<String> vlist = Lists.newArrayList();
			
			for(Dependency dependency : newDependencys) {
				if(!flogs.contains(dependency.getVersion())) {
					//插入日志
					FavoriteLog fl = new FavoriteLog();
					fl.setFavoriteId(favorite.getId());
					fl.setStarCount(githubInfo.getStarCount());
					fl.setWatcherCount(githubInfo.getWatcherCount());
					fl.setForkCount(githubInfo.getForkCount());
					fl.setVersion(dependency.getVersion());
					fl.setCreatedDate(dependency.getUpdatedTimestamp());
					favoriteLogMapper.insert(fl);
					vlist.add(dependency.getVersion());
				}
			}
			favorite.setStarCount(githubInfo.getStarCount());
			favorite.setWatcherCount(githubInfo.getWatcherCount());
			favorite.setForkCount(githubInfo.getForkCount());
			favorite.setLastVersion(newDependencys.get(0).getVersion());
			favorite.setModifieDate(new Date());
			favoriteMapper.updateByPrimaryKey(favorite);
			
			if(!vlist.isEmpty()) {
				githubInfo.setReleases(vlist);
				sendMessage(favorite,githubInfo);	
			}
		} catch (Exception e) {
			result = false;
			log.error("updated is failure {} :",e);
		}
		return result;
	}
	
	
	
	void sendMessage(Favorite favorite,GithubInfo githubInfo) throws Exception{
		Map<String,Object> map = Maps.newHashMap();
		map.put("favorite", favorite);
		map.put("github", githubInfo);
		freeMarkerCfg = freemarkerConfigurer.getConfiguration();  
        //3. 加载模板  
        Template template = null;
        try {
			template=freeMarkerCfg.getTemplate("favorite-"+favorite.getName()+".ftl");
		} catch (TemplateNotFoundException e) {
			log.warn("未找到定制模版"+"favorite-"+favorite.getName()+".ftl");
		} catch (MalformedTemplateNameException e) {
			log.warn("未找到定制模版"+"favorite-"+favorite.getName()+".ftl");
		} catch (ParseException e) {
			log.warn("未找到定制模版"+"favorite-"+favorite.getName()+".ftl");
		} catch (IOException e) {
			log.warn("未找到定制模版"+"favorite-"+favorite.getName()+".ftl");
		}
        //用通用模版
        if(null==template) {
        	 template = freeMarkerCfg.getTemplate("favorite-general.ftl");  
        }
        //5. 输出   
        StringWriter writer = new StringWriter();  
        template.process(map, writer);  
        String content = writer.getBuffer().toString(); 
        
		MarkdownMessage message = new MarkdownMessage();
		 message.setTitle("Github News:");
		 message.add(content);
		 //message.add("\n\n");
        client.send(chatbotWebhookAdmin, message);
		
	}
	
	void sendFailureMessage(Favorite favorite,String versionStr){
		MarkdownMessage message = new MarkdownMessage();
		 message.setTitle("Github News:");
		 message.add(favorite.getName()+"_"+versionStr+"_ 失败");
		 //message.add("\n\n");
        client.send(chatbotWebhookAdmin, message);
	}
	
	
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static String versionStr(List<Dependency> dependencys) {
		return String.join(",", dependencys.stream().map(x -> x.getVersion()).collect(Collectors.toList()));
	}

}
