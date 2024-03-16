package com.mt.ems.entity.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@DEntity(label="学生",comment="",moduleLabel="学生管理")
@Entity(name="ems_student")
@Table(name = "ems_student" , indexes = { @Index(name = "index_gender", columnList = "gender")  })
@ApiModel(description = "学生:")
public class Student extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="学生姓名",comment="名称",component="文本",where=false)
	@ApiModelProperty(value = "学生姓名:名称")
	@Column(name="student_name",length=20,nullable=false,unique=false)
	private String studentName;

	@DColumn(index=4,label="身份证号",comment="身份证号",component="文本",where=false)
	@ApiModelProperty(value = "身份证号:身份证号")
	@Column(name="student_id_num",length=255,nullable=false,unique=true)
	private String studentIdNum;

	@DColumn(index=5,label="年龄",comment="年龄",component="数字，整数",where=false)
	@ApiModelProperty(value = "年龄:年龄")
	@Column(name="student_age",length=255,nullable=true,unique=false)
	private Integer studentAge;

	@DColumn(index=6,label="入学时间",comment="入学时间",component="日期选择",where=false)
	@ApiModelProperty(value = "入学时间:入学时间")
	@Column(name="date_of_start_study",length=255,nullable=true,unique=false)
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateOfStartStudy;

	@DColumn(index=7,label="分数",comment="分数",component="数字，整数",where=false)
	@ApiModelProperty(value = "分数:分数")
	@Column(name="score",length=255,nullable=true,unique=false)
	private Long score;

	@DColumn(index=8,label="入职日期",comment="入职的时间",component="日期选择",where=false)
	@ApiModelProperty(value = "入职日期:入职的时间")
	@Column(name="date_of_join",length=255,nullable=true,unique=false)
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateOfJoin;

	@DColumn(index=9,label="性别",codeTable="Gender",codeTableOptions="男,女,未知",comment="性别",component="字典下拉单选",where=false)
	@ApiModelProperty(value = "性别:性别")
	@Column(name="gender",length=255,nullable=true,unique=false)
	private String gender;


	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentIdNum() {
		return this.studentIdNum;
	}

	public void setStudentIdNum(String studentIdNum) {
		this.studentIdNum = studentIdNum;
	}

	public Integer getStudentAge() {
		return this.studentAge;
	}

	public void setStudentAge(Integer studentAge) {
		this.studentAge = studentAge;
	}

	public Date getDateOfStartStudy() {
		return this.dateOfStartStudy;
	}

	public void setDateOfStartStudy(Date dateOfStartStudy) {
		this.dateOfStartStudy = dateOfStartStudy;
	}

	public Long getScore() {
		return this.score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Date getDateOfJoin() {
		return this.dateOfJoin;
	}

	public void setDateOfJoin(Date dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}



}
