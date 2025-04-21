package com.suimeng.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "meitu")
@Component
public class MeituProperties {
    private Map<String,Meitu> meitus = new HashMap<>();

    public Map<String, Meitu> getMeitus() {
        return meitus;
    }

    public void setMeitus(Map<String, Meitu> meitus) {
        this.meitus = meitus;
    }

    public static class Meitu {
        private String key;
        private String secret;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
}
