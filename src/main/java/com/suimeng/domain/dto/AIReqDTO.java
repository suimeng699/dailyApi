package com.suimeng.domain.dto;

import lombok.Data;

@Data
public class AIReqDTO {
    private String content;
    private String name;
    private Long sessionId;

    public AIReqDTO(String content, String name, Long sessionId) {
        this.content = content;
        this.name = name;
        this.sessionId = sessionId;
    }
    public AIReqDTO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
