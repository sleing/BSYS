package com.mt.common.core.security;

import com.mt.common.core.web.SecurityExpression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mt.common.system.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUser implements UserDetails {
    private Long Eid;

    private String username;

    private String password;

    private String nickname;

    private Long organizationId;

    //登录的设备类型
    private String device;

    private Long state;

    private Long deleted;

    private String organizationName;

    private List<Long> roleIds;

    private List<String> roles;

    private Set<Authority> authorities;

    private boolean cached ;

    public  static  LoginUser create(User user)
    {
        LoginUser loginUser = new LoginUser();

        loginUser.Eid = user.getEid();
        loginUser.username = user.getUsername();
        loginUser.password = user.getPassword();
        loginUser.nickname = user.getNickname();
        loginUser.organizationId = user.getOrganizationId();
        loginUser.state = user.getState();
        loginUser.deleted = user.getDeleted();
        loginUser.organizationName = user.getOrganizationName();

        List<String> roleCodes = new ArrayList();
        user.getRoles().forEach(role -> {
            roleCodes.add(role.getRoleCode());
        });
        loginUser.roles = roleCodes;

        Set<Authority> authorities = new HashSet<>();
        user.getAuthorities().forEach(authority->{
            authorities.add(new Authority(authority.getAuthority()));
        });
        loginUser.authorities = authorities;

        return loginUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return state == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Long getEid() {
        return Eid;
    }

    public void setEid(Long eid) {
        Eid = eid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }


    @Override
    public String toString() {
        return "LoginUser{" +
                "Eid=" + Eid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", organizationId=" + organizationId +
                ", device='" + device + '\'' +
                ", state=" + state +
                ", deleted=" + deleted +
                ", organizationName='" + organizationName + '\'' +
                ", roleIds=" + roleIds +
                ", roles=" + roles +
                ", authorities=" + authorities +
                ", cached=" + cached +
                '}';
    }
}
