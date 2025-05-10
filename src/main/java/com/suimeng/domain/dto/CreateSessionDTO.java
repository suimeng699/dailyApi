package com.suimeng.domain.dto;

import lombok.Data;

@Data
public class CreateSessionDTO {
    String title;
    public CreateSessionDTO() {
    }
    public CreateSessionDTO(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
