package com.suimeng.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.ByteArrayBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Connection {
    /**
     * 获取请求Response（参数为：请求方式-大写  ，请求路径，请求路径参数，请求头，请求体-如果是POST请求）
     * @param method
     * @param url
     * @param urlParams
     * @param headers
     * @param entity
     * @return
     */
    public static HttpResponse getResponse(String method, String url, Map<String, String> urlParams, Map<String, String> headers, String entity) {
        //创建客户端
        HttpClient client = HttpClients.createDefault();
        //处理url路径参数
        String urlParamsStr = "";
        if (urlParams != null && !urlParams.isEmpty()) {
            for (Map.Entry<String, String> entry : urlParams.entrySet()) {
                urlParamsStr += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        if (urlParamsStr.length() > 0) urlParamsStr = urlParamsStr.substring(1, urlParamsStr.length());
        url = url + "?" + urlParamsStr;
        HttpResponse httpResponse = null;
        //处理Post请求
        if (method.equals("POST")) {
            HttpPost httpPost = new HttpPost(url);
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            httpPost.setEntity(EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON).setText(entity).build());
            try {
                httpResponse = client.execute(httpPost);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        //处理Get请求
        if (method.equals("GET")) {
            HttpGet httpGet = new HttpGet(url);
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            try {
                httpResponse = client.execute(httpGet);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("请求状态码：" + httpResponse.getStatusLine().getStatusCode());
        return httpResponse;
    }
    /**
     * 通过url链接得到图片字节数组
     * @param resUrl
     * @return
     */
    public static byte[] getFile4Url(String resUrl) {
        int len = 0;
        byte[] bytes = new byte[1024];
        ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
        try {
            HttpResponse response = Connection.getResponse("GET", resUrl, null, null, null);
            if (response.getStatusLine().getStatusCode() == 200) {
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
