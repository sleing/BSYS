package com.mt.common.core.socket.message;

import com.mt.common.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

public class MessageParse {

    public static Message parse(String messageStr) {
        if (StringUtils.isEmpty(messageStr)) return null;
        String headerStr = null;
        String bodyStr = null;
        //messageStr = StringUtils.removeEnd(messageStr, Message.END);
        int headerEndIndex = StringUtils.indexOf(messageStr, "\n\n");

        if (headerEndIndex == -1) {
            headerStr = messageStr;
        } else {
            headerStr = StringUtils.substring(messageStr, 0, headerEndIndex);
            bodyStr = StringUtils.substring(messageStr, headerEndIndex + 2);
        }

        String[] lines = StringUtils.split(headerStr, "\n");
        if (lines == null || lines.length == 0) {
            return null;
        }
        STOMPCommand command = null;
        command = Enum.valueOf(STOMPCommand.class, lines[0]);

        Message message = null;
        switch (command) {
            case SEND:
                message = new SENDMessage();
                break;
            case P2P:
                message = new P2PMessage();
                break;
            case SUBSCRIBE:
                message = new SUBSCRIBEMessage();
                break;
            case UNSUBSCRIBE:
                message = new UNSUBSCRIBEMessage();
                break;
            case ACK:
                message = new ACKMessage();
                break;
            case NACK:
                message = new NACKMessage();
                break;
            case DISCONNECT:
                message = new DISCONNECTMessage();
                break;
            case CONNECT:
                message = new CONNECTMessage();
                break;
            case CONNECTED:
                message = new CONNECTEDMessage();
                break;
            case MESSAGE:
                message = new MESSAGEMessage();
                break;
            case RECEIPT:
                message = new RECEIPTMessage();
                break;
            case ERROR:
                message = new ERRORMessage();
                break;
            default:
                throw new BusinessException(lines[0] + "不是正确的消息类型");
        }
        for (int i = 1; i < lines.length; i++) {
            message.addHeader(StringUtils.substringBefore(lines[i], ":"), StringUtils.substringAfter(lines[i], ":"));
        }
        message.setBodyJson(bodyStr);

        return message;
    }

}
