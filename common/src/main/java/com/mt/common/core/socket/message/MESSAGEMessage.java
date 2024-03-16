package com.mt.common.core.socket.message;

import cn.hutool.core.bean.BeanUtil;

import java.util.HashMap;
import java.util.Map;

public class MESSAGEMessage  extends  Message{
    public MESSAGEMessage() {
        this.setStompCommand(STOMPCommand.MESSAGE);
    }

    public String getDestination() {  return this.headers.get("destination");  }
    public void setDestination(String destination) { this.headers.put("destination",destination); }
    public String getMessageId() {  return this.headers.get("message-id");  }
    public void setMessageId(String messageId) { this.headers.put("message-id",messageId); }
    public String getSubscription() {  return this.headers.get("subscription");  }
    public void setSubscription(String subscription) { this.headers.put("subscription",subscription); }
    public String getAck() {  return this.headers.get("ack");  }
    public void setAck(String ack) { this.headers.put("ack",ack); }

    public static MESSAGEMessage fromSENDMessage(SENDMessage sendMessage)
    {
        MESSAGEMessage messageMessage = new MESSAGEMessage();
        BeanUtil.copyProperties(sendMessage,messageMessage);

        return messageMessage;
    }
}
