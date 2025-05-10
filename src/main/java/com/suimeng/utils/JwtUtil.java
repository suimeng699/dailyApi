package com.suimeng.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * JWT工具类
 * 提供Token生成、解析、校验等功能
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey key;

    /**
     * 初始化方法，校验配置并生成密钥
     */
    @PostConstruct
    public void init() {
        if (secret == null || expiration == null) {
            throw new IllegalStateException("JWT configuration is missing. Please check application.yml");
        }
        // 将Base64编码的secret转换为字节数组
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    /**
     * 生成JWT Token
     * @param username
     * @return
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * 从Token中解析用户名
     * @param token Token字符串
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * 校验Token有效性
     * @param token Token字符串
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        logger.info("[JwtUtil] validateToken called");
        logger.info("[JwtUtil] Token to validate: {}", token);
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            logger.info("[JwtUtil] Token validation success");
            return true;
        } catch (Exception e) {
            logger.error("[JwtUtil] Token validation failed: {}", e.getMessage(), e);
            return false;
        }
    }
    /**
 * 获取token剩余过期秒数
 * @param token Token字符串
 * @return 剩余秒数，已过期返回0
 */
public long getExpireSeconds(String token) {
    try {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Date expirationDate = claims.getExpiration();
        long diff = (expirationDate.getTime() - System.currentTimeMillis()) / 1000;
        return Math.max(diff, 0);
    } catch (Exception e) {
        logger.error("[JwtUtil] getExpireSeconds failed: {}", e.getMessage(), e);
        return 0;
    }
}}