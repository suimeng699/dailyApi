package com.suimeng.dao;

import com.suimeng.domain.pojo.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface MessageDao {
    @Select("select message_id messageId, user_id userId, message from db_message where user_id = #{userId}")
    public List<UserMessage> getMessage(Integer userId);
    @Select("insert into db_message (user_id, message) values (#{userId}, #{message})")
    public UserMessage insertMessage(UserMessage userMessage);
}
