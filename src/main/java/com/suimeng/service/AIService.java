package com.suimeng.service;

import com.suimeng.domain.dto.AIReqDTO;
import com.suimeng.domain.pojo.ChatSession;
import com.suimeng.domain.vo.MessageVO;
import com.suimeng.domain.vo.SessionVO;

import java.util.List;

public interface AIService {
    String sendRequest(AIReqDTO dto);

    SessionVO createSession(String title);

    List<ChatSession> getSessionsByUserId();

    void deleteSession(Long sessionId);

    List<SessionVO> getAllSessions();

    MessageVO getMessagesBySessionId(Long sessionId);
}
