package com.github.jartisan.latest.global.utils;

import java.io.IOException;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.jartisan.latest.global.entity.Dependency;

/***
 * SonatypeUtil
 * @author jalen
 *
 */
public class SonatypeUtil {
	private static final Logger log = LogManager.getLogger(SonatypeUtil.class);
	
	public static final String MAVEN_SONATYPE = "https://search.maven.org/solrsearch/select";
	
	
	public static List<Dependency> listReleases(String groupId,String artifactId){
		List<Dependency> dependencys = Lists.newArrayList();
		String url = MAVEN_SONATYPE+"?q=g:%22"+groupId+"%22+AND+a:%22"+artifactId+"%22&core=gav&rows=10&wt=json";
		log.debug("url:"+url);
		JSONArray array = null;
		try {
			org.jsoup.nodes.Document doc = null;
			doc = Jsoup.connect(url).ignoreContentType(true).timeout(30000).get();
			String elem = doc.body().text();
			log.debug("The Central Repository 返回值:"+elem);
			JSONObject response = JSONObject.parseObject(elem).getJSONObject("response");
			String key = "docs";
			if (response.containsKey(key) && response.getJSONArray(key).size() > 0) {
				array = response.getJSONArray("docs");
				for(int i=0;i<array.size();i++) {
					Dependency dependency = new Dependency();
					JSONObject obj =array.getJSONObject(i);
					dependency.setArtifactId(obj.getString("a"));
					dependency.setGroupId(obj.getString("g"));
					dependency.setVersion(obj.getString("v"));
					DateTime dateTime = new DateTime(obj.getLongValue("timestamp"));
					dependency.setUpdatedTimestamp(dateTime.toDate());
					dependencys.add(dependency);
				}
			}
		} catch (IOException e) {
			log.error("Can't send httpGet... " + e.getMessage(), e);
		}
		return dependencys;
	}
	
	
	
	public static Dependency getLatestRelease(String groupId,String artifactId){
		Dependency dependency = new Dependency();
		String url = MAVEN_SONATYPE+"?q=g:%22"+groupId+"%22+AND+a:%22"+artifactId+"%22&core=gav&rows=1&wt=json";
		log.debug("url:"+url);
		JSONObject docJson = null;
		try {
			org.jsoup.nodes.Document doc = null;
			doc = Jsoup.connect(url).ignoreContentType(true).timeout(30000).get();
			String elem = doc.body().text();
			log.debug("The Central Repository 返回值:"+elem);
			JSONObject response = JSONObject.parseObject(elem).getJSONObject("response");
			String key = "docs";
			if (response.containsKey(key) && response.getJSONArray(key).size() > 0) {
				docJson = response.getJSONArray(key).getJSONObject(0);
				dependency.setArtifactId(docJson.getString("a"));
				dependency.setGroupId(docJson.getString("g"));
				dependency.setVersion(docJson.getString("v"));
				DateTime dateTime = new DateTime(docJson.getLongValue("timestamp"));
				dependency.setUpdatedTimestamp(dateTime.toDate());
			}
		} catch (IOException e) {
			log.error("Can't send httpGet... " + e.getMessage(), e);
		}
		return dependency;
	}
}
