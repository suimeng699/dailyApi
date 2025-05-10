package com.suimeng.domain.vo;

import com.suimeng.domain.pojo.ai.Message;
import lombok.Data;

import java.util.List;

@Data
public class MessageVO{
    private Long sessionId;
    private String title;
    private List<Message> messages;

    public MessageVO(Long sessionId, String title, List<Message> messages) {
        this.sessionId = sessionId;
        this.title = title;
        this.messages = messages;
    }
    public MessageVO() {
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
