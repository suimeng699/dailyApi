package com.suimeng.domain.pojo.image;

import java.util.HashMap;
import java.util.Map;

public class Image {
    //必选	sync_timeout	Interger	请求超时时间，单位秒
    private Integer syncTimeout = 10;
    //必选	task_type	String	任务类型，固定“mtlab”
    private final String taskType = "mtlab";
    //必选	init_images	Object	原始图片地址
    private Map<String,String> initImages;
    //必选	task	String	任务地址，固定“v1/sod”
    private final String task = "v1/sod";
    //必选	params	String	额外参数的json信息
    private Map<String,ImageParam> params;

    public Integer getSyncTimeout() {
        return syncTimeout;
    }

    public void setSyncTimeout(Integer syncTimeout) {
        this.syncTimeout = syncTimeout;
    }

    public Map<String, String> getInitImages() {
        return initImages;
    }

    public void setInitImages(String url) {
        if (this.initImages == null) {
            this.initImages = new HashMap<>();
        }
        this.initImages.put("url", url);
    }

    public Map<String, ImageParam> getParams() {
        return params;
    }

    public void setParams(ImageParam params) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        this.params.put("parameter", params);
    }

    @Override
    public String toString() {
        String init_images = "{";
        if (this.initImages != null && this.initImages.size() > 0) {
            for (Map.Entry<String, String> entry : this.initImages.entrySet()) {
                init_images += "\""+entry.getKey() + "\":\"" + entry.getValue() + "\",";
            }
            init_images = init_images.substring(0, init_images.length() - 1);
        }
        init_images += "}";
        //"params":"{"parameter":{"rsp_media_type":"url"}}"
        String params = "{\\\"parameter\\\":";
        if (this.params != null && this.params.size() > 0) {
            for (Map.Entry<String, ImageParam> entry : this.params.entrySet()) {
                params += entry.getValue().toString();
            }
        }
        params += "}";
        return "{" +
                "\"sync_timeout\":" + syncTimeout +
                ",\"task_type\":\"" + taskType +
                "\",\"init_images\":[" + init_images +
                "],\"task\":\"" + task +
                "\",\"params\":\"" + params +
                "\"}";
    }
}
