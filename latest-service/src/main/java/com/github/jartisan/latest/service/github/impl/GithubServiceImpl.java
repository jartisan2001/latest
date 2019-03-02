package com.github.jartisan.latest.service.github.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.github.jartisan.latest.configuration.GitHubConfig;
import com.github.jartisan.latest.dao.sonatype.entity.Favorite;
import com.github.jartisan.latest.dao.sonatype.entity.FavoriteDaily;
import com.github.jartisan.latest.dao.sonatype.entity.Java;
import com.github.jartisan.latest.dao.sonatype.mapper.FavoriteDailyMapper;
import com.github.jartisan.latest.dao.sonatype.mapper.FavoriteLogMapper;
import com.github.jartisan.latest.dao.sonatype.mapper.FavoriteMapper;
import com.github.jartisan.latest.dao.sonatype.mapper.JavaMapper;
import com.github.jartisan.latest.dao.sonatype.query.LatestQuery;
import com.github.jartisan.latest.global.consts.Const;
import com.github.jartisan.latest.global.entity.Dependency;
import com.github.jartisan.latest.global.entity.GithubInfo;
import com.github.jartisan.latest.global.exception.BaseException;
import com.github.jartisan.latest.global.utils.GitUtil;
import com.github.jartisan.latest.global.utils.GithubUtil;
import com.github.jartisan.latest.global.utils.SonatypeUtil;
import com.github.jartisan.latest.service.github.GithubService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import freemarker.template.Configuration;
import freemarker.template.Template;

/***
 * 
 * @author jalen
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GithubServiceImpl implements GithubService {
	private static Logger log = LoggerFactory.getLogger(GithubServiceImpl.class);
	
	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;

	private Configuration freeMarkerCfg;
	
	@Autowired
	private GitHubConfig gitHubConfig;

	@Autowired
	private FavoriteLogMapper favoriteLogMapper;

	@Autowired
	private FavoriteDailyMapper favoriteDailyMapper;

	@Autowired
	private FavoriteMapper favoriteMapper;
	
	@Autowired
	private JavaMapper javaMapper;

	@Override
	public void everyDaily(String checkType) throws BaseException {
		StopWatch stopwatch = new StopWatch("每日统计_" + checkType);
		stopwatch.start("list_" + checkType);
		List<Favorite> favorites = favoriteMapper.listAll(checkType);
		stopwatch.stop();
		for (Favorite favorite : favorites) {
			stopwatch.start("list_" + favorite.getName());
			GithubInfo info = GithubUtil.getProjectInfo(favorite.getGithubApi(),gitHubConfig.getToken());
			FavoriteDaily daily = new FavoriteDaily();
			daily.setFavoriteId(favorite.getId());
			daily.setStarCount(info.getStarCount());
			daily.setWatcherCount(info.getWatcherCount());
			daily.setForkCount(info.getForkCount());
			favoriteDailyMapper.insert(daily);
			stopwatch.stop();

			//每天同步最新版本
			if(checkType.equals(Const.CHECK_TYPE_MAVEN)) {
				Java java = javaMapper.selectByFavoriteId(favorite.getId());
				Dependency dependency = SonatypeUtil.getLatestRelease(java.getGroupId(), java.getArtifactId());
				favorite.setLastVersion(dependency.getVersion());
				favoriteMapper.updateByPrimaryKey(favorite);
			}
			
			try {
				// github api rate limit
				log.info("github limit sleep 3 s....");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				log.error("sleep 3 s,{}", e);
			}
		}

		log.info("TaskCount : {} , TotalTimeSeconds : {} s", stopwatch.getTaskCount(), stopwatch.getTotalTimeSeconds());

	}

	@Override
	public void syncReadme() throws Exception{
		List<LatestQuery> latests = favoriteLogMapper.listLatestBeforeDay(Const.CHECK_TYPE_MAVEN,3);

		if(latests.isEmpty()) {
			log.info("当天的版本更新列表不存在");
			return;
		}
		Map<String, Object> root = Maps.newHashMap();

		root.put("latestList", latests);
		root.put("include", favoriteMapper.listAll(Const.CHECK_TYPE_MAVEN));
		//root.put("lastUpdated", new Date());

		freeMarkerCfg = freemarkerConfigurer.getConfiguration();

		Template template = null;
		template = freeMarkerCfg.getTemplate("github-readme.ftl");

		File dest = new File(gitHubConfig.getLocalRepoPath()+File.separator+gitHubConfig.getFileName());
		log.info("存在版本更新：{}",latests.toString());
		if (dest.exists()) {
			//先删除后创建
			log.info("正在删除历史文件：{}",gitHubConfig.getLocalRepoPath()+File.separator+gitHubConfig.getFileName());
			if(dest.delete()) {
				log.info("删除成功");
				Files.createParentDirs(dest);
				log.info("创建文件");
			}
			if(!dest.createNewFile()) {
				log.error("创建失败 Invalid file");
				 throw new IOException("创建失败 Invalid file");
			}
		}
		log.info("创建完成");
		Writer w = new OutputStreamWriter(new FileOutputStream(dest), "utf-8");
		template.process(root, w);
		
		log.info("从模板生成文件");
		GitUtil.pullToLocal(gitHubConfig.getUserName(),gitHubConfig.getPassWord(),gitHubConfig.getLocalRepoPath());
		
		List<String> list = Lists.newArrayList();
		list.add("README.md");
		GitUtil.commitToGitRepository(gitHubConfig.getUserName(),gitHubConfig.getPassWord(),gitHubConfig.getLocalRepoPath(), list, "update latest - "+DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

	}

}
