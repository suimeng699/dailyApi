package com.suimeng.service.impl;

import com.google.gson.Gson;
import com.suimeng.properties.AIProperties;
import com.suimeng.domain.pojo.ChatRequest;
import com.suimeng.domain.pojo.ChatResponse;
import com.suimeng.domain.pojo.Choice;
import com.suimeng.domain.pojo.Message;
import com.suimeng.service.AIService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
public class AIServiceImpl implements AIService {
    @Autowired
    private AIProperties aiProperties;
    public String sendRequest(String content, String name) {
        // 创建HttpClient实例
        AIProperties.AI ai = aiProperties.getAis().get(name);
        HttpClient httpClient = HttpClients.createDefault();
        Gson gson = new Gson();
        List<Message> messages = new ArrayList();
        messages.add(new Message("system", "You are a helpful assistant"));
        messages.add(new Message("user", content));
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
            //返回文本结果
            return choices.get(0).getMessage().getContent();
        } catch (Exception e) {
//            log.info("请求失败！请重试....", e);
            return "请求失败！请重试....";
        }
    }
}
