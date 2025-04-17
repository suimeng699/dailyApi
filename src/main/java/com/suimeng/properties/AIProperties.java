package com.suimeng.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "ai-chat")
public class AIProperties {
    private Map<String, AI> ais = new HashMap<>();

    public Map<String, AI> getAis() {
        return ais;
    }

    public void setAis(Map<String, AI> ais) {
        this.ais = ais;
    }

    public static class AI {
        private String name;
        private String key;
        private String url;
        private String model;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }
}
