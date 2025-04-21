package com.suimeng.domain.pojo;

import lombok.Data;
import org.springframework.context.annotation.Primary;

@Data
public class User {
    private Integer userId;
    private String userName;
}
