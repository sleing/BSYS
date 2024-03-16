package com.mt.common.core.socket.message;

import java.util.HashMap;
import java.util.Map;

public class DISCONNECTMessage  extends  Message{
    public DISCONNECTMessage() {
        this.setStompCommand(STOMPCommand.DISCONNECT);
    }
    public String getNone() {  return this.headers.get("none");  }
    public void setNone(String none) { this.headers.put("none",none); }
    public String getReceipt() {  return this.headers.get("receipt");  }
    public void setReceipt(String receipt) { this.headers.put("receipt",receipt); }
}
