package com.suimeng.service.impl;

import com.google.gson.Gson;
import com.suimeng.dao.ChatSessionDao;
import com.suimeng.dao.MessageDao;
import com.suimeng.domain.dto.AIReqDTO;
import com.suimeng.domain.pojo.*;
import com.suimeng.domain.pojo.ai.ChatRequest;
import com.suimeng.domain.pojo.ai.ChatResponse;
import com.suimeng.domain.pojo.ai.Choice;
import com.suimeng.domain.pojo.ai.Message;
import com.suimeng.domain.vo.MessageVO;
import com.suimeng.domain.vo.SessionVO;
import com.suimeng.property.AIProperties;
import com.suimeng.service.AIService;
import com.suimeng.utils.SnowflakeIdGenerator;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * AI服务实现类
 * 实现AI对话请求、历史消息管理等业务逻辑
 */
@Service
public class AIServiceImpl implements AIService {
    @Autowired
    private ChatSessionDao chatSessionDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private AIProperties aiProperties;
    @Autowired
    private  SnowflakeIdGenerator snowflakeIdGenerator;
    private static final Logger log = LoggerFactory.getLogger(AIServiceImpl.class);

    /**
     * 发送请求
     * @param dto
     * @return
     */
    public String sendRequest(AIReqDTO dto) {
        log.info("AI请求开始，模型: {}，内容: {}", dto.getName(), dto.getContent());
        // 创建HttpClient实例
        String content = dto.getContent().trim();
        AIProperties.AI ai = aiProperties.getAis().get(dto.getName());
        HttpClient httpClient = HttpClients.createDefault();
        Gson gson = new Gson();
        //获取本次对话的snowID
        long snowId = snowflakeIdGenerator.nextId();
        //将本次对话存入对话消息表
        ChatMessage message = new ChatMessage(dto.getSessionId(), content, true, snowId);
        int flag = messageDao.insertMessage(message);
        if (flag == 0) {
            log.error("插入错误");
        }
        if (message != null) log.info("用户消息插入成功，内容: {}", content);
        //查询历史记录
        List<ChatMessage> chatMessages = messageDao.getMessageUserBySessionId(dto.getSessionId());
        List<Message> messages = new ArrayList();
        messages.add(new Message("system", "You are a helpful assistant"));
        for (ChatMessage chatMessage : chatMessages) {
            if (chatMessage.isUser()) {
                log.debug("历史消息: {}", chatMessage.getMessage());
                messages.add(new Message("user", chatMessage.getMessage()));
            }
        }
        try {
            //构建请求对象
            Map<String, Object> map = new HashMap();
            map.put("Content-Type", "application/json");
            map.put("Authorization", "Bearer " + ai.getKey());
            //设置请求头
            HttpPost httpPost = new HttpPost(ai.getUrl());
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
            //设置请求体
            ChatRequest chatRequest = new ChatRequest(ai.getModel(), messages, 1, 2048);
            String json = gson.toJson(chatRequest);
            httpPost.setEntity(EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON).setText(json).build());
            //发送请求
            HttpResponse response = httpClient.execute(httpPost);
            InputStreamReader isr = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8);
            ChatResponse chatResponse = gson.fromJson(isr, ChatResponse.class);
            List<Choice> choices = chatResponse.getChoices();
            String result = choices.get(0).getMessage().getContent();
            //存入
            messageDao.insertMessage(new ChatMessage(dto.getSessionId(), result, false, snowId));
            log.info("AI响应成功，模型: {}，响应内容: {}", dto.getName(), result);
            //返回文本结果
            return result;
        } catch (Exception e) {
            log.error("AI请求失败，模型: {}，内容: {}，异常: {}", dto.getName(), content, e.getMessage(), e);
            return "请求失败！请重试....";
        }
    }
    /**
     * 创建新的会话
     * @param title 会话标题
     * @return
     */
    @Override
    public SessionVO createSession(String title) {
        Long userId = 8L;
        ChatSession session = new ChatSession(userId, title);
        log.info("Creating new chat session for user: {}, title: {}", userId, title);
        chatSessionDao.insertSession(session);
        log.info("Chat session created successfully, sessionId: {}", session.getSessionId());
        return new SessionVO(session.getSessionId(), title, session.getCreateTime());
    }

    /**
     * 根据用户ID获取会话列表
     * @return 会话列表
     */
    @Override
    public List<ChatSession> getSessionsByUserId() {
        Long userId = 8L;
        log.info("Getting chat sessions for user: {}", userId);
        List<ChatSession> sessions = chatSessionDao.getSessionsByUserId(userId);
        log.info("Found {} chat sessions for user: {}", sessions.size(), userId);
        return sessions;
    }

    /**
     * 删除会话
     * @param sessionId 会话ID
     */
    @Override
    public void deleteSession(Long sessionId) {
        Long userId = 8L;
        log.info("Deleting chat session: {} for user: {}", sessionId, userId);
        chatSessionDao.deleteSession(sessionId, userId);
        log.info("Chat session deleted successfully");
    }

    /**
     * 获取该用户的所有的会话
     * @return
     */
    @Override
    public List<SessionVO> getAllSessions() {
        Long userId = 8L;
        //获取该用户的所有的会话
        List<ChatSession> sessions = chatSessionDao.getSessionsByUserId(userId);
        List<SessionVO> sessionVOs = new ArrayList<>();
        if (sessions != null) {
            for (ChatSession session : sessions) {
                SessionVO sessionVO = new SessionVO();
                sessionVO.setSessionId(session.getSessionId());
                sessionVO.setTitle(session.getTitle());
                sessionVO.setCreateTime(session.getCreateTime());
                sessionVOs.add(sessionVO);
            }
        }
        return sessionVOs;
    }

    @Override
    public MessageVO getMessagesBySessionId(Long sessionId) {
        List<ChatMessage> messages = messageDao.getMessageUserBySessionId(sessionId);
        String title = chatSessionDao.getSessionBySessionId(sessionId).getTitle();
        if (messages == null) {
            return null;
        }
        List<Message> messageList = new ArrayList<>();
        for (ChatMessage message : messages) {
            messageList.add(new Message(message.isUser() ? "user" : "assistant", message.getMessage()));
        }
        return new MessageVO(sessionId, title, messageList);
    }
}
