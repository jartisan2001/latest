package com.github.jartisan.latest.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

/****
 *  服务端事件消息接收
 * @author wjl
 *
 */
@WebServlet(name = "weixin", urlPatterns = "/weixin")
public class ReceiveServlet extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(ReceiveServlet.class);
	
	private static final long serialVersionUID = 1L;
	/***
	 * 重复通知过滤 
	 */
	private static ExpireKey expireKey = new DefaultExpireKey();
	/***
	 * 从官方获取 
	 */
	@Value("${weixin.token}")
	public String token;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletOutputStream outputStream = response.getOutputStream();
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		logger.info("ReceiveServlet doGet [signature:{}],[timestamp:{}],[nonce:{}],[echostr:{}]",signature,timestamp,nonce,echostr);

		//验证请求签名
		if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
			logger.info("The request signature is invalid");
			return;
		}
		
		outputStreamWrite(outputStream, echostr);
		
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletInputStream inputStream = request.getInputStream();
		ServletOutputStream outputStream = response.getOutputStream();
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		logger.info("ReceiveServlet doPost [signature:{}],[timestamp:{}],[nonce:{}],[echostr:{}]",signature,timestamp,nonce,echostr);

		// 首次请求申请验证,返回echostr
		if (echostr != null) {
			outputStreamWrite(outputStream, echostr);
			return;
		}

		// 验证请求签名
		if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
			logger.info("The request signature is invalid");
			return;
		}

		if (inputStream != null) {
			// 转换XML
			EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
			String key = eventMessage.getFromUserName() + "__" + eventMessage.getToUserName() + "__"
					+ eventMessage.getMsgId() + "__" + eventMessage.getCreateTime();
			if (expireKey.exists(key)) {
				// 重复通知不作处理
				return;
			} else {
				expireKey.add(key);
			}
			//MoreObjects.toStringHelper(eventMessage).omitNullValues();
			logger.info(ReflectionToStringBuilder.toString(eventMessage, ToStringStyle.MULTI_LINE_STYLE, true, true, true, null));
			// 创建回复
			XMLMessage xmlTextMessage = new XMLTextMessage(eventMessage.getFromUserName(), eventMessage.getToUserName(),"细节之中自有天地，重构成就优秀代码;\r\n" + 
					"一起分享，重构心得，讲述你的重构故事。\r\n" + 
					"来吧程序猿~~~。\r\n");
			// 回复
			xmlTextMessage.outputStreamWrite(outputStream);
			return;
		}
		outputStreamWrite(outputStream, "");
	}

	/**
	 * 数据流输出
	 */
	private boolean outputStreamWrite(OutputStream outputStream, String text) {
		try {
			outputStream.write(text.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("outputStreamWrite" + "_" +e.getMessage(),e);
			return false;
		} catch (IOException e) {
			logger.error("outputStreamWrite" + "_" +e.getMessage(),e);
			return false;
		}
		return true;
	}

}
