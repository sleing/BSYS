package com.mt.common.core.web;

import com.mt.common.core.security.LoginUser;
import com.mt.common.system.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Controller基类
 * Created by wangfan on 2017-06-10 10:10
 */
public class BaseController {

    /**
     * 获取当前登录的user
     */
    public LoginUser LoginUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) return null;
            Object object = authentication.getPrincipal();
            if (object != null) return (LoginUser) object;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 获取当前登录的userId
     */
    public Long getLoginUserId() {
        LoginUser loginUser = LoginUser();
        return loginUser == null ? null : loginUser.getEid();
    }


    /**
     * 设置请求分页数据
     */
    protected void startPage(){

    }

}
