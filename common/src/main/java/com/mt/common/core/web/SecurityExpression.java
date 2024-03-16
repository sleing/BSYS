package com.mt.common.core.web;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public  class SecurityExpression extends SecurityExpressionRoot  {
    public SecurityExpression(Authentication authentication) {
        super(authentication);
    }
}
