package com.mt.common.core.socket;

import java.util.Map;

public interface SubscriptionHandler {
    public void  subscriptionHandle(String destination, Object body, String fromUser, Map<String,String> headers);
}
