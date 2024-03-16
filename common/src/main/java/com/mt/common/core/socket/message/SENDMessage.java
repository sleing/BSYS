package com.mt.common.core.socket.message;

import java.util.HashMap;
import java.util.Map;

public class SENDMessage extends Message {
    public SENDMessage() {
        this.setStompCommand(STOMPCommand.SEND);
    }

    public String getDestination() {
        return this.headers.get("destination");
    }

    public void setDestination(String destination) {
        this.headers.put("destination", destination);
    }

    public String getTransaction() {
        return this.headers.get("transaction");
    }

    public void setTransaction(String transaction) {
        this.headers.put("transaction", transaction);
    }



}
