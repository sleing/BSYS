package com.mt.common.core.security;

import com.mt.common.system.entity.Menu;
import com.mt.common.system.mapper.MenuMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: ${fxb}
 * Email: fanxb.tl@gmail.com
 * Date: 2018-07-19
 */
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuMapper menuMapper;
    private List<Menu> menus;

    public void loadResource() {
        this.menus = this.menuMapper.listAppUrlMenus();
        if(this.menus == null ) this.menus = new ArrayList<>();
        this.menus = this.menus.stream().map((menu)->{
            String path = menu.getPath().trim();
            path = StringUtils.removeStart(path,"#{appUrl}");
            path = StringUtils.substringBefore(path,"?");
            path = StringUtils.replacePattern(path,"#\\{.*\\}","**");

            menu.setPath(path);
            return menu;
        }).collect(Collectors.toList());
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (this.menus == null) this.loadResource();
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        if(StringUtils.startsWith(request.getServletPath(),"/api/")) return null;
        Set<ConfigAttribute> allConfigAttribute = new HashSet<>();
        AntPathRequestMatcher matcher;
        for (Menu menu : this.menus) {
            matcher = new AntPathRequestMatcher(menu.getPath());
            if (matcher.matches(request)) {
                ConfigAttribute configAttribute = new MyConfigAttribute(new Authority(menu.getAuthority()));
                allConfigAttribute.add(configAttribute);
                return allConfigAttribute;
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
