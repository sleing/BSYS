package com.mt.common.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MessageUtil
 * @Description TODO
 * @Author ycs
 * @Date 2021/9/16 16:36
 * @Version 1.0
 */
public class MessageUtil {
    private static MessageSource messageSource;

    private static Map<String, String> Messages = new HashMap<String, String>();

    static {
        Messages.put("CM.DB.CREATE_SUCCESS", "{0}新增成功");
        Messages.put("CM.DB.CREATE_FAILED", "{0}新增失败");
        Messages.put("CM.DB.UPDATE_SUCCESS", "{0}更新成功");
        Messages.put("CM.DB.UPDATE_FAILED", "{0}更新失败");
        Messages.put("CM.DB.DELETE_SUCCESS", "{0}删除成功");
        Messages.put("CM.DB.DELETE_FAILED", "{0}删除失败");
        Messages.put("CM.DB.NO_RESULT", "没有查询到{0}数据");
        Messages.put("CM.DB.EDIT_CONFLICT", "记录已被他人更新，请重新编辑");
        Messages.put("CM.CAPTCHA_ERROR", "验证码不正确或已过期");
        Messages.put("SYSTEM_ERROR", "系统错误");
        Messages.put("CM.PERMISSION_ERROR", "没有权限");
    }
    /**
     * 注入
     */
//    private static void init() {
//        if (messageSource == null) {
//            synchronized (MessageUtil.class) {
//                ApplicationContext applicationContextFactory = new ClassPathXmlApplicationContext("classpath:message_source.xml");
//                messageSource = (MessageSource) applicationContextFactory.getBean("messageSource");
//            }
//        }
//    }

    /**
     * 获取消息
     *
     * @param errorCode messageId
     * @param param     参数
     * @return
     */
    public static String getMessage(String errorCode, String... param) {
        try {
            String message = Messages.get(errorCode);
            for (int i = 0; i < param.length; i++) {
                message = StringUtils.replace(message, "{" + i + "}", param[i]);
            }
            return message;
        } catch (NoSuchMessageException e) {
            return errorCode;
        }
    }

    /**
     * 获取消息
     *
     * @param errorCode messageId
     * @return
     */
    public static String getMessage(String errorCode) {
        try {
            String message = Messages.get(errorCode);
            return message;
        } catch (NoSuchMessageException e) {
            return errorCode;
        }
    }
}
