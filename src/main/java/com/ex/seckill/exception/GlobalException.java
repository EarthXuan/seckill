package com.ex.seckill.exception;

public class GlobalException extends RuntimeException{
    private static final long serialVersionUID = -1;
    private String msg;
    public GlobalException(String msg){
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }
}
