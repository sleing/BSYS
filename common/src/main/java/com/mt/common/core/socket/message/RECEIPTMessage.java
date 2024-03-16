package com.mt.common.core.socket.message;

import java.util.HashMap;
import java.util.Map;

public class RECEIPTMessage extends  Message{
    public RECEIPTMessage() {
        this.setStompCommand(STOMPCommand.RECEIPT);
    }

    public String getReceiptId() {  return this.headers.get("receipt-id");  }
    public void setReceiptId(String receiptId) { this.headers.put("receipt-id",receiptId); }
    public String getNone() {  return this.headers.get("none");  }
    public void setNone(String none) { this.headers.put("none",none); }

}
