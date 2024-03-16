package com.mt.common.core.config.jmconfig;

import cn.hutool.jwt.JWTException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mt.common.core.exception.IException;
import com.mt.common.core.security.JwtUtil;
import com.mt.common.core.security.LoginUser;
import com.mt.common.core.web.JsonResult;
import com.mt.common.system.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhs
 * @version 1.0
 * @date 2022/11/19 16:28
 */
@Component
public class JimuInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        String token = JwtUtil.getTokenByRequest(request);
        List<String> roles = JwtUtil.getLoginUser().getRoles();
        String userType=null;
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            String r = it.next().toString();
            userType=r;
        }
        JsonResult result;
        if (StringUtils.isNotEmpty(token)&&(userType.equals("student"))) {
            response.setContentType("text/html;charset=utf-8");
            request.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.flush();
            throw new jmException(403,"没有访问权限");
        }
        else{
            return true;
        }
    }
}
