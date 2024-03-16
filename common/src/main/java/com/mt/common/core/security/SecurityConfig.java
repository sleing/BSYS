package com.mt.common.core.security;

import com.mt.common.core.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置
 * Created by wangfan on 2020-03-23 18:04
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyFilterSecurityInterceptor urlFilterSecurityInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                expressionInterceptUrlRegistry = http.authorizeRequests();

        // 允许访问的白名单
        expressionInterceptUrlRegistry.antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        for (String pattern : Config.whiteURLs) {
            expressionInterceptUrlRegistry.antMatchers(pattern).permitAll();
        }

        // 除上面之外的所有请求全部需要鉴权认证
        expressionInterceptUrlRegistry.anyRequest().authenticated()

                // 基于token，所以不需要session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 禁止iframe调用
                .and().headers().frameOptions().disable()
                // CSRF跨域检查禁用，因为不使用session
                .and().cors().and().csrf().disable();
        // 添加JWT filter
        http.addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加url安全拦截器?
        http.addFilterBefore(urlFilterSecurityInterceptor,FilterSecurityInterceptor.class);
        // 定义退出处理
        http.logout().logoutUrl("/api/logout").logoutSuccessHandler(jwtLogoutSuccessHandler());
        // 定义异常处理
        http.exceptionHandling().accessDeniedHandler(jwtExceptionHandler()).authenticationEntryPoint(jwtExceptionHandler());

    }

    /**
     * 身份认证接口
     * userDetailsService 自定义注入UserDetailsService来管理系统的实体数据，重写loadUserByUsername来进行用户验证
     * BCryptPasswordEncoder 自定义加密解密处理类
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl()).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsServiceImpl() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public JwtLoginFilter jwtLoginFilter() throws Exception {
        return new JwtLoginFilter(authenticationManagerBean());
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(userDetailsServiceImpl());
    }

    @Bean
    public MyFilterSecurityInterceptor urlFilterSecurityInterceptor() {
        return new MyFilterSecurityInterceptor();
    }

    @Bean
    public JwtLogoutSuccessHandler jwtLogoutSuccessHandler() {
        return new JwtLogoutSuccessHandler();
    }

    @Bean
    public JwtExceptionHandler jwtExceptionHandler() {
        return new JwtExceptionHandler();
    }

}
