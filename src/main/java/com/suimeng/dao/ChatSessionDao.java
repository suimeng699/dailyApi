package com.suimeng.dao;

import com.suimeng.domain.pojo.ChatSession;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatSessionDao {
    /**
     * 新增会话
     * @param session
     * @return
     */
    @Insert("INSERT INTO tb_chat_session(user_id, title, create_time, update_time, is_deleted) " +
            "VALUES(#{userId}, #{title}, #{createTime}, #{updateTime}, #{isDeleted})")
    @Options(useGeneratedKeys = true, keyProperty = "sessionId")
    int insertSession(ChatSession session);

    /**
     * 根据用户id获取会话列表
     * @param userId
     * @return
     */
    @Select("SELECT session_id sessionId, user_id userId, title, create_time createTime, " +
            "update_time updateTime, is_deleted isDeleted FROM tb_chat_session " +
            "WHERE user_id = #{userId} AND is_deleted = false ORDER BY update_time DESC")
    List<ChatSession> getSessionsByUserId(Long userId);

    @Select("SELECT session_id sessionId, user_id userId, title, create_time createTime, " +
            "update_time updateTime, is_deleted isDeleted FROM tb_chat_session " +
            "WHERE session_id = #{sessionId}")
    ChatSession getSessionBySessionId(Long sessionId);
    /**
     * 删除会话
     * @param sessionId
     * @param userId
     * @return
     */
    @Update("UPDATE tb_chat_session SET is_deleted = true, update_time = NOW() " +
            "WHERE session_id = #{sessionId} AND user_id = #{userId}")
    int deleteSession(Long sessionId, Long userId);

} 