package com.mt.common.core.security;

import com.alibaba.fastjson.JSON;
import com.mt.common.core.socket.SocketManager;
import com.mt.common.core.web.JsonResult;
import com.mt.common.system.service.LoginRecordService;
import com.mt.common.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 请求过滤器,处理携带token的请求
 * Created by wangfan on 2020-03-23 22:46
 */
public class JwtRequestFilter extends OncePerRequestFilter {


    private final UserDetailsService userDetailsService;
    @Autowired
    private LoginRecordService loginRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    public JwtRequestFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = JwtUtil.getTokenByRequest(request);

        if (StringUtils.isNotEmpty(token)) {
            try {
                token = JwtUtil.verifyToken(token, userDetailsService, redisUtil);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter out = response.getWriter();

                out.write(JSON.toJSONString(JsonResult.error(401L, "令牌检查出错，原始是：" + e.getMessage())));
                out.flush();
                return;
            }
            response.addHeader(JwtUtil.TOKEN_HEADER_NAME, token);
//            loginRecordService.saveAsync(username, LoginRecord.TYPE_REFRESH, null, request);

            String username = JwtUtil.getUsername(token);
            LoginUser loginUser = (LoginUser) userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    loginUser, null, loginUser.getAuthorities());

            if(!loginUser.isCached()) {
                this.userService.cache(loginUser);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if(request.getRequestURI().startsWith("/WebSocket/")) {
                SocketManager.setLoginUser(loginUser);
            }
        }

        chain.doFilter(request, response);
    }
}
