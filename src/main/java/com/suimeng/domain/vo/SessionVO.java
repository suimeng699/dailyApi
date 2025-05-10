package com.suimeng.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionVO {
    private Long sessionId;
    private String title;
    private LocalDateTime createTime;

    public SessionVO(Long sessionId, String title, LocalDateTime createTime) {
        this.sessionId = sessionId;
        this.title = title;
        this.createTime = createTime;
    }
    public SessionVO() {
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
