package com.github.jartisan.latest.global.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.jartisan.latest.global.entity.GithubInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/***
 * GithubUtil
 * @author ejb3
 *
 */
public class GithubUtil {
	
	private static final Logger log = LogManager.getLogger(GithubUtil.class);
	
	/**
	 *setConnectTimeout 设置连接超时时间，单位毫秒。 
	 */
	private static final int  SET_CONNECT_TIMEOUT = 30000;
	/***
	 * setSocketTimeout：请求获取数据的超时时间，单位毫秒
	 */
	private static final int  SET_SOCKET_TIMEOUT = 30000;

	/***
	 * 获取项目信息
	 * @param fullName 全名
	 * @return
	 */
	public static GithubInfo getProjectInfo(String api,String token) {
		
		String body =GithubUtil.httpGet(api,buildTokenParams(token));
		
		JSONObject json = JSON.parseObject(body);
		int starCount = json.getIntValue("stargazers_count");
		int watcherCount = json.getIntValue("subscribers_count");
		int forkCount = json.getIntValue("forks");
		Date created = json.getDate("created_at");
		
		GithubInfo info = new GithubInfo();
		info.setStarCount(starCount);
		info.setForkCount(forkCount);
		info.setWatcherCount(watcherCount);
		info.setCreatedDate(created);
		
		return info;
	}
	
	
	/***
	 * 获取Tags信息
	 * @param fullName 全名
	 * @return
	 */
	public static GithubInfo getTags(String api,String token) {
		List<String> list = Lists.newArrayList();
		String body =GithubUtil.httpGet(api+"/tags",buildTokenParams(token));
		
		JSONArray array = JSON.parseArray(body);
		for(Iterator<Object> iterator = array.iterator();iterator.hasNext();){
			JSONObject jsonObject =	(JSONObject)iterator.next();
			list.add(jsonObject.getString("name"));
		}
		GithubInfo info = new GithubInfo();
		info.setTags(list);
		return info;
	}
	
	/***
	 * 获取最近一次Tag信息
	 * @param fullName 全名
	 * @return
	 */
	public static String getLastTag(String api,String token) {
		String tag = "unknown";
		GithubInfo info =getTags(api,token);
		List<String> tags =info.getTags();
		if(null!=tags&&tags.size()>0) {
			return tags.get(0);
		}
		return tag;
	}
	
	
	/***
	 * 获取Releases信息
	 * @param fullName 全名
	 * @return
	 */
	public static GithubInfo getReleases(String api,String token) {
		List<String> list = Lists.newArrayList();
		String body =GithubUtil.httpGet(api+"/releases",buildTokenParams(token));
		
		JSONArray array = JSON.parseArray(body);
		for(Iterator<Object> iterator = array.iterator();iterator.hasNext();){
			JSONObject jsonObject =	(JSONObject)iterator.next();
			list.add(jsonObject.getString("tag_name"));
		}
		GithubInfo info = new GithubInfo();
		info.setReleases(list);
		return info;
	}
	
	
	/***
	 * 获取Commits信息
	 * @param fullName 全名
	 * @return
	 */
	public static GithubInfo getCommits(String api,String token) {
		List<String> list = Lists.newArrayList();
		String body =GithubUtil.httpGet(api+"/commits",buildTokenParams(token));
		
		JSONArray array = JSON.parseArray(body);
		for(Iterator<Object> iterator = array.iterator();iterator.hasNext();){
			JSONObject jsonObject =	(JSONObject)iterator.next();
			list.add(jsonObject.getString("sha"));
		}
		GithubInfo info = new GithubInfo();
		info.setCommits(list);
		return info;
	}
	
	
	/***
	 * commit
	 * @param fullName 全名
	 * @return
	 */
	public static GithubInfo commit(String api,String token) {
		List<String> list = Lists.newArrayList();
		String body =GithubUtil.httpGet(api+"/commits",buildTokenParams(token));
		
		JSONArray array = JSON.parseArray(body);
		for(Iterator<Object> iterator = array.iterator();iterator.hasNext();){
			JSONObject jsonObject =	(JSONObject)iterator.next();
			list.add(jsonObject.getString("sha"));
		}
		GithubInfo info = new GithubInfo();
		info.setCommits(list);
		return info;
	}
	
	/***
	 * 获取Releases信息
	 * @param fullName 全名
	 * @return
	 */
	public static GithubInfo getReleases4html(String fullName,String token) {
		List<String> list = Lists.newArrayList();
		String body =GithubUtil.httpGet("https://github.com/"+fullName+"/releases",buildTokenParams(token));
		Document doc = Jsoup.parse(body);
		Elements divs = doc.select("div[class=commit js-details-container Details]");
		for (org.jsoup.nodes.Element div : divs) {
			Element link = div.getElementsByTag("a").get(0);
			String linkText = link.text();
			if(!list.contains(div.text())) {
				list.add(linkText);	
			}
		}
		GithubInfo info = new GithubInfo();
		info.setReleases(list);
		return info;
	}
	
	/***
	 * 获取最近一次Release信息
	 * @param fullName 全名
	 * @return
	 */
	public static String getLastRelease(String api,String token) {
		String release = "unknown";
		GithubInfo info =getReleases(api,token);
		List<String> releases =info.getReleases();
		if(null!=releases&&releases.size()>0) {
			return releases.get(0);
		}
		return release;
	}
	
	
	
	public static List<NameValuePair> buildTokenParams (String token){
		List<NameValuePair> params = Lists.newArrayList();
		params.add(new BasicNameValuePair("access_token",token));
		return params;
	}
	
	public static String httpGet(String url, List<NameValuePair> params) {
		log.debug("httpclient invoked,url:{},params:{},",url,params);
		StopWatch stpWatch = new StopWatch();
		stpWatch.start();
		String body = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			Preconditions.checkArgument(url.length() > 0, "url is:\'\'");
			httpclient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SET_CONNECT_TIMEOUT).setConnectTimeout(SET_SOCKET_TIMEOUT).setCookieSpec(CookieSpecs.STANDARD).build();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setConfig(requestConfig);
			// 设置参数
			String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
			httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));
			// 发送请求
			response = httpclient.execute(httpGet);
			Header[] headers = response.getAllHeaders();
			Map<String,String> headerMap = Maps.newHashMap();
			for (Header header : headers) {
				headerMap.put(header.getName(),header.getValue());
			}
			
			// 获取返回数据
			HttpEntity entity = response.getEntity();
			log.debug("http statuscode {}", response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 获取返回数据
				body = EntityUtils.toString(entity,Consts.UTF_8);
				log.info("X-RateLimit-Limit : {} , X-RateLimit-Remaining : {}",headerMap.get("X-RateLimit-Limit"),headerMap.get("X-RateLimit-Remaining"));
				//log.info("httpclient invoked,responsebody:{}",body);
			}
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			log.error("Can't send httpGet... " + e.getMessage(), e);
		} catch (IOException e) {
			log.error("Can't send httpGet... " + e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.error("Can't send httpGet... " + e.getMessage(), e);
		} catch (URISyntaxException e) {
			log.error("Can't send httpGet... " + e.getMessage(), e);
		} finally {
			if (null != response){
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					log.error("Can't send httpGet... " + e.getMessage(), e);
				}
			}
				
		}
		stpWatch.stop();
		log.debug("httpclient invoked,耗时:{}ms",stpWatch.getTime(TimeUnit.MILLISECONDS));
		return body;
	}
	
	
	public static String httpPut(String url, String outStr,List<NameValuePair> params) {
		log.debug("httpclient invoked,url:{},params:{},",url,outStr);
		StopWatch stpWatch = new StopWatch();
		stpWatch.start();
		String body = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			Preconditions.checkArgument(url.length() > 0, "url is:\'\'");
			HttpPut httpput = new HttpPut(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SET_CONNECT_TIMEOUT).setConnectTimeout(SET_SOCKET_TIMEOUT).build();
			httpput.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(outStr, Consts.UTF_8);
			httpput.setEntity(stringEntity);
			Header[] headers = new BasicHeader[] { new BasicHeader("Content-Type", "application/json") };
			httpput.setHeaders(headers);
			httpclient = HttpClients.createDefault();
			
			// 设置参数
			String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
			httpput.setURI(new URI(httpput.getURI().toString() + "?" + str));
			
			response = httpclient.execute(httpput);
			
			Header[] headerList = response.getAllHeaders();
			Map<String,String> headerMap = Maps.newHashMap();
			for (Header header : headerList) {
				headerMap.put(header.getName(),header.getValue());
			}
			
			log.info("http statuscode {}", response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				body = EntityUtils.toString(response.getEntity(),Consts.UTF_8);
				log.info("X-RateLimit-Limit : {} , X-RateLimit-Remaining : {}",headerMap.get("X-RateLimit-Limit"),headerMap.get("X-RateLimit-Remaining"));
				log.info("httpclient invoked,responsebody:{}",body);
			}
			EntityUtils.consume(stringEntity);
		} catch (ClientProtocolException e) {
			log.error("Can't send sendPost... " + e.getMessage(), e);
		} catch (IOException e) {
			log.error("Can't send sendPost... " + e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.error("Can't send sendPost... " + e.getMessage(), e);
		} catch (URISyntaxException e) {
			log.error("Can't send sendPost... " + e.getMessage(), e);
		} finally {
			if (null != response){
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					log.error("Can't send sendPost... " + e.getMessage(), e);
				}
			}
				
		}
		stpWatch.stop();
		log.debug("httpclient invoked,耗时:{}ms",stpWatch.getTime(TimeUnit.MILLISECONDS));
		return body;
	}
	
}
