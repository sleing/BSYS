package com.mt.common.core.config.jmconfig;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BaseResult {

    //成功状态码
    public static final int OK = 1;
    //失败状态码
    public static final int ERROR = 0;

    //返回码
    private Integer code;
    //返回消息
    private String message;

    //存放数据
    private Object data;
    //其他数据
    private Map<String,Object> other = new HashMap<>();


    public BaseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public BaseResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResult() {

    }

    /**
     * 快捷成功BaseResult对象
     * @param message
     * @return
     */
    public static BaseResult ok(String message){
        return new BaseResult(BaseResult.OK , message);
    }

    public static BaseResult ok(String message, Object data){
        return new BaseResult(BaseResult.OK , message, data );
    }

    /**
     * 快捷失败BaseResult对象
     * @param message
     * @return
     */
    public static BaseResult error(String message){
        return new BaseResult(BaseResult.ERROR , message);
    }

    /**
     * 自定义数据区域
     * @param key
     * @param msg
     * @return
     */
    public BaseResult append(String key , Object msg){
        other.put(key , msg);
        return this;
    }
}

