package com.suimeng.domain.pojo.image;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Data {
        @JsonProperty("result")
        Result result;

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }
}
