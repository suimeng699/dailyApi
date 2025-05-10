package com.suimeng.domain.pojo;

import lombok.Data;

@Data
public class ChatMessage {
    private Long messageId;
    private Long sessionId;
    private String message;
    private boolean isUser;
    private Long snowId;

    public ChatMessage(Long sessionId, String message, boolean isUser, Long snowId) {
        this.sessionId = sessionId;
        this.message = message;
        this.isUser = isUser;
        this.snowId = snowId;
    }
    public ChatMessage() {
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public long getSnowId() {
        return snowId;
    }

    public void setSnowId(long snowId) {
        this.snowId = snowId;
    }
}
