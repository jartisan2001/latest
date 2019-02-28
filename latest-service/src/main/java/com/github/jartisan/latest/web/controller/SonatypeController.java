package com.github.jartisan.latest.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.jartisan.latest.dao.sonatype.entity.Favorite;
import com.github.jartisan.latest.global.entity.Dependency;
import com.github.jartisan.latest.global.exception.BaseException;
import com.github.jartisan.latest.global.response.RestResult;
import com.github.jartisan.latest.service.sonatype.SonatypeService;

/**
 * 
 * @ClassName: SonatypeController
 * @Description: sonatype
 * @author jalen
 * @date 2017年3月27日
 */
@RestController
@RequestMapping("/api/sonatype")
public class SonatypeController {

	private static final Logger logger = LoggerFactory.getLogger(SonatypeController.class);

	@Autowired
	private SonatypeService sonatypeService;

	/***
	 * 保存favorite
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "/v1/favorite", method = RequestMethod.POST)
	public RestResult<Favorite> favorite(@Validated @RequestBody Dependency dependency) throws BaseException {
		logger.info("favorite dependency : {}", dependency.toString());
		Favorite favorite = sonatypeService.saveFavorite(dependency);
		return RestResult.ok(favorite);
	}

}
