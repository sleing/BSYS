package com.mt.common.core.socket;

public class SubscriptionHandlerHolder {
    private SubscriptionHandler subscriptionHandler;
    private Class  bodyClazz;

    public SubscriptionHandlerHolder(SubscriptionHandler subscriptionHandler, Class bodyClazz) {
        this.subscriptionHandler = subscriptionHandler;
        this.bodyClazz = bodyClazz;
    }

    public SubscriptionHandler getSubscriptionHandler() {
        return subscriptionHandler;
    }

    public void setSubscriptionHandler(SubscriptionHandler subscriptionHandler) {
        this.subscriptionHandler = subscriptionHandler;
    }

    public Class getBodyClazz() {
        return bodyClazz;
    }

    public void setBodyClazz(Class bodyClazz) {
        this.bodyClazz = bodyClazz;
    }
}
