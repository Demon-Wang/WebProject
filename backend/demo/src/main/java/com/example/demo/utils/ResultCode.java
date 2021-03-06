package com.example.demo.utils;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(1000, "操作成功"),

    FAILED(1001, "响应失败"),

    VALIDATE_FAILED(1002, "参数校验失败"),

    LOGIN_FAILED(1003, "登录失败"),

    ERROR(5000, "未知错误");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
