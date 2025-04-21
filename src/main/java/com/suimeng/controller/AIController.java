package com.suimeng.controller;

import com.suimeng.domain.pojo.Result;
import com.suimeng.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ai")
public class AIController {
    @Autowired
    private AIService aIService;
    @PostMapping ("/client")
    public Result<String> deepSeek(@RequestParam("content") String content,@RequestParam("name") String name) {
        return Result.success(aIService.sendRequest(content, name));
    }
}
