package com.suimeng.service;

import com.suimeng.domain.pojo.image.Image;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;

import java.io.IOException;

public interface ImageService {

    void getImageCutout(String url , HttpServletResponse response);
}
