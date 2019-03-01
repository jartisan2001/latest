package com.github.jartisan.latest.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.github.jartisan.latest.dao.sonatype.mapper.FavoriteLogMapper;
import com.github.jartisan.latest.dao.sonatype.mapper.FavoriteMapper;
import com.github.jartisan.latest.dao.sonatype.query.LatestQuery;
import com.github.jartisan.latest.global.consts.Const;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import freemarker.template.Configuration;
import freemarker.template.Template;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FileTest {

	private static Logger log = LoggerFactory.getLogger(FileTest.class);
	
	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;
	
	private Configuration freeMarkerCfg;
	
	@Autowired
	private FavoriteMapper favoriteMapper;
	
	@Autowired
	private FavoriteLogMapper favoriteLogMapper;
	
	
	@Test
	public void freeMarkerContent() throws Exception{
		List<LatestQuery> latests =favoriteLogMapper.listLatestByToday(Const.CHECK_TYPE_MAVEN);
		
		Map<String,Object> root = Maps.newHashMap();
		
		root.put("latestList", latests);
		root.put("include", favoriteMapper.listAll(Const.CHECK_TYPE_MAVEN));
		root.put("lastUpdated", new Date());
		
		freeMarkerCfg = freemarkerConfigurer.getConfiguration();
		
		Template template = freeMarkerCfg.getTemplate("github-readme.ftl");

		String fileName = "." + File.separator + "README"+"-"+DateFormatUtils.format(new Date(), "yyyyMMdd");
		
		File dest = new File(fileName + ".md");
		if (!dest.exists()) {
			log.info("该文件不存在,创建文件{}",dest);
			Files.createParentDirs(dest);
			dest.createNewFile();
			log.info("创建完成");
		}
		
        //Writer file = new FileWriter(dest);
		Writer file = new OutputStreamWriter(new FileOutputStream(dest), "utf-8");
		template.process(root, file);
		
		
	}
}
