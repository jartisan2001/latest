package com.github.jartisan.latest.global.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/***
 * HttpPostRetryUtils
 * @author jalen
 */
public class HttpPostRetryUtils {
	
	private static final Logger log = LogManager.getLogger(HttpPostRetryUtils.class);
	
	public static String retryPostJson(String uri, String json, int retryCount, int connectTimeout,
			int connectionRequestTimeout, int socketTimeout){
		log.info("HttpPostRetryUtils invoked,url:{},params:{},",uri,json);
		StopWatch stpWatch = new StopWatch();
		stpWatch.start();
		if (StringUtils.isAnyBlank(uri, json)) {
			log.error("HttpPostRetryUtils uri or json is not blank!");
			return null;
		}

		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {

			@Override
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount <= retryCount) {
					log.info("正在重试{}次，uri :{}",executionCount,uri);	
				}
				
				if (executionCount > retryCount) {
					log.error("HttpPostRetryUtils Do not retry if over max retry count"); 
					return false;
				}
				if (exception instanceof InterruptedIOException) {
					log.error("HttpPostRetryUtils An input or output transfer has been terminated");
					return false;
				}
				if (exception instanceof UnknownHostException) {
					// 修改代码让不识别主机时重试，实际业务当不识别的时候不应该重试，再次为了演示重试过程，执行会显示retryCount次下面的输出
					log.error("HttpPostRetryUtils Unknown host");
					return true;
				}
				if (exception instanceof ConnectException) {
					log.error("HttpPostRetryUtils Connection refused");
					return false;
				}
				if (exception instanceof SSLException) {
					log.error("HttpPostRetryUtils SSL handshake exception");
					return false;
				}
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// Retry if the request is considered idempotent
					return true;
				}
				return false;
			}
		};
		String responseContent = httpPost(uri, json, connectTimeout, connectionRequestTimeout, socketTimeout,httpRequestRetryHandler);
		stpWatch.stop();
		log.info("HttpPostRetryUtils invoked,耗时:{}ms",stpWatch.getTime(TimeUnit.MILLISECONDS));
		return responseContent;
	}

	private static String httpPost(String uri, String json, int connectTimeout, int connectionRequestTimeout,
			int socketTimeout, HttpRequestRetryHandler httpRequestRetryHandler) {
		CloseableHttpClient client = HttpClients.custom().setRetryHandler(httpRequestRetryHandler).build();
		HttpPost post = new HttpPost(uri);
		// Create request data
		StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		// Set request body
		post.setEntity(entity);

		RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).build();
		post.setConfig(config);
		String responseContent = null;
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post, HttpClientContext.create());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				responseContent = EntityUtils.toString(response.getEntity(), Consts.UTF_8.name());
			}
		} catch (ParseException e) {
			log.error("HttpPostRetryUtils Can't send httpPost... " + e.getMessage(), e);
		} catch (IOException e) {
			log.error("HttpPostRetryUtils Can't send httpPost... " + e.getMessage(), e);
		} finally {
			if (null != response){
				if (ObjectUtils.anyNotNull(response)) {
					try {
						EntityUtils.consume(response.getEntity());
					} catch (IOException e) {
						log.error("HttpPostRetryUtils Can't send httpPost... " + e.getMessage(), e);
					}
				}	
			}
			
		}
		return responseContent;
	}

}
