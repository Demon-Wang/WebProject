package com.example.demo.utils;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException{
    private String msg;
    private ResultCode code;
    public APIException() {
        this.code=ResultCode.ERROR;
        this.msg="接口错误";
    }

    public APIException(ResultCode code,String msg) {
        this.code=code;
        this.msg=msg;
    }
    public APIException(String msg) {
        this.code=ResultCode.ERROR;
        this.msg=msg;
    }
}
