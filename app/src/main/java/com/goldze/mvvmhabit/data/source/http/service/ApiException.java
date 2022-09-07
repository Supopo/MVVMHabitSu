package com.goldze.mvvmhabit.data.source.http.service;

public class ApiException extends RuntimeException {
    private String code;
    private String msg;

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

    public ApiException(String code, String message) {
        this.code = code;
        this.msg = message;
    }
}
