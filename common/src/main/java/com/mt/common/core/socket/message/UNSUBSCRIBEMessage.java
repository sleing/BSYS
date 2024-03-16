package com.mt.common.core.socket.message;

import java.util.HashMap;
import java.util.Map;

public class UNSUBSCRIBEMessage  extends  Message{
    public UNSUBSCRIBEMessage() {
        this.setStompCommand(STOMPCommand.UNSUBSCRIBE);
    }

    public String getDestination() {  return this.headers.get("destination");  }
    public void setDestination(String destination) { this.headers.put("destination",destination); }
    public String getId() {  return this.headers.get("id");  }
    public void setId(String id) { this.headers.put("id",id); }
    public String getNone() {  return this.headers.get("none");  }
    public void setNone(String none) { this.headers.put("none",none); }
}
