package com.suimeng.domain.pojo;

public class  Result<T> {
    private String code;
    private String msg;
    private T data;

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result success(T data) {
        return new Result(Code.SUCCESS, "", data);
    }
    public static <T> Result success(String msg,T data) {
        return new Result(Code.SUCCESS, msg, data);
    }
    public static <T> Result fail(String msg) {
        return new Result(Code.FAIL, msg, null);
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
