package com.suimeng.domain.pojo.image;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Result {
        @JsonProperty("urls")
        List<String> urls;

        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }

}
