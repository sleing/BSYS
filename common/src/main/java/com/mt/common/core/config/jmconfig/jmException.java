package com.mt.common.core.config.jmconfig;

public class jmException extends RuntimeException{
    private Integer code;
    private String message;

    public jmException(Integer code, String message) {
        super(message);
        this.code = code;

    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
