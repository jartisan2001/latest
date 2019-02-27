package com.github.jartisan.latest.global.utils.dingding;

import com.alibaba.fastjson.JSONObject;
import com.github.jartisan.latest.global.utils.HttpClientUtil;
import com.github.jartisan.latest.global.utils.dingding.message.Message;

/**
 *
 * @author dustin
 * @date 2017/3/17
 */
public class DingtalkChatbotClient {

    public SendResult send(String webhook, Message message){

        SendResult sendResult = new SendResult();
        String result = HttpClientUtil.httpPost(webhook, message.toJsonString());
            JSONObject obj = JSONObject.parseObject(result);
            Integer errcode = obj.getInteger("errcode");
            sendResult.setErrorCode(errcode);
            sendResult.setErrorMsg(obj.getString("errmsg"));
            sendResult.setIsSuccess(errcode.equals(0));

        return sendResult;
    }

}


