package com.suimeng.domain.pojo.image;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知字段
public class ImageResponse {
    @JsonProperty("data")
    private Data data;

    // Getter and Setter
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    // Nested Data class
    @JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知字段
    public static class Data {
        @JsonProperty("result")
        private Result result;

        // Getter and Setter
        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }
    }

    // Nested Result class
    @JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知字段
    public static class Result {
        @JsonProperty("urls")
        private List<String> urls;

        // Getter and Setter
        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }
    }



}
