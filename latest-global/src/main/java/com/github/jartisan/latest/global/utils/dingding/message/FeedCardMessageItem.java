package com.github.jartisan.latest.global.utils.dingding.message;

/**
 *
 * @author dustin
 * @date 2017/3/19
 */
public class FeedCardMessageItem {
    private String title;
    private String picURL;
    private String messageURL;

    public String getMessageURL() {
        return messageURL;
    }

    public void setMessageURL(String messageURL) {
        this.messageURL = messageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }
}