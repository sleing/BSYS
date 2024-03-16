package com.mt.common.core.socket.message;

import java.util.HashMap;
import java.util.Map;

public class ERRORMessage  extends  Message{
    public ERRORMessage() {
        this.setStompCommand(STOMPCommand.ERROR);
    }

    public String getNone() {  return this.headers.get("none");  }
    public void setNone(String none) { this.headers.put("none",none); }
    public String getMessage() {  return this.headers.get("message");  }
    public void setMessage(String message) { this.headers.put("message",message); }
}
