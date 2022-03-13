package com.zsh.www.result;

public class ResponseResult<T> {
    private int code;
    private String msg;
    private T payload;

    public ResponseResult(ResultCodeEnum resultCodeEnum, T payload) {
        this(resultCodeEnum.getCode(), resultCodeEnum.getCodeMsg(), payload);
    }

    public ResponseResult(int code, String msg, T payload) {
        this.code = code;
        this.msg = msg;
        this.payload = payload;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public static ResponseResult success() {
        return new ResponseResult(ResultCodeEnum.SUCCESS, null);
    }

    public static ResponseResult fail() {
        return new ResponseResult(ResultCodeEnum.FAIL, null);
    }
}
