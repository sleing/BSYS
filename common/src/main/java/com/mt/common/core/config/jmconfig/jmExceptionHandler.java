package com.mt.common.core.config.jmconfig;

import com.alibaba.fastjson.JSON;
import com.mt.common.core.web.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class jmExceptionHandler {

    @ExceptionHandler(jmException.class)
    @ResponseBody//为了返回数据
    public void handle(HttpServletRequest request, HttpServletResponse response, jmException exception) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JsonResult result;
        result = JsonResult.error(403L, "没有访问权限");
        out.write(JSON.toJSONString(result));
        out.flush();
    }
}
