package com.mt.common.core.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class Qr {
    private static Map<String,Class<Qr>> QrTypes = new HashMap<>();

    /**
     * 每个子类都需要注册,例如下面的代码：
     *    static {
     *         Qr.registerType("base",Qr.class);
     *     }
     * @param type
     * @param clazz
     */
    static {
        Qr.registerType("base",Qr.class);
    }
    public static void registerType(String type,Class<Qr> clazz)
    {
        QrTypes.put(type,clazz);
    }
    public static Class<Qr> getQrClass(String type)
    {
        return QrTypes.get(type);
    }

    public  static String type;//种类标识
    public  static String label;//中文名称
    public  static Long expireTime = Long.MAX_VALUE; //二位码的有效期,单位毫秒

    //主题
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
