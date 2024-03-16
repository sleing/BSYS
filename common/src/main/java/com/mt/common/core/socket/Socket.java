package com.mt.common.core.socket;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.security.LoginUser;
import com.mt.common.core.socket.message.*;
import org.springframework.stereotype.Component;


import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


@ServerEndpoint("/WebSocket/{device}")
@Component
public class Socket {
    static Log log = LogFactory.get(Socket.class);

    private Session session;
    private String username = "";
    private String device;


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return this.username + "$$" + this.device;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("device") String device) throws IOException {

        try {
            this.device = device;
            LoginUser loginUser = SocketManager.getLoginUser();
            if (loginUser == null) {
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "用户没有登录，非法访问"));
                return;
            }
            this.session = session;
            this.username = loginUser.getUsername();
            SocketManager.add(this);
            try {
                sendMessage("connected");
            } catch (IOException e) {
                log.error("用户:" + username + ",网络异常!!!!!!");
                throw new BusinessException("Websocket连接出错，原因是:" + e.getMessage(), e);
            }
        } finally {
            SocketManager.clearLoginUser();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            SocketManager.remove(this);

        } finally {
            SocketManager.clearLoginUser();
        }

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        try {

            log.info("用户消息:" + username + ",报文:" + message);
            if ("mt".equals(message)) {
                this.sendMessage("mt");
            } else {
                this.handMessage(message);
            }
        } finally {
            SocketManager.clearLoginUser();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        try {
            log.error("用户错误:" + this.username + ",原因:" + error.getMessage());
            error.printStackTrace();
        } finally {
            SocketManager.clearLoginUser();
        }
    }

    private void handMessage(String messageStr) throws IOException {
        Message message = MessageParse.parse(messageStr);
        if (message == null) {
            throw new BusinessException(messageStr + "   是错误的消息格式");
        }
        switch (message.getStompCommand()) {
            case SEND:
                handleSEND((SENDMessage) message);
                break;
            case P2P:
                handleP2P((P2PMessage) message);
                break;

            case SUBSCRIBE:
                handleSUBSCRIBE((SUBSCRIBEMessage) message);
                break;
            case UNSUBSCRIBE:
                handleUNSUBSCRIBE((UNSUBSCRIBEMessage) message);
                break;
            case ACK:
                handleACK((ACKMessage) message);
                break;
            case NACK:
                handleNACK((NACKMessage) message);
                break;
            case DISCONNECT:
                handleDISCONNECT((DISCONNECTMessage) message);
                break;
            case CONNECT:
                handleCONNECT((CONNECTMessage) message);
                break;
            case CONNECTED:
                handleCONNECTED((CONNECTEDMessage) message);
                break;
            case MESSAGE:
                handleMESSAGE((MESSAGEMessage) message);
                break;
            case RECEIPT:
                handleRECEIPT((RECEIPTMessage) message);
                break;
            case ERROR:
                handleERROR((ERRORMessage) message);
                break;
        }
    }

    public void handleSEND(SENDMessage sendMessage) throws IOException {
        MESSAGEMessage messageMessage = MESSAGEMessage.fromSENDMessage(sendMessage);
        SocketManager.publish(messageMessage);

        SocketManager.callSubscriptionHandlers(messageMessage.getDestination(), messageMessage.getBodyJson(),this.username,sendMessage.getHeaders());
    }

    public void handleP2P(P2PMessage message) throws IOException {
        String toUser = message.getToUser();
        message.setFromUser(this.username);
        boolean sent = SocketManager.sendMessage(toUser, message);

    }

    public void handleSUBSCRIBE(SUBSCRIBEMessage message) {
        SocketManager.subscribe(message.getDestination(), this);
    }

    public void handleUNSUBSCRIBE(UNSUBSCRIBEMessage message) {
        SocketManager.unsubscribe(message.getDestination(), this);
    }

    public void handleACK(ACKMessage message) {
    }

    public void handleNACK(NACKMessage message) {
    }

    public void handleDISCONNECT(DISCONNECTMessage message) {
    }

    public void handleCONNECT(CONNECTMessage message) {
    }

    public void handleCONNECTED(CONNECTEDMessage message) {
    }

    public void handleMESSAGE(MESSAGEMessage message) {
    }

    public void handleRECEIPT(RECEIPTMessage message) {
    }

    public void handleERROR(ERRORMessage message) {
    }


    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendMessage(Message message) throws IOException {
        this.session.getBasicRemote().sendText(message.toStomp());
    }

}


