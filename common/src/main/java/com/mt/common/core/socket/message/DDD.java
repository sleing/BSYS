package com.mt.common.core.socket.message;

import cn.hutool.Hutool;
import cn.hutool.core.util.HashUtil;
import com.mt.common.core.utils.StringUtils;


import java.util.List;

public class DDD {
    public static void main(String[] args) {

        String[] ddd = new String[]{"CONNECT or STOMP",
                "accept-version",
                "host",
                "login",
                "passcode",
                "heart-beat",
                "",
                "CONNECTED",
                "version",
                "session",
                "server",
                "heart-beat",
                "",
                "SEND",
                "destination",
                "transaction",
                "SUBSCRIBE",
                "destination",
                "id",
                "ack",
                "",
                "UNSUBSCRIBE",
                "id",
                "none",
                "",
                "ACK NACK",
                "id",
                "transaction",
                "",
                "DISCONNECT",
                "none",
                "receipt",
                "",
                "MESSAGE",
                "destination",
                "message-id",
                "subscription",
                "ack",
                "",
                "RECEIPT",
                "receipt-id",
                "none",
                "",
                "ERROR",
                "none",
                "message",
        };

        for(int i=0;i<ddd.length;i++)
        {

            String name= StringUtils.underlineToCamel(ddd[i]);
            String code ="    public String get"+org.apache.commons.lang3.StringUtils.capitalize(name)+"() {  return this.headers.get(\""+ddd[i]+"\");  }\n" +
                    "    public void set"+org.apache.commons.lang3.StringUtils.capitalize(name)+"(String "+name+") { this.headers.put(\""+ddd[i]+"\","+name+"); }";
            System.out.println(code);
        }
    }
}
