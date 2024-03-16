package com.mt.common.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@DEntity(label="业务日志",comment="",moduleLabel="系统管理")
@Entity(name="erp_business_log")
@Table(name = "erp_business_log" , indexes = { @Index(name = "index_user_id", columnList = "user_id")  })
@ApiModel(description = "业务日志:")
public class BusinessLog extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="用户Id",foreignEntity="User",comment="",component="对象选择",where=false)
	@ApiModelProperty(value = "用户Id:")
	@Column(name="user_id",length=255,nullable=true,unique=false)
	private Long userId;

	@Transient
	private User user;

	@Transient
	@ApiModelProperty(value = "用户Id名称:")
	@DColumn(index=3,label="用户Id",foreignEntity="User",comment="")
	private String userName;

	@DColumn(index=4,label="操作时间",comment="",component="日期选择",where=false)
	@ApiModelProperty(value = "操作时间:")
	@Column(name="log_date",length=255,nullable=true,unique=false)
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date logDate;

	@DColumn(index=5,label="业务实体",comment="",component="文本",where=false)
	@ApiModelProperty(value = "业务实体:")
	@Column(name="entity",length=255,nullable=true,unique=false)
	private String entity;

	@DColumn(index=6,label="业务实体名",comment="",component="文本",where=false)
	@ApiModelProperty(value = "业务实体名:")
	@Column(name="entity_label",length=255,nullable=true,unique=false)
	private String entityLabel;

	@DColumn(index=7,label="业务ID",comment="",component="数字，整数",where=false)
	@ApiModelProperty(value = "业务ID:")
	@Column(name="entity_id",length=255,nullable=true,unique=false)
	private Long entityId;

	@DColumn(index=8,label="业务",comment="",component="文本",where=false)
	@ApiModelProperty(value = "业务:")
	@Column(name="business",length=255,nullable=true,unique=false)
	private String business;

	@DColumn(index=9,label="业务信息",comment="",component="文本",where=false)
	@ApiModelProperty(value = "业务信息:")
	@Column(name="business_content",length=500,nullable=true,unique=false)
	private String businessContent;

	@DColumn(index=10,label="相关数据JSON",comment="",component="文本",where=false)
	@ApiModelProperty(value = "相关数据JSON:")
	@Column(name="data",length=8000,nullable=true,unique=false)
	private String data;


	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getEntity() {
		return this.entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getEntityLabel() {
		return this.entityLabel;
	}

	public void setEntityLabel(String entityLabel) {
		this.entityLabel = entityLabel;
	}

	public Long getEntityId() {
		return this.entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getBusiness() {
		return this.business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getBusinessContent() {
		return this.businessContent;
	}

	public void setBusinessContent(String businessContent) {
		this.businessContent = businessContent;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		if(user == null){
		}
		else
		{
		this.userId = user.getEid();
		this.user = user;		
		}
}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



}
