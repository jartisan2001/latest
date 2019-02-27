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

import com.github.jartisan.latest.dao.sonatype.entity.Favorite;
import com.github.jartisan.latest.dao.sonatype.mapper.FavoriteMapper;
import com.github.jartisan.latest.global.consts.Const;

/***
 * 
 * @author ejb3 2015年2月4日 下午12:27:47
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest
@Transactional(rollbackFor = Exception.class)
public class FavoriteMapperTest {
	
	private static Logger log = LoggerFactory.getLogger(FavoriteMapperTest.class);
	@Autowired
	private FavoriteMapper favoriteMapper;
	
	@Test
	public void TestListAll() {
		List<Favorite> list = favoriteMapper.listAll(null);
		log.info("ListAll size : {}",list.size());
		List<Favorite> listMaven = favoriteMapper.listAll(Const.CHECK_TYPE_MAVEN);
		log.info("listMaven size : {}",listMaven.size());
	}

}
