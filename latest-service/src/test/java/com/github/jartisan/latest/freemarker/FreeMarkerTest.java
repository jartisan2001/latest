package com.github.jartisan.latest.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.github.jartisan.latest.dao.sonatype.entity.Favorite;
import com.github.jartisan.latest.dao.sonatype.mapper.FavoriteLogMapper;
import com.github.jartisan.latest.global.entity.GithubInfo;
import com.github.jartisan.latest.global.utils.dingding.DingtalkChatbotClient;
import com.github.jartisan.latest.global.utils.dingding.message.MarkdownMessage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;


@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest
public class FreeMarkerTest {
	private static Logger log = LoggerFactory.getLogger(FreeMarkerTest.class);
	//1. 获取spring 的freeMarkerConfigurer 配置类  
    @Autowired
    private FreeMarkerConfigurer freemarkerConfigurer;  
    //Freemarker 原生的配置类型  
    private Configuration freeMarkerCfg;  
    
    @Autowired
	private FavoriteLogMapper favoriteLogMapper;
    
    public static final String MAVEN_SONATYPE = "http://search.maven.org/solrsearch/select";
	public static final String CHATBOT_WEBHOOK_ADMIN = "https://oapi.dingtalk.com/robot/send?access_token=842a03578923ccec228ae89fd271cfeda9ead962e6df6fd21cf646eb544de91b";
	private DingtalkChatbotClient client = new DingtalkChatbotClient();
	
	
	@Test
	public void testText() throws Exception{
		Map<String, String> textMsgMap = new HashMap<>();
		textMsgMap.put("Content", "316");
		
        freeMarkerCfg = freemarkerConfigurer.getConfiguration();  
        //3. 加载模板  
        Template template = freeMarkerCfg.getTemplate("spring.freemarker.ftl");  
        //4. 构造数据对象   
        Map<String,Object> root = new HashMap<>();  
        root.put("name", "小明");  
        root.put("from", "张三");  
        //5. 输出   
        StringWriter writer = new StringWriter();  
        template.process(root, writer);  
        String content = writer.getBuffer().toString();  
        log.info("content:" + content);  
	}
	
	@Test
	public void testDingding() throws Exception{
		Map<String,Object> map = Maps.newHashMap();

		Favorite favorite = new Favorite();
		favorite.setName("fastjson");
		favorite.setLastVersion("1.0.1");
		favorite.setLanguage("Java");
		
		GithubInfo info = new GithubInfo();
		info.setStarCount(12801);
		info.setForkCount(35789);
		info.setWatcherCount(456);
		info.setCreatedDate(DateUtils.addDays(new Date(), -8));
		//info.setLastUpdated(DateUtils.addDays(new Date(), -1));
		info.setLastUpdated(DateUtils.addDays(favoriteLogMapper.selectByLastVersion(1).getCreatedDate(), -1));
		
		List<String> vlist = Lists.newArrayList("1.0.0","2.0.0");
		info.setReleases(vlist);
		
		map.put("favorite", favorite);
		map.put("github", info);
		
        freeMarkerCfg = freemarkerConfigurer.getConfiguration();  
        //3. 加载模板  
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
		 message.setTitle("Java News:");
		 message.add(content);
		 //message.add("\n\n");
        client.send(CHATBOT_WEBHOOK_ADMIN, message);
        log.info("content:" + content);  
	}
}
