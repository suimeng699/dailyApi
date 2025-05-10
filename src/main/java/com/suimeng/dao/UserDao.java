package com.suimeng.dao;

import com.suimeng.domain.dto.LoginDTO;
import com.suimeng.domain.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
public interface UserDao{
    @Select("select user_id userId,user_name userName,password from tb_user where user_id = #{userId}")
    User getUserById(Long userId);

    @Select("select user_id userId,user_name userName,password from tb_user where user_name = #{userName}")
    User findByUsername(String userName);

    @Insert("insert into tb_user(user_name,password,create_time,update_time) values(#{userName},#{password},#{createTime},#{updateTime})")
    int insertUser(User user);

    @Update("update tb_user set password = #{password},update_time = #{updateTime} where user_id = #{id}")
    int updateUser(User user);
}
