package com.github.jartisan.latest.maven;

import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.jartisan.latest.global.entity.Dependency;
import com.github.jartisan.latest.global.utils.SonatypeUtil;

/**   
* @Title: MavenDeployTest.java 
* @Package com.github.jartisan.latest.maven 
* @Description: http://search.maven.org 中央仓库类库 rest api 测试
* @author wjl
* @date 2018年5月26日 下午3:14:08 
* @version V1.0   
*/
public class MavenDeployTest {
	
	private static Logger logger = LoggerFactory.getLogger(MavenDeployTest.class);
	
	private static final String RESP_URL="http://search.maven.org/solrsearch/select";
	
	
	@Test
	public void testSonatypeUtil(){
		String groupId= "org.apache.maven";
		String artifactId= "maven";
		
		Dependency dependency=SonatypeUtil.getLatestRelease(groupId,artifactId);
		logger.info(dependency.toString());
		
		List<Dependency> dependencys =  SonatypeUtil.listReleases(groupId, artifactId);
		logger.info(dependencys.toString());
		
		
	}
	
	
	
	@Test
	public void testSearchRepository(){
		String groupId= "org.apache.cxf";
		String artifactId= "cxf";
		
		JSONObject jsonObj=grabbing(groupId,artifactId);
		
		JSONArray array = grabbings(groupId, artifactId);
		
		for(int i=0;i<array.size();i++) {
			JSONObject obj =array.getJSONObject(i);
			logger.info(obj.getString("g"));
			logger.info(obj.getString("a"));
			logger.info(obj.getString("v"));
			DateTime dateTime = new DateTime(obj.getLongValue("timestamp"));
			logger.info(dateTime.toString("yyyy-MM-dd"));
		}
		
		
		
		logger.info(jsonObj.toJSONString());
		
	}
	
	@Test
	/***
	 * 获取最新版本
	 * @param groupId
	 * @param artifactId
	 * @return
	 */
	static JSONObject grabbing(String groupId,String artifactId){
		//String url = RESP_URL+"?q=g:\""+groupId+"\"+AND+a:\""+artifactId+"\"&core=gav&rows=1&wt=json";
		String url = RESP_URL+"?q=g:%22"+groupId+"%22+AND+a:%22"+artifactId+"%22&core=gav&rows=10&wt=json";
		JSONObject docJson = null;
		try {
			//URLEncoder.encode(url, "UTF-8");
			org.jsoup.nodes.Document doc = null;
			//doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").ignoreContentType(true).timeout(30000).get();
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").ignoreContentType(true).timeout(30000).get();
			String elem = doc.body().text();
			logger.info("The Central Repository 返回值:"+elem);
			JSONObject response = JSONObject.parseObject(elem).getJSONObject("response");
			if (response.containsKey("docs") && response.getJSONArray("docs").size() > 0) {
				docJson = response.getJSONArray("docs").getJSONObject(0);
				logger.info(docJson.getString("g"));
				logger.info(docJson.getString("a"));
				logger.info(docJson.getString("v"));
			}
			logger.info(doc.text());
		} catch (IOException e) {
			logger.error("Can't send httpGet... " + e.getMessage(), e);
		}
		return docJson;
	}
	
	/***
	 * 获取最新版本
	 * @param groupId
	 * @param artifactId
	 * @return
	 */
	static JSONArray grabbings(String groupId,String artifactId){
		//String url = RESP_URL+"?q=g:\""+groupId+"\"+AND+a:\""+artifactId+"\"&core=gav&rows=1&wt=json";
		String url = RESP_URL+"?q=g:%22"+groupId+"%22+AND+a:%22"+artifactId+"%22&core=gav&rows=10&wt=json";
		JSONArray docJson = null;
		try {
			//URLEncoder.encode(url, "UTF-8");
			org.jsoup.nodes.Document doc = null;
			//doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").ignoreContentType(true).timeout(30000).get();
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").ignoreContentType(true).timeout(30000).get();
			String elem = doc.body().text();
			logger.info("The Central Repository 返回值:"+elem);
			JSONObject response = JSONObject.parseObject(elem).getJSONObject("response");
			if (response.containsKey("docs") && response.getJSONArray("docs").size() > 0) {
				docJson = response.getJSONArray("docs");
			}
			logger.info(doc.text());
		} catch (IOException e) {
			logger.error("Can't send httpGet... " + e.getMessage(), e);
		}
		return docJson;
	}
	
	

}
