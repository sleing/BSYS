package com.mt.common.core.socket.message;

import java.util.HashMap;
import java.util.Map;

public class CONNECTEDMessage extends  Message{
    public CONNECTEDMessage() {
        this.setStompCommand(STOMPCommand.CONNECTED);
    }

    public String getVersion() {  return this.headers.get("version");  }
    public void setVersion(String version) { this.headers.put("version",version); }
    public String getSession() {  return this.headers.get("session");  }
    public void setSession(String session) { this.headers.put("session",session); }
    public String getServer() {  return this.headers.get("server");  }
    public void setServer(String server) { this.headers.put("server",server); }
    public String getHeartBeat() {  return this.headers.get("heart-beat");  }
    public void setHeartBeat(String heartBeat) { this.headers.put("heart-beat",heartBeat); }
}
