package com.ex.seckill.utils;

import java.io.Serializable;

public class ServerResponse<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    private ServerResponse(int code) {
        this.code = code;
    }

    private ServerResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static <T> ServerResponse<T> createBySuccess() {
        return new
                ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new
                ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T>
    createBySuccessMessage(String msg) {
        return new
                ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T>
    createBySuccessCodeMessage(String msg, T data) {
        return new ServerResponse<T>
                (ResponseCode.SUCCESS.getCode(), msg, data)
                ;
    }

    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>
                (ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc())
                ;
    }

    public static <T> ServerResponse<T> createByErrorMessage
            (String errorMessage) {
        return new
                ServerResponse<T>
                (ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage
            (int erroCode, String errorMessage) {
        return new ServerResponse<T>(erroCode, errorMessage);
    }


}