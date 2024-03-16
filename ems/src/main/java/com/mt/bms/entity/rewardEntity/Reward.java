package com.mt.bms.entity.rewardEntity;

import javax.persistence.*;
import java.io.Serializable;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@DEntity(label="获奖登记",comment="",moduleLabel="学生获奖管理")
@Entity(name="bms_reward")
@Table(name = "bms_reward" , indexes = {   })
@ApiModel(description = "获奖登记:")
public class Reward extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="级别",comment="级别",component="文本",where=false)
	@ApiModelProperty(value = "级别:级别")
	@Column(name="level",length=255,nullable=false,unique=false)
	private String level;

	@DColumn(index=4,label="竞赛名称",comment="竞赛名称",component="文本",where=false)
	@ApiModelProperty(value = "竞赛名称:竞赛名称")
	@Column(name="title",length=255,nullable=false,unique=false)
	private String title;

	@DColumn(index=5,label="指导老师",comment="指导老师",component="文本",where=false)
	@ApiModelProperty(value = "指导老师:指导老师")
	@Column(name="instructors",length=255,nullable=true,unique=false)
	private String instructors;

	@DColumn(index=6,label="证明文件",comment="证明文件",component="文本",where=false)
	@ApiModelProperty(value = "证明文件:证明文件")
	@Column(name="evidence",length=255,nullable=true,unique=false)
	private String evidence;

	@DColumn(index=7,label="时间",comment="时间",component="日期选择",where=false)
	@ApiModelProperty(value = "时间:时间")
	@Column(name="award_time",length=255,nullable=true,unique=false)
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date awardTime;

	@DColumn(index=8,label="举办单位",comment="举办单位",component="文本",where=false)
	@ApiModelProperty(value = "举办单位:举办单位")
	@Column(name="organization",length=255,nullable=true,unique=false)
	private String organization;

	@DColumn(index=9,label="获奖学生",comment="获奖学生",component="文本",where=false)
	@ApiModelProperty(value = "获奖学生:获奖学生")
	@Column(name="all_students",length=255,nullable=true,unique=false)
	private String allStudents;

	@DColumn(index=10,label="审核状态",comment="审核状态",component="文本",where=false)
	@ApiModelProperty(value = "审核状态:审核状态")
	@Column(name="audit_status",length=255,nullable=true,unique=false)
	private String auditStatus;

	@DColumn(index=11,label="作品名称",comment="作品名称",component="文本",where=false)
	@ApiModelProperty(value = "作品名称:作品名称")
	@Column(name="work_name",length=255,nullable=true,unique=false)
	private String workName;

	@DColumn(index=12,label="获奖电话",comment="获奖电话",component="文本",where=false)
	@ApiModelProperty(value = "获奖电话:获奖电话")
	@Column(name="tel",length=255,nullable=true,unique=false)
	private String tel;

	@DColumn(index=13,label="学院",comment="学院",component="文本",where=false)
	@ApiModelProperty(value = "学院:学院")
	@Column(name="college",length=255,nullable=true,unique=false)
	private String college;


	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstructors() {
		return this.instructors;
	}

	public void setInstructors(String instructors) {
		this.instructors = instructors;
	}

	public String getEvidence() {
		return this.evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	public Date getAwardTime() {
		return this.awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	public String getOrganization() {
		return this.organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getAllStudents() {
		return this.allStudents;
	}

	public void setAllStudents(String allStudents) {
		this.allStudents = allStudents;
	}

	public String getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getWorkName() {
		return this.workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCollege() {
		return this.college;
	}

	public void setCollege(String college) {
		this.college = college;
	}



}
