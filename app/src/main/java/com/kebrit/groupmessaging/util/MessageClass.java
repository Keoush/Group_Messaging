package com.kebrit.groupmessaging.util;

/**
 * Created by sadegh on 5/14/15.
 */
public class MessageClass {

    private String content;
    private String senderId;
    private String receiverId;
    private String date;

    public MessageClass(String content, String senderId, String receiverId) {
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public MessageClass(String content, String senderId, String receiverId, String date) {
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getDate() {
        return date;
    }
}
