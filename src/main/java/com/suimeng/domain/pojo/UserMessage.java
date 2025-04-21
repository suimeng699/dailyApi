package com.suimeng.domain.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Data
public class UserMessage {
    private Integer messageId;
    private Integer userId;
    private String message;
    public UserMessage(Integer userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
