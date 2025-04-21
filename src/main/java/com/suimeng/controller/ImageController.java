package com.suimeng.controller;

import com.suimeng.domain.pojo.Result;
import com.suimeng.domain.pojo.image.Image;
import com.suimeng.service.ImageService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("image")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @PostMapping ("/getImageCutout")
    public Result<String> getImageCutout(@RequestParam("url") String url , HttpServletResponse response)
    {
        imageService.getImageCutout(url , response);
        return Result.success(null);
    }
}
