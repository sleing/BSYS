package com.mt.common.core.utils;

/**
 * @ClassName Message
 * @Description TODO
 * @Author ycs
 * @Date 2021/9/16 16:34
 * @Version 1.0
 */
public class Message {

    /**
     * code
     */
    private String code;

    /**
     * args
     */
    private String[] args;

    public Message(String code) {
        this.code = code;
    }

    public Message(String code, String... args) {
        this.code = code;
        this.args = args;
    }

    @Override
    public String toString() {
        return MessageUtil.getMessage(this.code, this.args);
    }

    /**
     * 取得code
     *
     * @return
     */
    public String getCode() {
        return this.code;
    }
}
