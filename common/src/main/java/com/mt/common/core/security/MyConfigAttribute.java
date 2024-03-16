package com.mt.common.core.security;

import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:自定义ConfigAttribute实现
 * User: ${fxb}
 * Email: fanxb.tl@gmail.com
 * Date: 2018-07-19
 */
public class MyConfigAttribute implements ConfigAttribute {
    private Authority myGrantedAuthority;

    public MyConfigAttribute( Authority myGrantedAuthority) {
        this.myGrantedAuthority = myGrantedAuthority;
    }


    @Override
    public String getAttribute() {
        return this.myGrantedAuthority.getAuthority();
    }

    public Authority getMyGrantedAuthority() {
        return myGrantedAuthority;
    }
}
