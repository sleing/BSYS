package com.mt.common.core.aspect;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mt.common.core.annotation.BusinessRecord;
import com.mt.common.system.entity.BusinessLog;
import com.mt.common.system.entity.User;
import com.mt.common.system.service.BusinessLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class BusinessLogAspect {
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Autowired
    private BusinessLogService businessLogService;

    @Pointcut("@annotation(com.mt.common.core.annotation.BusinessRecord)")
    public void businessLog() {
    }

    @Before("businessLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "businessLog()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        saveLog(joinPoint, result, null);
    }

    @AfterThrowing(pointcut = "businessLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        saveLog(joinPoint, null, e);
    }

    private void saveLog(JoinPoint joinPoint, Object result, Exception e) {
        // 获取reques对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (attributes == null ? null : attributes.getRequest());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        BusinessRecord businessRecord = method.getAnnotation(BusinessRecord.class);
        BusinessLog businessLog = new BusinessLog();
        // 记录返回结果
        if (request != null) {
            businessLog.setData(StrUtil.sub(JSON.toJSONString(result), 0, 2000));
        }
        // 记录异常信息
        if (e != null) {
            businessLog.setStatus("异常");
            businessLog.setData(StrUtil.sub(e.toString(), 0, 2000));
        } else {
            businessLog.setStatus("正常");
        }
        businessLog.setEntity(businessRecord.entity());
        businessLog.setEntityLabel(businessRecord.entityLabel());
        businessLog.setCreatorId(getLoginUserId());
        businessLog.setCreateDatetime(new Date());
        businessLog.setBusiness(businessRecord.business());
        businessLog.setBusinessContent(businessRecord.businessContent());
        businessLogService.saveAsync(businessLog);
    }

    /**
     * 获取当前登录的userId
     */
    public Long getLoginUserId() {
        Authentication subject = SecurityContextHolder.getContext().getAuthentication();
        if (subject == null) return null;
        Object object = subject.getPrincipal();
        if (object instanceof User) return ((User) object).getEid();
        return null;
    }
}
