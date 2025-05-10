package com.suimeng.service.impl;

import com.suimeng.dao.UserDao;
import com.suimeng.domain.dto.LoginDTO;
import com.suimeng.domain.dto.UserUpdateDTO;
import com.suimeng.domain.pojo.User;
import com.suimeng.domain.vo.LoginVO;
import com.suimeng.service.UserService;
import com.suimeng.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 * 实现用户的登录、注册、信息修改等业务逻辑
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录
     * @param dto 登录参数
     * @return 登录结果
     */
    @Override
    public LoginVO login(LoginDTO dto) {
        logger.info("用户尝试登录，用户名: {}", dto.getUsername());
        User user = userDao.findByUsername(dto.getUsername());
        if (user == null) {
            logger.error("用户不存在: {}", dto.getUsername());
            throw new RuntimeException("用户不存在");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            logger.error("密码错误，用户名: {}", dto.getUsername());
            throw new RuntimeException("密码错误");
        }

        LoginVO vo = getLoginVO(user);
        logger.info("用户登录成功: {}", dto.getUsername());
        return vo;
    }

    /**
     * 构建token响应
     * @param user 用户对象
     * @return 登录响应VO
     */
    private LoginVO getLoginVO(User user) {
        // 生成token
        String token = jwtUtil.generateToken(user.getUserName());

        // 构建响应
        LoginVO vo = new LoginVO();
        LoginVO.UserInfo userInfo = new LoginVO.UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUserName());

        vo.setToken(token);
        vo.setUser(userInfo);
        return vo;
    }

    /**
     * 用户注册
     * @param dto 注册参数
     */
    @Override
    public void register(LoginDTO dto) {
        logger.info("用户尝试注册，用户名: {}", dto.getUsername());
        //判断用户是否存在
        if (userDao.findByUsername(dto.getUsername()) != null) {
            logger.error("用户已存在: {}", dto.getUsername());
            throw new RuntimeException("用户已存在");
        }
        // 创建新用户
        User user = new User();
        user.setUserName(dto.getUsername());
        // 加密密码
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        //设置创建时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        //插入数据库
        int flag = userDao.insertUser(user);
        if (flag == 0) {
            logger.error("用户注册插入失败: {}", dto.getUsername());
            throw new RuntimeException("插入失败！");
        }
        logger.info("用户注册成功: {}", dto.getUsername());
    }

    /**
     * 修改用户信息
     * @param dto 修改参数
     */
    @Override
    public void update(UserUpdateDTO dto) {
        logger.info("用户尝试修改信息，用户名: {}", dto.getUsername());
        User user = userDao.findByUsername(dto.getUsername());
        //判断旧密码是否正确
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            logger.error("旧密码错误，用户名: {}", dto.getUsername());
            throw new RuntimeException("旧密码错误");
        }
        //将新密码赋值给user
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        //设置更新时间
        user.setUpdateTime(LocalDateTime.now());
        //更新数据库
        int flag = userDao.updateUser(user);
        logger.info("用户信息修改成功，用户名: {}", dto.getUsername());
    }

}
