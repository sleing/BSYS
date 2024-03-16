package com.mt.ams.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

/**
 * 发邮件工具类
 */

@Component
public class MailUtils {
    //private static final String USER = "1416863273@qq.com"; // 发件人称号，同邮箱地址
    private static final String USER = "fylsoftware@163.com"; // 发件人称号，同邮箱地址
    //private static final String PASSWORD = "bhoxupnlqfdfgdja"; // 如果是qq邮箱可以使户端授权码，或者登录密码
    private static final String PASSWORD = "ZEOAJWUIANBQJSWG"; // 如果是qq邮箱可以使户端授权码，或者登录密码


//    授权码
//    bhoxupnlqfdfgdja

    /**
     * @param to    收件人邮箱
     * @param text  邮件正文
     * @param title 标题
     */
    /* 发送验证信息的邮件 */
    public static boolean sendMail(String to, String text, String title) {
        try {
//            final Properties props = new Properties();
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.host", "smtp.qq.com");
            // 获取系统属性
            //            final Properties props = new Properties();
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.host", "smtp.qq.com");
            // 获取系统属性
            //            final Properties props = new Properties();
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.host", "smtp.qq.com");
            // 获取系统属性
            Properties props = System.getProperties();
            // 设置邮件服务器 ->QQ 邮件服务器
            props.setProperty("mail.smtp.host", "smtp.163.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            // 发件人的账号
            props.put("mail.user", USER);
            //发件人的密码
            props.put("mail.password", PASSWORD);

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    System.out.println("这里1"+props.getProperty("mail.smtp.port"));
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            // 设置收件人
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(text, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            System.out.println("这里2"+props.getProperty("mail.smtp.port"));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

//    public static void main(String[] args) throws Exception { // 做测试用
//        //生成随机6位验证码,并且存入数组
//        String code = randomCode();
//
//        String email = "2807736616@qq.com";
//        MailUtils.sendMail(email, "验证码是：" + code, "一个价值千万项目的验证码");
//        System.out.println("验证码是：" + code);
//
////        将验证码暂时存放 能走缓存尽量走缓存
//        File file = new File("D:\\Workspace\\DDD5\\ems\\src\\main\\java\\com\\mt\\ams\\utils", "checkCode.txt");
//        byte bt[] = new byte[1024];
//        //邮箱转换为字节流
//        byte btEmail[] = new byte[1024];
//        btEmail = email.getBytes();
//
////        邮箱与验证码形成唯一标识
//        StringBuffer uniqueFlag = new StringBuffer();
//        uniqueFlag.append(code);
//        uniqueFlag.append(",");
//        uniqueFlag.append(email);
//        byte[] btUnique = new byte[1024];
//        btUnique = (uniqueFlag.toString()).getBytes();
//        System.out.println(uniqueFlag);
//
//        bt = code.getBytes();
//        //写入文件
//        try {
//            FileOutputStream in = new FileOutputStream(file);
//            try {
////                in.write(bt, 0, bt.length);
//                in.write(btUnique, 0, btEmail.length);
//                in.close();
//                System.out.println("写入文件成功");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println("发送成功");
//        } finally {
//            System.out.println("结束");
//        }
//
//        //读取文件
//        try {
//            // 读取文件内容 (输入流)
//            FileInputStream out = new FileInputStream(file);
//            InputStreamReader isr = new InputStreamReader(out);
//            int ch = 0;
//            StringBuffer stringBuffer = new StringBuffer();
//            while ((ch = isr.read()) != -1) {
//                System.out.print((char) ch);
//                stringBuffer.append((char) ch);
//            }
//            System.out.println();
//            System.out.println("读取的验证码：" + stringBuffer.substring(0, 6));
//            System.out.println("读取的邮箱@前的符号：" + stringBuffer.substring(7));
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//
//    }

    @Cacheable(value = "cacheSpace", key = "#email")
    public String getCode(String email) {
        return null;
    }
}
