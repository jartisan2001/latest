package com.github.jartisan.latest.service.sonatype;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.jartisan.latest.dao.sonatype.entity.Favorite;
import com.github.jartisan.latest.global.consts.Const;
import com.github.jartisan.latest.scheduler.SchedulerTask;
@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest
public class SonatypeServiceTest {
	
	private static Logger log = LoggerFactory.getLogger(SonatypeServiceTest.class);
	
    @Autowired
    private SonatypeService sonatypeService;
    
    @Autowired
    private SchedulerTask schedulerTask;

	@Test
	public void testListAll() {
		List<Favorite> list =sonatypeService.listAll(Const.CHECK_TYPE_MAVEN);
		 log.info("list:{}" + list);  
	}

	@Test
	public void testDiscoverByMaven() {
		sonatypeService.discover(Const.CHECK_TYPE_MAVEN);
		log.info("end");
	}
	
	@Test
	public void testDiscoverByGithub() {
		sonatypeService.discover(Const.CHECK_TYPE_GITHUB);
		log.info("end");
	}

	@Test
	public void testSaveFavorite() {
		schedulerTask.everyDailyTask();
	}

}
