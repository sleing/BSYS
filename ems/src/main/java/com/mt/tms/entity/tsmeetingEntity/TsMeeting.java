package com.mt.tms.entity.tsmeetingEntity;

import javax.persistence.*;
import java.io.Serializable;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



import java.util.Date;
import com.mt.tms.entity.tsdepartmentEntity.TsDepartment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@DEntity(label="会议管理",comment="",moduleLabel="会议管理")
@Entity(name="tms_ts_meeting")
@Table(name = "tms_ts_meeting" , indexes = { @Index(name = "index_organization_id", columnList = "organization_id"),@Index(name = "index_vice_organization_id", columnList = "vice_organization_id"),@Index(name = "index_meeting_mode", columnList = "meeting_mode")  })
@ApiModel(description = "会议管理:")
public class TsMeeting extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="会议主题",comment="单位名称",component="文本",where=false)
	@ApiModelProperty(value = "会议主题:单位名称")
	@Column(name="theme",length=255,nullable=false,unique=false)
	private String theme;

	@DColumn(index=4,label="主办单位",foreignEntity="TsDepartment",comment="",component="对象选择",where=false)
	@ApiModelProperty(value = "主办单位:")
	@Column(name="organization_id",length=255,nullable=true,unique=false)
	private Long organizationId;

	@Transient
	private TsDepartment organization;

	@Transient
	@ApiModelProperty(value = "主办单位名称:")
	@DColumn(index=4,label="主办单位",foreignEntity="TsDepartment",comment="")
	private String organizationName;

	@DColumn(index=5,label="协办单位",foreignEntity="TsDepartment",comment="",component="对象选择",where=false)
	@ApiModelProperty(value = "协办单位:")
	@Column(name="vice_organization_id",length=255,nullable=true,unique=false)
	private Long viceOrganizationId;

	@Transient
	private TsDepartment viceOrganization;

	@Transient
	@ApiModelProperty(value = "协办单位名称:")
	@DColumn(index=5,label="协办单位",foreignEntity="TsDepartment",comment="")
	private String viceOrganizationName;

	@DColumn(index=6,label="会议时间",comment="",component="日期选择",where=false)
	@ApiModelProperty(value = "会议时间:")
	@Column(name="meeting_date",length=255,nullable=true,unique=false)
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date meetingDate;

	@DColumn(index=7,label="参会人员",comment="",component="文本",where=false)
	@ApiModelProperty(value = "参会人员:")
	@Column(name="conferee",length=255,nullable=true,unique=false)
	private String conferee;

	@DColumn(index=8,label="会议记录",comment="",component="文本",where=false)
	@ApiModelProperty(value = "会议记录:")
	@Column(name="meeting_record",length=255,nullable=true,unique=false)
	private String meetingRecord;

	@DColumn(index=9,label="会议方式",codeTable="会议方式",codeTableOptions="线下,线上",comment="",component="字典下拉单选",where=false)
	@ApiModelProperty(value = "会议方式:")
	@Column(name="meeting_mode",length=255,nullable=true,unique=false)
	private String meetingMode;

	@DColumn(index=10,label="会议地址",comment="",component="文本",where=false)
	@ApiModelProperty(value = "会议地址:")
	@Column(name="meeting_address",length=255,nullable=true,unique=false)
	private String meetingAddress;


	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getViceOrganizationId() {
		return this.viceOrganizationId;
	}

	public void setViceOrganizationId(Long viceOrganizationId) {
		this.viceOrganizationId = viceOrganizationId;
	}

	public Date getMeetingDate() {
		return this.meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getConferee() {
		return this.conferee;
	}

	public void setConferee(String conferee) {
		this.conferee = conferee;
	}

	public String getMeetingRecord() {
		return this.meetingRecord;
	}

	public void setMeetingRecord(String meetingRecord) {
		this.meetingRecord = meetingRecord;
	}

	public String getMeetingMode() {
		return this.meetingMode;
	}

	public void setMeetingMode(String meetingMode) {
		this.meetingMode = meetingMode;
	}

	public String getMeetingAddress() {
		return this.meetingAddress;
	}

	public void setMeetingAddress(String meetingAddress) {
		this.meetingAddress = meetingAddress;
	}

	public TsDepartment getOrganization() {
		return this.organization;
	}

	public void setOrganization(TsDepartment organization) {
		if(organization == null){
		}
		else
		{
		this.organizationId = organization.getEid();
		this.organization = organization;		
		}
}

	public String getOrganizationName() {
		return this.organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public TsDepartment getViceOrganization() {
		return this.viceOrganization;
	}

	public void setViceOrganization(TsDepartment viceOrganization) {
		if(viceOrganization == null){
		}
		else
		{
		this.viceOrganizationId = viceOrganization.getEid();
		this.viceOrganization = viceOrganization;		
		}
}

	public String getViceOrganizationName() {
		return this.viceOrganizationName;
	}

	public void setViceOrganizationName(String viceOrganizationName) {
		this.viceOrganizationName = viceOrganizationName;
	}



}
