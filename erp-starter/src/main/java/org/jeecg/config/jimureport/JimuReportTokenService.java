package org.jeecg.config.jimureport;

import com.mt.common.core.security.JwtUtil;
import com.mt.common.core.security.LoginUser;
import com.mt.common.core.security.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.jmreport.api.JmReportTokenServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
* 自定义积木报表鉴权(如果不进行自定义，则所有请求不做权限控制)
 * 1.自定义获取登录token
 * 2.自定义获取登录用户
*/
@Component
class JimuReportTokenService implements JmReportTokenServiceI {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    @Lazy
    private RedisUtil redisUtil;

    @Override
    public String getToken(HttpServletRequest request) {
        String access_token = JwtUtil.getTokenByRequest(request);
        return access_token;
    }

    @Override
    public String getUsername(String token) {
        String username = JwtUtil.getUsername(token);
        return username;
    }

    @Override
    public Boolean verifyToken(String token) {
        token = JwtUtil.verifyToken(token, userDetailsService, redisUtil);
        return StringUtils.isNotEmpty(token);
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        String username = JwtUtil.getUsername(token);
        //此处通过token只能拿到一个信息 用户账号  后面的就是根据账号获取其他信息 查询数据或是走redis 用户根据自身业务可自定义
        LoginUser loginUser = JwtUtil.getLoginUser();
        Map<String, Object> map = new HashMap<String, Object>();
        //设置账号名
        map.put("sysUserCode", loginUser.getUsername());
        map.put("sysUserId", loginUser.getEid());
        //设置部门编码
        map.put("sysOrgId", loginUser.getOrganizationId());

        // 将所有信息存放至map 解析sql会根据map的键值解析,可自定义其他值
        return map;
    }
}
