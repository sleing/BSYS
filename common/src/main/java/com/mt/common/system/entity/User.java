package com.mt.common.system.entity;

import javax.persistence.*;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.SecurityExpression;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by AutoGenerator on 2018-12-24 16:10
 */

@DEntity(label = "用户", comment = "用户", moduleLabel = "")
@Entity(name = "sys_user")
@Table(name = "sys_user")
@TableName("sys_user")
@ApiModel(description = "用户:")
public class User extends BaseEntity implements UserDetails {

//    @ApiModelProperty("用户id")
//    @TableId(value = "eid", type = IdType.AUTO)
//    private Long Eid;

//    @ApiModelProperty("机构名称")
//    @Transient
//    @TableField(exist = false)
//    private Long Eid;

    @DColumn(index = 2, label = "账号", comment = "账号")
    @ApiModelProperty(value = "账号")
    @Column(name = "username", length = 255, nullable = false, unique = false)
    private String username;

    @DColumn(index = 3, label = "密码", comment = "密码")
    @ApiModelProperty(value = "密码")
    @Column(name = "password", length = 255, nullable = false, unique = false)
    private String password;

    @DColumn(index = 4, label = "昵称", comment = "昵称")
    @ApiModelProperty(value = "昵称")
    @Column(name = "nickname", length = 255, nullable = false, unique = false)
    private String nickname;

    @DColumn(index = 5, label = "头像", comment = "头像")
    @ApiModelProperty(value = "头像")
    @Column(name = "avatar", length = 255, nullable = true, unique = false)
    private String avatar;

    @DColumn(index = 6, label = "性别", comment = "性别")
    @ApiModelProperty(value = "性别")
    @Column(name = "sex", length = 255, nullable = true, unique = false)
    private Long sex;

    @DColumn(index =7, label = "手机号", comment = "手机号")
    @ApiModelProperty(value = "手机号")
    @Column(name = "phone", length = 255, nullable = true, unique = false)
    private String phone;

    @DColumn(index =8, label = "邮箱", comment = "邮箱")
    @ApiModelProperty(value = "邮箱")
    @Column(name = "email", length = 255, nullable = true, unique = false)
    private String email;

    @DColumn(index =8, label = "邮箱", comment = "邮箱是否验证,0否,1是")
    @ApiModelProperty(value = "邮箱")
    @Column(name = "email_verified",insertable = false,columnDefinition = "int default 0", length = 255, nullable = false, unique = false)
    private Long emailVerified;

    @DColumn(index =9, label = "真实姓名", comment = "真实姓名")
    @ApiModelProperty(value = "真实姓名")
    @Column(name = "true_name", length = 255, nullable = true, unique = false)
    private String trueName;

    @DColumn(index =10, label = "身份证号", comment = "身份证号")
    @ApiModelProperty(value = "身份证号")
    @Column(name = "id_card", length = 255, nullable = true, unique = false)
    private String idCard;

    @DColumn(index =11, label = "出生日期", comment = "出生日期")
    @ApiModelProperty(value = "出生日期")
    @Column(name = "birthday", length = 255, nullable = true, unique = false)
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @DColumn(index =12, label = "个人简介", comment = "个人简介")
    @ApiModelProperty(value = "个人简介")
    @Column(name = "introduction", length = 255, nullable = true, unique = false)
    private String introduction;

    @DColumn(index =13, label = "机构id", comment = "机构id")
    @ApiModelProperty(value = "机构id")
    @Column(name = "organization_id", length = 255, nullable = true, unique = false)
    private Long organizationId;


    @DColumn(index =14, label = "状态", comment = "状态，0正常，1冻结")
    @ApiModelProperty(value = "状态")
    @Column(name = "state",insertable = false,columnDefinition = "int default 0", length = 255, nullable = false, unique = false)
    private Long state;

    @DColumn(index =15, label = "是否删除", comment = "是否删除,0否,1是")
    @ApiModelProperty(value = "是否删除")
    @Column(name = "deleted",insertable = false,columnDefinition = "int default 0", length = 255, nullable = false, unique = false)
    @TableLogic
    private Long deleted;

    @DColumn(index =16, label = "用户类型", comment = "用户类型")
    @ApiModelProperty(value = "用户类型")
    @Column(name = "user_type", length = 255, nullable = true, unique = false)
    private String userType;

    @ApiModelProperty("性别名称")
    @Transient
    @TableField(exist = false)
    private String sexName;

    @ApiModelProperty("机构名称")
    @Transient
    @TableField(exist = false)
    private String organizationName;

    @ApiModelProperty("角色id")
    @Transient
    @TableField(exist = false)
    private List<Long> roleIds;

    @ApiModelProperty("角色列表")
    @Transient
    @TableField(exist = false)
    private List<Role> roles;

    @ApiModelProperty("权限列表")
    @Transient
    @TableField(exist = false)
    private List<Menu> authorities;

    @Transient
    @JsonIgnore
    @TableField(exist = false)
    private SecurityExpression securityExpression;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Long emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public List<Menu> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Menu> authorities) {
        this.authorities = authorities;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public SecurityExpression getSecurityExpression() {
        if(this.securityExpression == null)
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.securityExpression = new SecurityExpression(authentication);
        }
        return securityExpression;
    }

    public void setSecurityExpression(SecurityExpression securityExpression) {
        this.securityExpression = securityExpression;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", trueName='" + trueName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", birthday=" + birthday +
                ", introduction='" + introduction + '\'' +
                ", organizationId=" + organizationId +
                ", state=" + state +
                ", deleted=" + deleted +
                ", userType='" + userType + '\'' +
                ", sexName='" + sexName + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", roleIds=" + roleIds +
                ", roles=" + roles +
                ", authorities=" + authorities +
                ", securityExpression=" + securityExpression +
                '}';
    }
}
