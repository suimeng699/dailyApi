package com.suimeng.dao;

import com.suimeng.domain.pojo.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageDao {
    @Select("select message_id messageId, session_id sessionId,is_user isUser, message , snow_id snowId from tb_message where session_id = #{sessionId}")
    public List<ChatMessage> getMessageUserBySessionId(Long sessionId);
    @Insert("INSERT INTO tb_message(message, session_id, is_user, snow_id) VALUES (#{message}, #{sessionId}, #{isUser}, #{snowId})")
    @Options(useGeneratedKeys = true, keyProperty = "messageId")
    public int insertMessage(ChatMessage chatMessage);
}
