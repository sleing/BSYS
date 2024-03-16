package com.mt.tms.entity.tsdepartmentEntity;

import javax.persistence.*;
import java.io.Serializable;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



import java.util.Date;
import com.mt.tms.entity.tsteacherEntity.TsTeacherInfo;
import com.mt.tms.entity.tsstudentEntity.TsStudentInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@DEntity(label="部门信息管理",comment="",moduleLabel="部门信息管理")
@Entity(name="tms_ts_department")
@Table(name = "tms_ts_department" , indexes = { @Index(name = "index_department_level", columnList = "department_level"),@Index(name = "index_supervisor_tea_id", columnList = "supervisor_tea_id"),@Index(name = "index_supervisor_stu_id", columnList = "supervisor_stu_id"),@Index(name = "index_leader_id", columnList = "leader_id")  })
@ApiModel(description = "部门信息管理:")
public class TsDepartment extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="部门级别",codeTable="部门级别",codeTableOptions="院级,校级",comment="",component="字典下拉单选",where=false)
	@ApiModelProperty(value = "部门级别:")
	@Column(name="department_level",length=255,nullable=true,unique=false)
	private String departmentLevel;

	@DColumn(index=4,label="部门主管老师",foreignEntity="TsTeacherInfo",comment="",component="对象选择",where=false)
	@ApiModelProperty(value = "部门主管老师:")
	@Column(name="supervisor_tea_id",length=255,nullable=true,unique=false)
	private Long supervisorTeaId;

	@Transient
	private TsTeacherInfo supervisorTea;

	@Transient
	@ApiModelProperty(value = "部门主管老师名称:")
	@DColumn(index=4,label="部门主管老师",foreignEntity="TsTeacherInfo",comment="")
	private String supervisorTeaName;

	@DColumn(index=5,label="部门主管学生",foreignEntity="TsStudentInfo",comment="",component="对象选择",where=false)
	@ApiModelProperty(value = "部门主管学生:")
	@Column(name="supervisor_stu_id",length=255,nullable=true,unique=false)
	private Long supervisorStuId;

	@Transient
	private TsStudentInfo supervisorStu;

	@Transient
	@ApiModelProperty(value = "部门主管学生名称:")
	@DColumn(index=5,label="部门主管学生",foreignEntity="TsStudentInfo",comment="")
	private String supervisorStuName;

	@DColumn(index=6,label="部门主要负责人",foreignEntity="TsStudentInfo",comment="",component="对象选择",where=false)
	@ApiModelProperty(value = "部门主要负责人:")
	@Column(name="leader_id",length=255,nullable=true,unique=false)
	private Long leaderId;

	@Transient
	private TsStudentInfo leader;

	@Transient
	@ApiModelProperty(value = "部门主要负责人名称:")
	@DColumn(index=6,label="部门主要负责人",foreignEntity="TsStudentInfo",comment="")
	private String leaderName;

	@DColumn(index=7,label="部门负责人",comment="",component="文本",where=false)
	@ApiModelProperty(value = "部门负责人:")
	@Column(name="vice_leader",length=255,nullable=true,unique=false)
	private String viceLeader;

	@DColumn(index=8,label="部门成员",comment="",component="文本",where=false)
	@ApiModelProperty(value = "部门成员:")
	@Column(name="member",length=255,nullable=true,unique=false)
	private String member;

	@DColumn(index=9,label="部门描述",comment="",component="文本",where=false)
	@ApiModelProperty(value = "部门描述:")
	@Column(name="description",length=255,nullable=true,unique=false)
	private String description;

	@DColumn(index=10,label="成立日期",comment="",component="日期选择",where=false)
	@ApiModelProperty(value = "成立日期:")
	@Column(name="found_date",length=255,nullable=true,unique=false)
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date foundDate;


	public String getDepartmentLevel() {
		return this.departmentLevel;
	}

	public void setDepartmentLevel(String departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	public Long getSupervisorTeaId() {
		return this.supervisorTeaId;
	}

	public void setSupervisorTeaId(Long supervisorTeaId) {
		this.supervisorTeaId = supervisorTeaId;
	}

	public Long getSupervisorStuId() {
		return this.supervisorStuId;
	}

	public void setSupervisorStuId(Long supervisorStuId) {
		this.supervisorStuId = supervisorStuId;
	}

	public Long getLeaderId() {
		return this.leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public String getViceLeader() {
		return this.viceLeader;
	}

	public void setViceLeader(String viceLeader) {
		this.viceLeader = viceLeader;
	}

	public String getMember() {
		return this.member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFoundDate() {
		return this.foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public TsTeacherInfo getSupervisorTea() {
		return this.supervisorTea;
	}

	public void setSupervisorTea(TsTeacherInfo supervisorTea) {
		if(supervisorTea == null){
		}
		else
		{
		this.supervisorTeaId = supervisorTea.getEid();
		this.supervisorTea = supervisorTea;		
		}
}

	public String getSupervisorTeaName() {
		return this.supervisorTeaName;
	}

	public void setSupervisorTeaName(String supervisorTeaName) {
		this.supervisorTeaName = supervisorTeaName;
	}

	public TsStudentInfo getSupervisorStu() {
		return this.supervisorStu;
	}

	public void setSupervisorStu(TsStudentInfo supervisorStu) {
		if(supervisorStu == null){
		}
		else
		{
		this.supervisorStuId = supervisorStu.getEid();
		this.supervisorStu = supervisorStu;		
		}
}

	public String getSupervisorStuName() {
		return this.supervisorStuName;
	}

	public void setSupervisorStuName(String supervisorStuName) {
		this.supervisorStuName = supervisorStuName;
	}

	public TsStudentInfo getLeader() {
		return this.leader;
	}

	public void setLeader(TsStudentInfo leader) {
		if(leader == null){
		}
		else
		{
		this.leaderId = leader.getEid();
		this.leader = leader;		
		}
}

	public String getLeaderName() {
		return this.leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}



}
