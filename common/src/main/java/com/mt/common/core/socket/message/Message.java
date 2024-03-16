package com.mt.common.core.socket.message;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Message  {

    //public static final String END="^@";

    protected STOMPCommand stompCommand;
    protected Map<String,String> headers = new HashMap<>();
    private Object body;
    private String bodyJson;

    public STOMPCommand getStompCommand() {
        return stompCommand;
    }

    public void setStompCommand(STOMPCommand stompCommand) {
        this.stompCommand = stompCommand;
    }

    public String getContentLength() {
        return this.headers.get("content-length");
    }

    public void setContentLength(String contentLength) {
        this.headers.put("content-length",contentLength);
    }


    public String getContentType() {
        return this.headers.get("content-type");
    }

    public void setContentType(String contentLength) {
        this.headers.put("content-type",contentLength);
    }
    public String getReceipt() {
        return this.headers.get("receipt");
    }

    public void setReceipt(String contentLength) {
        this.headers.put("receipt",contentLength);
    }

    public Object getBody() {
        return body;
    }

    public String getBodyJson() {
        return bodyJson;
    }

    public void setBodyJson(String bodyJson) {
        this.bodyJson = bodyJson;
    }

    public void setBody(Object body) {
        this.body = body;
    }
    public void addHeader(String name,String value)
    {
        this.headers.put(name,value);
    }
    public String getHeader(String name)
    {
        return this.headers.get(name);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String toStomp()
    {
        StringBuilder stomp = new StringBuilder();

        String content = "";
        if(this.body != null)
        {
            content = JSON.toJSONString(this.body)+"\n";
        }else if(this.bodyJson != null)
        {
            content = this.bodyJson;
        }
        this.setContentLength(content.length()+"");

        if(this.getContentType() == null)
        {
            this.setContentType("application/json");
        }

        stomp.append(this.stompCommand).append("\n");

        headers.entrySet().forEach((entry)->{
            if(entry.getValue() != null)
            {
                stomp.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
            }
        });
        stomp.append("\n");

        stomp.append(content);

        //stomp.append(Message.END);

        return stomp.toString();
    }
    public Message parse(Class clazz){
        if(StringUtils.isEmpty(this.getBodyJson()))
        {
            this.body = null;
            return this ;
        }

        if(clazz == null)
        {
            this.body = JSON.parseObject(this.getBodyJson());
        }
        else
        {
            this.body = JSON.parseObject(this.getBodyJson(),clazz);
        }

        return this;
    }

}
