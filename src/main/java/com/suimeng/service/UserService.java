package com.suimeng.service;

import com.suimeng.domain.dto.LoginDTO;
import com.suimeng.domain.dto.UserUpdateDTO;
import com.suimeng.domain.vo.LoginVO;

public interface UserService {
    LoginVO login(LoginDTO dto);

    void register(LoginDTO dto);

    void update(UserUpdateDTO dto);
}