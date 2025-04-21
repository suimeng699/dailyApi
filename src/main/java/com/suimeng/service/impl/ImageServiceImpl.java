package com.suimeng.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.meitu.openai.common.Signer;
import com.suimeng.domain.pojo.image.Image;
import com.suimeng.domain.pojo.image.ImageParam;
import com.suimeng.domain.pojo.image.ImageResponse;
import com.suimeng.properties.MeituProperties;
import com.suimeng.service.ImageService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MeituProperties meituProperties;

    /**
     * 将key和secret通过美图的api进行鉴权
     * @param key
     * @param secret
     * @return
     */
    private int getMeiTuAuthority(String key, String secret) {
        Signer signer = new Signer(key,secret);

        String url = "http://openapi.meitu.com/demo/authorization";
        String method = "POST"; // method 请保证和request 请求一致
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put(Signer.HeaderHost, "openapi.meitu.com");

        //如果body 为空请将body 设置为null，不要设置空字符串 ""
        String body = null;

        try {
            Map<String,String> signedHeaders = signer.sign(url, method, headers, body);
            System.out.println("signedHeader: "+signedHeaders);
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(method);
            for (Map.Entry<String,String> entry : signedHeaders.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            connection.setDoOutput(true);
            if (body!=null){
                connection.getOutputStream().write(body.getBytes());
            }
            if(connection.getResponseCode() == 200) return 1;
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 抠图
     * @param url
     * @param res
     */
    @Override
    public void getImageCutout(String url , HttpServletResponse res){
        //鉴权
        String key = meituProperties.getMeitus().get("cutout").getKey();
        String secret = meituProperties.getMeitus().get("cutout").getSecret();
        if(redisTemplate.opsForValue().get("meitu-cutout") == null) {
            if(getMeiTuAuthority(key,secret) == 1) {
                redisTemplate.opsForValue().set("meitu-cutout", "true");
                System.out.println("鉴权成功");
            }
            else {
                System.out.println("鉴权失败");
                return;
            }
        }
        //创建数据
        Image image = new Image();
        ImageParam params = new ImageParam();
        image.setParams(params);
        image.setInitImages(url);
        //解析数据
        ObjectMapper objectMapper = new ObjectMapper();
        //创建客户端
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://openapi.meitu.com/api/v1/sdk/sync/push?api_key=" + key +"&api_secret=" + secret);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON).setText(image.toString()).build());
        try {
            HttpResponse httpResponse = client.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                System.out.println("请求成功");
                String responseBody = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(responseBody);
                ImageResponse imageResponse = objectMapper.readValue(responseBody, ImageResponse.class);
                String resUrl = imageResponse.getData().getResult().getUrls().get(0);
                System.out.println("resUrl:" + resUrl);
                byte[] bytes = getPicture(resUrl);
                res.getOutputStream().write(bytes);
            }
            else {
                System.out.println("请求失败" + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过url链接得到图片字节数组
     * @param resUrl
     * @return
     */
    private byte[] getPicture(String resUrl) {
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(resUrl);
        int len = 0;
        byte[] bytes = new byte[1024];
        ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
        try {
            HttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("请求成功");
                InputStream inputStream = response.getEntity().getContent();
                while ((len = inputStream.read(bytes)) != -1) {
                    buffer.append(bytes, 0, len);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toByteArray();
    }


}
