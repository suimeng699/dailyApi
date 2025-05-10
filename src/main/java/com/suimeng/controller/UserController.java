package com.suimeng.controller;

import com.suimeng.domain.dto.LoginDTO;
import com.suimeng.domain.dto.UserUpdateDTO;
import com.suimeng.domain.pojo.Result;
import com.suimeng.domain.vo.LoginVO;
import com.suimeng.service.UserService;
import com.suimeng.utils.JwtUtil;
import com.suimeng.utils.TokenBlacklist;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;

/**
 * 用户相关接口控制器
 * 提供登录、注册、登出、用户信息更新等接口
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenBlacklist tokenBlacklist;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录接口
     * @param dto 登录参数
     * @return 登录结果，包含token和用户信息
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        logger.info("用户尝试登录，用户名: {}", dto.getUsername());
        try {
            LoginVO response = userService.login(dto);
            logger.info("用户登录成功: {}", dto.getUsername());
            return Result.success(response);
        } catch (Exception e) {
            logger.error("用户登录失败: {}，原因: {}", dto.getUsername(), e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户登出接口
     * @return 退出结果
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            long expire = jwtUtil.getExpireSeconds(token); // 你需要实现这个方法
            tokenBlacklist.blacklistToken(token, expire);
        }
        return Result.success(null);
    }

    /**
     * 用户注册接口
     * @param dto 注册参数
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody LoginDTO dto) {
        return Result.success("register is not support now!");
//        logger.info("用户尝试注册，用户名: {}", dto.getUsername());
//        try {
//            userService.register(dto);
//            logger.info("用户注册成功: {}", dto.getUsername());
//            return Result.success("success");
//        } catch (Exception e) {
//            logger.error("用户注册失败: {}，原因: {}", dto.getUsername(), e.getMessage(), e);
//            return Result.error(e.getMessage());
//        }
    }

    /**
     * 用户信息更新接口
     * @param dto 更新参数
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result<String> update(@RequestBody UserUpdateDTO dto) {
        logger.info("用户尝试更新信息，用户名: {}", dto.getUsername());
        try {
            userService.update(dto);
            logger.info("用户信息更新成功，用户名: {}", dto.getUsername());
            return Result.success("success");
        }
        catch (Exception e) {
            logger.error("用户信息更新失败，用户名: {}，原因: {}", dto.getUsername(), e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

}
