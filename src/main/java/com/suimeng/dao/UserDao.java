package com.suimeng.dao;

import com.suimeng.domain.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao{
    @Select("select user_id userId,user_name userName from tb_user where user_id = #{userId}")
    User getUserById(Integer userId);
}
