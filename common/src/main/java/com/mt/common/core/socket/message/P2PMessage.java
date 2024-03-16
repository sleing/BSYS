package com.mt.common.core.socket.message;

public class P2PMessage extends Message {
    public P2PMessage() {
        this.setStompCommand(STOMPCommand.P2P);
    }

    public String getFromUser() {
        return this.headers.get("fromUser");
    }

    public void setFromUser(String fromUser) {
        this.headers.put("fromUser", fromUser);
    }

    public String getToUser() {
        return this.headers.get("toUser");
    }

    public void setToUser(String toUser) {
        this.headers.put("toUser", toUser);
    }

}
