package com.mt.common.core.socket;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.mt.common.core.security.LoginUser;
import com.mt.common.core.socket.message.MESSAGEMessage;
import com.mt.common.core.socket.message.Message;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;


public class SocketManager {
    static Log log = LogFactory.get(SocketManager.class);
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    private static ConcurrentHashMap<String,Socket> sockets = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String,ConcurrentHashMap<String,Socket>> userSockets = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, CopyOnWriteArraySet<String>> subscriptions = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, CopyOnWriteArraySet<SubscriptionHandlerHolder>> subscriptionHandlers = new ConcurrentHashMap<>();
    private static ThreadLocal<LoginUser> loginUserThreadLocal = new ThreadLocal<>();


    public static void  add(Socket socket)
    {
        if (sockets.containsKey(socket.getId())) {
            sockets.remove(socket.getId());
            sockets.put(socket.getId(), socket);
        } else {
            sockets.put(socket.getId(), socket);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        addUserSocket(socket);
    }
    public static void  addUserSocket(Socket socket)
    {
        if (!userSockets.containsKey(socket.getUsername())) {
            userSockets.put(socket.getUsername(),new ConcurrentHashMap<>());
        }
        userSockets.get(socket.getUsername()).put(socket.getDevice(),socket);
    }

    public static void remove(Socket socket)
    {
        if (sockets.containsKey(socket.getId())) {
            sockets.remove(socket.getId());
            //从set中删除
            subOnlineCount();
        }
        if (userSockets.containsKey(socket.getUsername())) {
            userSockets.get(socket.getUsername()).remove(socket.getDevice());
        }

//        subscriptions.values().forEach((sockets ->{
//            sockets.remove(socket);
//        } ));

        log.info("用户退出:" + socket.getId() + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 订阅
     * @param destination
     * @param socket
     */
    public  static void subscribe(String destination,Socket socket)
    {
        if(!subscriptions.containsKey(destination))
        {
            subscriptions.put(destination,new CopyOnWriteArraySet());
        }
        subscriptions.get(destination).add(socket.getId());
    }

    /**
     * 服务端订阅消息
     * @param destination  订阅的主题
     * @param subscriptionHandler 消息接收处理器，即有消息时，调用这个处理器
     * @param bodyClazz 消息内容对应的类，如果消息的内容是个JSON对象，就用这个解析
     */
    public  static void subscribe(String destination,SubscriptionHandler subscriptionHandler,Class bodyClazz)
    {
        SubscriptionHandlerHolder holder = new SubscriptionHandlerHolder(subscriptionHandler,bodyClazz);
        if(!subscriptionHandlers.contains(destination))
        {
            subscriptionHandlers.put(destination,new CopyOnWriteArraySet());
        }
        subscriptionHandlers.get(destination).add(holder);
    }
    /**
     * 取消订阅
     * @param destination
     * @param subscriptionHandler
     */
    public static void unsubscribe(String destination,SubscriptionHandler subscriptionHandler)
    {
        if(!subscriptionHandlers.contains(destination))
        {
            return ;
        }
        subscriptionHandlers.get(destination).remove(subscriptionHandler);
    }
    public static void unsubscribe(String destination,Socket socket)
    {
        if(!subscriptions.contains(destination))
        {
            return ;
        }
        subscriptions.get(destination).remove(socket);
    }

    /**
     * 发布消息
     * @param destination 主题
     * @throws IOException
     */

    public static void publish(String destination,String bodyJson) throws IOException {
        MESSAGEMessage message = new MESSAGEMessage();
        message.setDestination(destination);
        message.setBodyJson(bodyJson);
        publish(message);
    }

    public static void publish(MESSAGEMessage message) throws IOException {
        CopyOnWriteArraySet<String> subscriptions1 = subscriptions.get(message.getDestination());
        if(subscriptions1 == null) return;
        Iterator<String> iterator = subscriptions1.iterator();
        while (iterator.hasNext())
        {
            Socket socket = sockets.get(iterator.next()) ;
            if(socket == null ) break;
            if(socket.getSession().isOpen())
            {
                socket.sendMessage(message);
            }
            else {
                remove(socket);
            }

        }
    }
    public static void callSubscriptionHandlers(String destination, String bodyJson, Map<String,String> headers) throws IOException {
        callSubscriptionHandlers(destination,bodyJson,null,headers);
    }
    public static void callSubscriptionHandlers(String destination,String bodyJson) throws IOException {
        callSubscriptionHandlers(destination,bodyJson,null);
    }
    public static void callSubscriptionHandlers(String destination,String bodyJson,String fromUser,Map<String,String> headers) throws IOException {
        CopyOnWriteArraySet<SubscriptionHandlerHolder> subscriptionHandlers1 = subscriptionHandlers.get(destination);
        if(subscriptionHandlers1 == null) return;
        Iterator<SubscriptionHandlerHolder> iterator = subscriptionHandlers1.iterator();
        while (iterator.hasNext())
        {
            SubscriptionHandlerHolder holder = iterator.next();
            Object body = bodyJson;
            if(holder.getBodyClazz() != null)
            {
                body = JSON.parseObject(bodyJson,holder.getBodyClazz());
            }
            else
            {
                body = JSON.parse(bodyJson);
            }
            holder.getSubscriptionHandler().subscriptionHandle(destination,body,fromUser,headers);
        }
    }

    /**
     * 实现服务器主动推送
     */
    public static void sendMessage(Socket socket,String message) throws IOException {
        socket.sendMessage(message);
    }

    public static boolean sendMessage(String user,Message message) throws IOException {
        return sendMessage(user,message.toStomp());
    }
    public static boolean sendMessage(String user,String message) throws IOException {
        if(!userSockets.containsKey(user)) return  false;
        Iterator<Socket> iterator = userSockets.get(user).values().iterator();
        boolean sent = false;
        while(iterator.hasNext())
        {
            sendMessage(iterator.next(),message);
            sent= true;
        }
        return sent;
    }

    /*通知公告*/
    public static void sendAll(String message) throws IOException {
        Iterator<Socket> iter =sockets.values().iterator();
        while (iter.hasNext()) {
            iter.next().sendMessage(message);
        }
    }

    public static  int getOnlineCount() {
        return onlineCount.get();
    }

    public static  void addOnlineCount() {
        SocketManager.onlineCount.incrementAndGet();
    }

    public static  void subOnlineCount() {

        SocketManager.onlineCount.decrementAndGet();
    }
    public static void setLoginUser(LoginUser loginUser)
    {
        loginUserThreadLocal.set(loginUser);
    }
    public static void clearLoginUser()
    {
        loginUserThreadLocal.remove();
    }
    public static LoginUser getLoginUser()
    {
        return loginUserThreadLocal.get();
    }

}


