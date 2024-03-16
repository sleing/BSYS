package com.mt.common.core.config;


import com.mt.common.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@PropertySource(value = {"classpath:config-${spring.profiles.active}.yml","file:config/config-${spring.profiles.active}.yml"},ignoreResourceNotFound =true,encoding = "UTF-8")
@ConfigurationProperties
public class Config {



    public Config() {
    }




    public static String uploadFileAddress;
    public static String workspapce;
    public static String serverPath;
    public static String uiPath;
    public static String module;
    public static String codeTablesFile;
    public static String moduleFile;
    public static String velocityTemplate;
    public static String velocityTemplateLis;
    public static String exceptionLogPath;
    public static String staticFilePath;

    public static String staticOthFilePath;

    public static String loginExpirationTime;//登录验证超时时间

    public static String applicationName;
    public static String applicationCode;
    public static String shortCode;
    public static String basePackage;

    public static String charset_UTF8;
    public static String charset_ISO;
    public static String dataBase;
    public static String serverId;
    public static String logPath;
    public static boolean isDeveloping;
    public static Set<String> whiteURLs;
    public static String bigScreen;

    public static Long tokenExpireTime = 1000 * 60 * 300L;  // token过期时间,单位为微秒
    public static Long tokenWillExpireTime  =  1000 * 60 * 30L;  // token将要过期自动刷新,单位分钟

    public static String getLoginExpirationTime() {
        return loginExpirationTime;
    }

    public static void setLoginExpirationTime(String loginExpirationTime) {
        Config.loginExpirationTime = loginExpirationTime;
    }

    public void setUploadFileAddress(String uploadFileAddress) {
        Config.uploadFileAddress = uploadFileAddress;
    }

    public String getUploadFileAddress() {
        return uploadFileAddress;
    }

    public String getWorkspapce() {
        return workspapce;
    }

    public void setWorkspapce(String workspapce) {
        Config.workspapce = workspapce;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        Config.serverPath = serverPath;
    }

    public String getUiPath() {
        return uiPath;
    }

    public void setUiPath(String uiPath) {
        Config.uiPath = uiPath;
    }

    public  String getModule() {
        return module;
    }

    public  void setModule(String module) {
        Config.module = module;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        Config.applicationName = applicationName;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        Config.applicationCode = applicationCode;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        Config.shortCode = shortCode;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        Config.basePackage = basePackage;
    }

    public String getCharset_UTF8() {
        return charset_UTF8;
    }

    public void setCharset_UTF8(String charset_UTF8) {
        Config.charset_UTF8 = charset_UTF8;
    }

    public String getCharset_ISO() {
        return charset_ISO;
    }

    public void setCharset_ISO(String charset_ISO) {
        Config.charset_ISO = charset_ISO;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        Config.dataBase = dataBase;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        Config.serverId = serverId;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        Config.logPath = logPath;
    }

    public boolean getIsDeveloping() {
        return isDeveloping;
    }

    public void setIsDeveloping(boolean isDeveloping) {
        Config.isDeveloping = isDeveloping;
    }

    public String getVelocityTemplate() {
        return velocityTemplate;
    }

    public void setVelocityTemplate(String velocityTemplate) {
        Config.velocityTemplate = velocityTemplate;
    }

    public boolean isIsDeveloping() {
        return isDeveloping;
    }

    public String getCodeTablesFile() {
        return codeTablesFile;
    }

    public void setCodeTablesFile(String codeTablesFile) {
        Config.codeTablesFile = codeTablesFile;
    }

    public String getModuleFile() {
        return moduleFile;
    }

    public void setModuleFile(String moduleFile) {
        Config.moduleFile = moduleFile;
    }

    public String getWhiteURLs() {
        return whiteURLs == null ? "" : whiteURLs.toString();
    }

    public void setWhiteURLs(String whiteURLs) {
        whiteURLs = StringUtils.trimToEmpty(whiteURLs);
        whiteURLs = StringUtils.replace(whiteURLs," ","");
        String[] temp = StringUtils.split(whiteURLs, ",");

        Config.whiteURLs = new HashSet<String>(Arrays.asList(temp));

    }

    public String getExceptionLogPath() {
        return exceptionLogPath;
    }

    public void setExceptionLogPath(String exceptionLogPath) {
        Config.exceptionLogPath = exceptionLogPath;
        BusinessException.setLogPath(Config.exceptionLogPath);
    }

    public String getStaticFilePath() {
        return staticFilePath;
    }

    public void setStaticFilePath(String staticFilePath) {
        Config.staticFilePath = staticFilePath;
    }

    public static String getStaticOthFilePath() {
        return staticOthFilePath;
    }

    public static void setStaticOthFilePath(String staticOthFilePath) {
        Config.staticOthFilePath = staticOthFilePath;
    }

    public  Long getTokenExpireTime() {
        return Config.tokenExpireTime;
    }
    public  void setTokenExpireTime(long tokenExpireTime) {
        Config.tokenExpireTime=1000 * 60 *tokenExpireTime;
    }
    public  Long getTokenWillExpireTime() {
        return Config.tokenWillExpireTime;
    }
    public  void setTokenWillExpireTime(Long tokenWillExpireTime) {
        Config.tokenWillExpireTime =1000 * 60 *tokenWillExpireTime;
    }

    public  String getBigScreen() {
        return bigScreen;
    }

    public  void setBigScreen(String bigScreen) {
        Config.bigScreen = bigScreen;
    }
    public static String getVelocityTemplateLis() {
        return velocityTemplateLis;
    }

    public static void setVelocityTemplateLis(String velocityTemplateLis) {
        Config.velocityTemplateLis = velocityTemplateLis;
    }
}
