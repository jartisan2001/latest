package com.github.jartisan.latest.dao.favorite;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.jartisan.latest.dao.sonatype.mapper.FavoriteLogMapper;
import com.github.jartisan.latest.dao.sonatype.query.LatestQuery;
import com.github.jartisan.latest.global.consts.Const;

/***
 * 
 * @author jalen 2015年2月4日 下午12:27:47
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest
@Transactional(rollbackFor = Exception.class)
public class FavoriteLogMapperTest {
	
	private static Logger log = LoggerFactory.getLogger(FavoriteLogMapperTest.class);
	@Autowired
	private FavoriteLogMapper favoriteLogMapper;
	
	@Test
	public void TestListLatest() {
		List<LatestQuery> list = favoriteLogMapper.listLatestByToday(null);
		log.info("Latest size : {}",list.size());
		
		List<LatestQuery> listMaven = favoriteLogMapper.listLatestByToday(Const.CHECK_TYPE_MAVEN);
		log.info("listMaven size : {}",listMaven.size());
	}
	
	@Test
	public void TestList3day() {
		List<LatestQuery> list = favoriteLogMapper.listLatestBy3day(null);
		log.info("Latest size : {}",list.size());
		
		List<LatestQuery> listMaven = favoriteLogMapper.listLatestBy3day(Const.CHECK_TYPE_MAVEN);
		log.info("listMaven size : {}",listMaven.size());
	}


}
