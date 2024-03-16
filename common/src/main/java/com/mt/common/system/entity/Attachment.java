package com.mt.common.system.entity;

import javax.persistence.*;
import java.io.Serializable;

import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Date;

import com.mt.common.system.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@DEntity(label = "附件", comment = "", moduleLabel = "附件")
@Entity(name = "sys_attachment")
@Table(name = "sys_attachment", indexes = {@Index(name = "index_upload_user_id", columnList = "upload_user_id"),
        @Index(name = "index_attachment_id", columnList = "attachment_id")})
@ApiModel(description = "附件:")
public class Attachment extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @DColumn(index = 3, label = "附件的真实名称", comment = "附件的真实名称")
    @ApiModelProperty(value = "附件的真实名称:附件的真实名称")
    @Column(name = "attachment_real_name", length = 255, nullable = false, unique = false)
    private String attachmentRealName;

    @DColumn(index = 4, label = "保存文件名", comment = "保存文件名")
    @ApiModelProperty(value = "保存文件名:保存文件名")
    @Column(name = "attachment_logical_name", length = 255, nullable = false, unique = false)
    private String attachmentLogicalName;

    @DColumn(index = 4, label = "附件文件唯一标识", comment = "附件文件唯一标识")
    @ApiModelProperty(value = "附件文件唯一标识:附件文件唯一标识")
    @Column(name = "attachment_id", length = 255, nullable = false, unique = false)
    private String attachmentId;

    @DColumn(index = 5, label = "上传时间", comment = "上传时间")
    @ApiModelProperty(value = "上传时间:上传时间")
    @Column(name = "upload_time", length = 255, nullable = true, unique = false)
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uploadTime;

    @DColumn(index = 6, label = "上传人", foreignEntity = "User", comment = "上传人")
    @ApiModelProperty(value = "上传人:上传人")
    @Column(name = "upload_user_id", length = 255, nullable = true, unique = false)
    private Long uploadUserId;

    @Transient
    private User uploadUser;

    @Transient
    @ApiModelProperty(value = "上传人名称:上传人")
    @DColumn(index = 6, label = "上传人", foreignEntity = "User", comment = "上传人")
    private String uploadUserName;

    @DColumn(index = 7, label = "附件地址", comment = "附件地址")
    @ApiModelProperty(value = "附件地址:附件地址")
    @Column(name = "attachment_addr", length = 255, nullable = true, unique = false)
    private String attachmentAddr;

    @DColumn(index = 8, label = "关联表单Id", comment = "关联表单Id")
    @ApiModelProperty(value = "关联表单Id:关联表单Id")
    @Column(name = "associate_form_id", length = 255, nullable = false, unique = false)
    private String associateFormId;

    @DColumn(index = 9, label = "关联表单名称", comment = "关联表单名称")
    @ApiModelProperty(value = "关联表单名称:关联表单名称")
    @Column(name = "associate_form_name", length = 255, nullable = false, unique = false)
    private String associateFormName;

    @DColumn(index = 10, label = "附件大小", comment = "附件大小")
    @ApiModelProperty(value = "附件大小:附件大小")
    @Column(name = "associate_size", length = 255, nullable = false, unique = false)
    private Long associateSize;

    @DColumn(index = 11, label = "是否有效", comment = "是否有效")
    @ApiModelProperty(value = "是否有效:是否有效")
    @Column(name = "is_effective", length = 255, nullable = true, unique = false)
    private Boolean isEffective;

    @DColumn(index = 12, label = "分组名", comment = "分组名")
    @ApiModelProperty(value = "分组名:分组名")
    @Column(name = "group_name", length = 255, nullable = true, unique = false)
    private String groupName;

    @DColumn(index = 13, label = "组织id", comment = "组织id")
    @ApiModelProperty(value = "组织id:组织id")
    @Column(name = "organization_id", length = 255, nullable = true, unique = false)
    private Long organizationId;

    @DColumn(index = 14, label = "组织name", comment = "组织name")
    @ApiModelProperty(value = "组织name:组织name")
    @Column(name = "organization_name", length = 255, nullable = true, unique = false)
    private String organizationName;


    public String getAttachmentRealName() {
        return this.attachmentRealName;
    }

    public void setAttachmentRealName(String attachmentRealName) {
        this.attachmentRealName = attachmentRealName;
    }

    public String getAttachmentLogicalName() {
        return this.attachmentLogicalName;
    }

    public void setAttachmentLogicalName(String attachmentLogicalName) {
        this.attachmentLogicalName = attachmentLogicalName;
    }

    public Date getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Long getUploadUserId() {
        return this.uploadUserId;
    }

    public void setUploadUserId(Long uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public String getAttachmentAddr() {
        return this.attachmentAddr;
    }

    public void setAttachmentAddr(String attachmentAddr) {
        this.attachmentAddr = attachmentAddr;
    }

    public String getAssociateFormId() {
        return this.associateFormId;
    }

    public void setAssociateFormId(String associateFormId) {
        this.associateFormId = associateFormId;
    }

    public String getAssociateFormName() {
        return this.associateFormName;
    }

    public void setAssociateFormName(String associateFormName) {
        this.associateFormName = associateFormName;
    }

    public Long getAssociateSize() {
        return this.associateSize;
    }

    public void setAssociateSize(Long associateSize) {
        this.associateSize = associateSize;
    }

    public Boolean getIsEffective() {
        return this.isEffective;
    }

    public void setIsEffective(Boolean isEffective) {
        this.isEffective = isEffective;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public User getUploadUser() {
        return this.uploadUser;
    }

    public void setUploadUser(User uploadUser) {
        if (uploadUser == null) {
            return;
        }
        //this.uploadUserId = uploadUser.getEid();
        this.uploadUser = uploadUser;
    }

    public String getUploadUserName() {
        return this.uploadUserName;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }
}
