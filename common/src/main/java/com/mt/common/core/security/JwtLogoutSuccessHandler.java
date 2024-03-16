package com.mt.common.core.security;

import com.mt.common.system.entity.LoginRecord;
import com.mt.common.system.service.LoginRecordService;
import com.mt.common.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录处理
 * Created by wangfan on 2020-03-23 19:15
 */
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private LoginRecordService loginRecordService;

    @Autowired
    private UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // 记录退出登录日志
        String token = JwtUtil.getTokenByRequest(request);
        if(StringUtils.isNotEmpty(token))
        {
            String username = JwtUtil.getUsername(token);
            if(StringUtils.isNotEmpty(username)) {

                loginRecordService.saveAsync(username, LoginRecord.TYPE_LOGOUT, null, request);

                this.userService.decache(username);
            }
        }
    }

}
