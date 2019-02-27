package com.github.jartisan.latest.global.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Preconditions;

/***
 * HttpClentUtil
 * 
 * @author wjl
 */
public class HttpClientUtil {

	private static final Logger log = LogManager.getLogger(HttpClientUtil.class);
	/**
	 *setConnectTimeout 设置连接超时时间，单位毫秒。 
	 */
	private static final int  SET_CONNECT_TIMEOUT = 30000;
	/***
	 * setSocketTimeout：请求获取数据的超时时间，单位毫秒
	 */
	private static final int  SET_SOCKET_TIMEOUT = 30000;
	
	public static String httpGet(String url) {
		log.debug("httpclient invoked,url:{}",url);
		StopWatch stpWatch = new StopWatch();
		stpWatch.start();
		String body = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			Preconditions.checkArgument(url.length() > 0, "url is:\'\'");
			httpclient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SET_CONNECT_TIMEOUT).setConnectTimeout(SET_SOCKET_TIMEOUT).build();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setConfig(requestConfig);
			// 设置参数
			httpGet.setURI(new URI(httpGet.getURI().toString()));
			// 发送请求
			response = httpclient.execute(httpGet);
			// 获取返回数据
			HttpEntity entity = response.getEntity();
			log.debug("http statuscode {}", response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 获取返回数据
				body = EntityUtils.toString(entity,Consts.UTF_8);
				log.debug("httpclient invoked,responsebody:{}",body);
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
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SET_CONNECT_TIMEOUT).setConnectTimeout(SET_SOCKET_TIMEOUT).build();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setConfig(requestConfig);
			// 设置参数
			String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
			httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));
			// 发送请求
			response = httpclient.execute(httpGet);
			// 获取返回数据
			HttpEntity entity = response.getEntity();
			log.debug("http statuscode {}", response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 获取返回数据
				body = EntityUtils.toString(entity,Consts.UTF_8);
				//log.debug("httpclient invoked,responsebody:{}",body);
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
	
	
	public static String httpGet(String url, List<NameValuePair> params,String charset) {
		log.debug("httpclient invoked,url:{},params:{},",url,params);
		StopWatch stpWatch = new StopWatch();
		stpWatch.start();
		String body = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			Preconditions.checkArgument(url.length() > 0, "url is:\'\'");
			httpclient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SET_CONNECT_TIMEOUT).setConnectTimeout(SET_SOCKET_TIMEOUT).build();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setConfig(requestConfig);
			// 设置参数
			String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
			httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));
			// 发送请求
			response = httpclient.execute(httpGet);
			// 获取返回数据
			HttpEntity entity = response.getEntity();
			log.debug("http statuscode {}", response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 获取返回数据
				body = EntityUtils.toString(entity,charset);
				log.debug("httpclient invoked,responsebody:{}",body);
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
		log.debug("httpclient invoked,耗时:{}ms",stpWatch.getTime(TimeUnit.MILLISECONDS));
		return body;
	}

	public static String httpPost(String url, Header[] headers, String outStr) {
		log.debug("httpclient invoked,url:{},header:{},params:{},",url,headers,outStr);
		StopWatch stpWatch = new StopWatch();
		stpWatch.start();
		String body = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			Preconditions.checkArgument(url.length() > 0, "url is:\'\'");
			HttpPost httppost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SET_CONNECT_TIMEOUT).setConnectTimeout(SET_SOCKET_TIMEOUT).build();
			httppost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(outStr, Consts.UTF_8);
			httppost.setEntity(stringEntity);
			httppost.setHeaders(headers);
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(httppost);
			log.debug("http statuscode {}", response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				body = EntityUtils.toString(response.getEntity(),Consts.UTF_8);
				log.debug("httpclient invoked,responsebody:{}",body);
			}
			EntityUtils.consume(stringEntity);
		} catch (ClientProtocolException e) {
			log.error("Can't send sendPost... " + e.getMessage(), e);
		} catch (IOException e) {
			log.error("Can't send sendPost... " + e.getMessage(), e);
		} catch (IllegalArgumentException e) {
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
		log.debug("httpclient invoked,耗时:{}ms",stpWatch.getTime(TimeUnit.MILLISECONDS));
		return body;
	}

	public static String httpPost(String url, String outStr) {
		log.debug("httpclient invoked,url:{},params:{},",url,outStr);
		StopWatch stpWatch = new StopWatch();
		stpWatch.start();
		String body = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			Preconditions.checkArgument(url.length() > 0, "url is:\'\'");
			HttpPost httppost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SET_CONNECT_TIMEOUT).setConnectTimeout(SET_SOCKET_TIMEOUT).build();
			httppost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(outStr, Consts.UTF_8);
			httppost.setEntity(stringEntity);
			Header[] headers = new BasicHeader[] { new BasicHeader("Content-Type", "application/json") };
			httppost.setHeaders(headers);
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(httppost);
			log.debug("http statuscode {}", response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				body = EntityUtils.toString(response.getEntity(),Consts.UTF_8);
				log.debug("httpclient invoked,responsebody:{}",body);
			}
			EntityUtils.consume(stringEntity);
		} catch (ClientProtocolException e) {
			log.error("Can't send sendPost... " + e.getMessage(), e);
		} catch (IOException e) {
			log.error("Can't send sendPost... " + e.getMessage(), e);
		} catch (IllegalArgumentException e) {
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
	
	
	

	public static String httpPost(String url, List<NameValuePair> formparams) {
		log.debug("httpclient invoked,url:{},params:{},",url,formparams);
		StopWatch stpWatch = new StopWatch();
		stpWatch.start();
		String body = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			Preconditions.checkArgument(url.length() > 0, "url is:\'\'");
			HttpPost httppost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SET_CONNECT_TIMEOUT).setConnectTimeout(SET_SOCKET_TIMEOUT).build();
			httppost.setConfig(requestConfig);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
			httppost.setEntity(entity);
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(httppost);
			log.debug("http statuscode {}", response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				body = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
				log.debug("httpclient invoked,responsebody:{}",body);
			}
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			log.error("Can't send sendPost... " + e.getMessage(), e);
		} catch (IOException e) {
			log.error("Can't send sendPost... " + e.getMessage(), e);
		} catch (IllegalArgumentException e) {
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
		log.debug("httpclient invoked,耗时:{}ms",stpWatch.getTime(TimeUnit.MILLISECONDS));
		return body;
	}

	public static void main(String[] args) throws ParseException, IOException {
		/*
		 * String url = "https://api.weixin.qq.com/cgi-bin/token";
		 * List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		 * formparams.add(new BasicNameValuePair("grant_type",
		 * "client_credential")); formparams.add(new BasicNameValuePair("appid",
		 * "wxae034fc58441d7b2")); formparams.add(new
		 * BasicNameValuePair("secret", "c9813bc84a3032622dc4178e01c0bd75"));
		 * 
		 * String body =HttpClentUtil.httpGet(url, formparams);
		 * 
		 * log.debug(body);
		 */

		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=S9STXzYKfiNH-y9CCt1CpdnpHZWkNgRQwrhRkMzF8ZZY-NhgOHyBci9sfVHLLH6zCG0GnZqNkZTNzmyJkrI2NvPUswNbE8tv67ImhKwc5imz1kYX8RWGwEDzI4qzLhl2WOXjACAGYH";
		String body = "{\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\": \"搜索\",\"url\":\"http://www.soso.com/\"}]}]}";
		// System.out.println(HttpClentUtil.doPostStr(url, body));
		System.out.println(HttpClientUtil.httpPost(url, body));

	}

}
