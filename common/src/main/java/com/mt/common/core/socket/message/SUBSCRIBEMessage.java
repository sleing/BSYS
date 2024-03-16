package com.mt.common.core.socket.message;

import java.util.HashMap;
import java.util.Map;

public class SUBSCRIBEMessage  extends  Message{
    public SUBSCRIBEMessage() {
        this.setStompCommand(STOMPCommand.SUBSCRIBE);
    }

    public String getDestination() {  return this.headers.get("destination");  }
    public void setDestination(String destination) { this.headers.put("destination",destination); }
    public String getId() {  return this.headers.get("id");  }
    public void setId(String id) { this.headers.put("id",id); }
    public String getAck() {  return this.headers.get("ack");  }
    public void setAck(String ack) { this.headers.put("ack",ack); }


}
