package com.suimeng.controller;

import com.suimeng.dao.MessageDao;
import com.suimeng.domain.dto.AIReqDTO;
import com.suimeng.domain.dto.CreateSessionDTO;
import com.suimeng.domain.pojo.ChatSession;
import com.suimeng.domain.pojo.Result;
import com.suimeng.domain.vo.MessageVO;
import com.suimeng.domain.vo.SessionVO;
import com.suimeng.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * AI相关接口控制器
 * 提供AI客户端请求接口
 */
@RestController
@RequestMapping("ai")
public class AIController {
    private static final Logger logger = LoggerFactory.getLogger(AIController.class);
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private AIService aiService;

    /**
     * AI客户端请求接口
     * @param dto AI请求参数
     * @return AI响应结果
     */
    @PostMapping("/client")
    public Result<String> getClient(@RequestBody AIReqDTO dto) {
        logger.info("AI客户端请求，内容: {}，名称: {} ,sessionId:{}", dto.getContent(), dto.getName(),dto.getSessionId());
        String result = aiService.sendRequest(dto);
        logger.info("AI响应结果: {}", result);
        return Result.success(result);
    }

    /**
     * 新建会话
     * @param dto
     * @return
     */
    @PostMapping("/session/add")
    public Result<SessionVO> createSession(@RequestBody CreateSessionDTO dto) {
        Long userId = 8L;
        logger.info("Creating new chat session request, userId: {}, title: {}", userId, dto.getTitle());
        SessionVO session = aiService.createSession(dto.getTitle());
        return Result.success(session);
    }

    /**
     * 获取会话对话列表
     * @return
     */
    @GetMapping("/session/list")
    public Result<List<SessionVO>> getSessions() {
        List<SessionVO> messages = aiService.getAllSessions();
        return Result.success(messages);
    }

    /**
     * 删除会话
     * @param sessionId
     * @return
     */
    @DeleteMapping("/session/{sessionId}")
    public Result<String> deleteSession(@PathVariable Long sessionId) {
        Long userId = 8L;
        logger.info("Deleting chat session request, sessionId: {}, userId: {}", sessionId, userId);
        aiService.deleteSession(sessionId);
        return Result.success("删除会话成功");
    }

    @GetMapping("/session/{sessionId}")
    public Result<MessageVO> getSession(@PathVariable Long sessionId) {
        Long userId = 8L;
        logger.info("Getting chat session request, sessionId: {}, userId: {}", sessionId, userId);
        MessageVO vo = aiService.getMessagesBySessionId(sessionId);
        return Result.success(vo);
    }
}
