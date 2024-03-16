package com.mt.common.core.socket.message;

import java.util.HashMap;
import java.util.Map;

public class ACKMessage extends  Message{
    public ACKMessage() {
        this.setStompCommand(STOMPCommand.ACK);
    }

    public String getId() {  return this.headers.get("id");  }
    public void setId(String id) { this.headers.put("id",id); }
    public String getTransaction() {  return this.headers.get("transaction");  }
    public void setTransaction(String transaction) { this.headers.put("transaction",transaction); }
}
