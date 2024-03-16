package com.mt.common.core.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义异常基类
 * Created by wangfan on 2018-02-22 11:29
 */
public abstract class IException extends RuntimeException {
    private static final long serialVersionUID = -1582874427218948396L;
    private Long code;
    protected Map<String,Object> extendedData = new HashMap<String, Object>();

    public IException() {
    }

    public IException(String message) {
        super(message);
    }
    public IException(Throwable cause) {
        super(cause);
        if(cause instanceof IException)
        {
            this.extendedData.putAll(((IException)cause).getExtendedData());
        }
    }
    public IException(Long code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        if(cause instanceof IException)
        {
            this.extendedData.putAll(((IException)cause).getExtendedData());
        }
    }

    public IException( Long code,String message) {
        super(message);
        this.code = code;
    }

    public IException(String message, Throwable cause) {
        super(message, cause);
        if(cause instanceof IException)
        {
            this.extendedData.putAll(((IException)cause).getExtendedData());
        }
    }
    /**
     * @return the extendedData
     */
    public Map<String,Object> getExtendedData() {
        return extendedData;

    }
    /**
     * @param extendedData the extendedData to set
     */
    public void setExtendedData(Map<String,Object> extendedData) {
        this.extendedData = extendedData;
    }
    /*
     * 增加扩展数据
     */
    public void putExtendedData(String key,Object value)
    {

        this.extendedData.put(key, value);
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }


}
