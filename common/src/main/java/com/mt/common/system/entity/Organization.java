package com.mt.common.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by AutoGenerator on 2020-03-14 11:29:04
 */


@DEntity(label = "组织机构", comment = "组织机构", moduleLabel = "")
@Entity(name = "sys_organization")
@Table(name = "sys_organization", indexes = {})
@TableName("sys_organization")
@ApiModel(description = "组织机构:")
public class Organization extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

//    @ApiModelProperty(value = "机构id")
//    @TableId(value = "eid", type = IdType.AUTO)
//    private Long Eid;

    @DColumn(index = 2, label = "上级id,0是顶级", comment = "上级id,0是顶级")
    @ApiModelProperty(value = "上级id")
    @Column(name = "parent_id", insertable = false,columnDefinition = "int default 0",length = 255, nullable = false, unique = false)
    private Long parentId;

    @DColumn(index = 3, label = "机构名称", comment = "机构名称")
    @ApiModelProperty(value = "机构名称")
    @Column(name = "organization_name", length = 255, nullable = false, unique = false)
    private String organizationName;

    @DColumn(index = 4, label = "机构全称", comment = "机构全称")
    @ApiModelProperty(value = "机构全称")
    @Column(name = "organization_full_name", length = 255, nullable = true, unique = false)
    private String organizationFullName;

    @DColumn(index = 5, label = "机构代码", comment = "机构代码")
    @ApiModelProperty(value = "机构代码")
    @Column(name = "organization_code", length = 255, nullable = true, unique = false)
    private String organizationCode;

    @DColumn(index = 6, label = "机构类型", comment = "机构类型")
    @ApiModelProperty(value = "机构类型")
    @Column(name = "organization_type", length = 255, nullable = false, unique = false)
    private Long organizationType;

    @DColumn(index = 7, label = "负责人id", comment = "负责人id")
    @ApiModelProperty(value = "负责人id")
    @Column(name = "leader_id", length = 255, nullable = true, unique = false)
    private Long leaderId;

    @DColumn(index = 8, label = "排序号", comment = "排序号")
    @ApiModelProperty(value = "排序号")
    @Column(name = "sort_number",insertable = false,columnDefinition = "int default 1", length = 255, nullable = false, unique = false)
    private Long sortNumber;

    @DColumn(index = 9, label = "备注", comment = "备注")
    @ApiModelProperty(value = "备注")
    @Column(name = "comments", length = 255, nullable = true, unique = false)
    private String comments;

    @DColumn(index =10, label = "是否删除", comment = "是否删除,0否,1是")
    @ApiModelProperty(value = "是否删除")
    @Column(name = "deleted",insertable = false,columnDefinition = "int default 0", length = 255, nullable = false, unique = false)
    @TableLogic
    private Long deleted;


    @ApiModelProperty(value = "负责人姓名")
    @Transient
    @TableField(exist = false)
    private String leaderName;

    @ApiModelProperty(value = "负责人账号")
    @Transient
    @TableField(exist = false)
    private String leaderAccount;

    @ApiModelProperty(value = "上级名称")
    @Transient
    @TableField(exist = false)
    private String parentName;

    @ApiModelProperty(value = "机构类型名称")
    @Transient
    @TableField(exist = false)
    private String organizationTypeName;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationFullName() {
        return organizationFullName;
    }

    public void setOrganizationFullName(String organizationFullName) {
        this.organizationFullName = organizationFullName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Long getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(Long organizationType) {
        this.organizationType = organizationType;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public Long getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Long sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getLeaderAccount() {
        return leaderAccount;
    }

    public void setLeaderAccount(String leaderAccount) {
        this.leaderAccount = leaderAccount;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getOrganizationTypeName() {
        return organizationTypeName;
    }

    public void setOrganizationTypeName(String organizationTypeName) {
        this.organizationTypeName = organizationTypeName;
    }

    @Override
    public String toString() {
        return "Organization{" +
                ", Eid=" + Eid +
                ", parentId=" + parentId +
                ", organizationName=" + organizationName +
                ", organizationFullName=" + organizationFullName +
                ", organizationCode=" + organizationCode +
                ", organizationType=" + organizationType +
                ", leaderId=" + leaderId +
                ", sortNumber=" + sortNumber +
                ", comments=" + comments +
                ", createDatetime=" + createDatetime +
                ", deleted=" + deleted +
                ", leaderName=" + leaderName +
                ", parentName=" + parentName +
                ", organizationTypeName=" + organizationTypeName +
                "}";
    }

}
