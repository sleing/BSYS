package com.mt.common.core.socket.message;

import java.util.HashMap;
import java.util.Map;

public class CONNECTMessage  extends  Message{
    public CONNECTMessage() {
        this.setStompCommand(STOMPCommand.CONNECT);
    }

    public String getAcceptVersion() {  return this.headers.get("accept-version");  }
    public void setAcceptVersion(String acceptVersion) { this.headers.put("accept-version",acceptVersion); }
    public String getHost() {  return this.headers.get("host");  }
    public void setHost(String host) { this.headers.put("host",host); }
    public String getLogin() {  return this.headers.get("login");  }
    public void setLogin(String login) { this.headers.put("login",login); }
    public String getPasscode() {  return this.headers.get("passcode");  }
    public void setPasscode(String passcode) { this.headers.put("passcode",passcode); }
    public String getHeartBeat() {  return this.headers.get("heart-beat");  }
    public void setHeartBeat(String heartBeat) { this.headers.put("heart-beat",heartBeat); }
}
