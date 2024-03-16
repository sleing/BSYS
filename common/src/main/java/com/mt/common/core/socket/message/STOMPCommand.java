package com.mt.common.core.socket.message;

public enum STOMPCommand {
    SEND("SEND"),
    P2P("P2P"),
    SUBSCRIBE("SUBSCRIBE"),
    UNSUBSCRIBE("UNSUBSCRIBE"),
    BEGIN("BEGIN"),
    COMMIT("COMMIT"),
    ABORT("ABORT"),
    ACK("ACK"),
    NACK("NACK"),
    DISCONNECT("DISCONNECT"),
    CONNECT("CONNECT"),
    CONNECTED("CONNECTED"),
    STOMP("STOMP"),
    MESSAGE("MESSAGE"),
    RECEIPT("RECEIPT"),
    ERROR("ERROR");
    // 成员变量
    private String name;

    STOMPCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
