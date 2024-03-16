package com.mt.common.core.security;

import com.alibaba.fastjson.JSON;
import com.mt.common.core.constants.Constants;
import com.mt.common.core.web.JsonResult;
import com.mt.common.system.entity.LoginRecord;
import com.mt.common.system.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 登录过滤器
 * Created by wangfan on 2020-03-24 12:57
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private LoginRecordService loginRecordService;
    @Resource
    private RedisTemplate<String, Long> redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;


    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        super.setFilterProcessesUrl("/api/login");
    }

    /**
     * 登录成功签发token返回json数据
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        UserDetails user = (UserDetails) authResult.getPrincipal();
        String access_token = JwtUtil.sign(user.getUsername(), user.getPassword());
        // 记录登录日志
        loginRecordService.saveAsync(user.getUsername(), LoginRecord.TYPE_LOGIN, null, request);
        PrintWriter out = response.getWriter();
        // 返回json数据
        response.setContentType("application/json;charset=UTF-8");

        out.write(JSON.toJSONString(JsonResult.ok("登录成功").put("access_token", access_token)
                .put("token_type", JwtUtil.TOKEN_TYPE)));
        out.flush();
    }

    /**
     * 登录失败处理
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        String username = request.getParameter("username");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JsonResult result;
        if (e instanceof UsernameNotFoundException) {
            result = JsonResult.error("账号不存在");
            loginRecordService.saveAsync(username, LoginRecord.TYPE_ERROR, "账号不存在", request);
        } else if (e instanceof BadCredentialsException) {
//            System.out.println(username);
            //TODO:记录登录错误次数
            redisTemplate.opsForValue().increment(username + "-LoginErrorNumber", 1);
            //TODO:设置登录错误次数过期时间10分钟
            redisTemplate.expire(username + "-LoginErrorNumber", 10, TimeUnit.MINUTES);
            //TODO:默认不显示验证码
            Boolean captchaStatus = false;
            //TODO:判断是否需要返回验证码，登录失败2次后需要输入验证码 超过10分钟恢复正常
            if (redisTemplate.boundValueOps(username + "-LoginErrorNumber").get(0, -1) != null) {
                String loginErrorNumber = redisTemplate.boundValueOps(username + "-LoginErrorNumber").get(0, -1);
                if (loginErrorNumber.length() > 0 && Integer.parseInt(loginErrorNumber) > 2) {
//                    System.out.println("登录错误次数超过2次");
                    //TODO:登录错误次数超过2次需要输验证码
                    captchaStatus = true;
                }
            }
            result = JsonResult.error("账号或密码错误").put("captchaStatus", captchaStatus);
            loginRecordService.saveAsync(username, LoginRecord.TYPE_ERROR, "账号或密码错误", request);
        } else if (e instanceof LockedException) {
            result = JsonResult.error("账号被锁定");
            loginRecordService.saveAsync(username, LoginRecord.TYPE_ERROR, "账号被锁定", request);
        } else {
            result = JsonResult.error(e.getMessage());
        }
        out.write(JSON.toJSONString(result));
        out.flush();
    }

}
