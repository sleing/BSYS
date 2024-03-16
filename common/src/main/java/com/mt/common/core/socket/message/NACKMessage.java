package com.mt.common.core.socket.message;

import java.util.HashMap;
import java.util.Map;

public class NACKMessage  extends  Message{
    public NACKMessage() {
        this.setStompCommand(STOMPCommand.NACK);
    }

    public String getId() {  return this.headers.get("id");  }
    public void setId(String id) { this.headers.put("id",id); }
    public String getTransaction() {  return this.headers.get("transaction");  }
    public void setTransaction(String transaction) { this.headers.put("transaction",transaction); }
}
