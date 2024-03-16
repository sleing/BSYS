package com.mt.tms.entity.tsstudentEntity;

import javax.persistence.*;
import java.io.Serializable;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



import com.mt.tms.entity.tsCollegeEntity.TsCollege;

@DEntity(label="团学会学生信息管理",comment="",moduleLabel="团学会学生信息管理")
@Entity(name="tms_ts_student_info")
@Table(name = "tms_ts_student_info" , indexes = { @Index(name = "index_college_id", columnList = "college_id"),@Index(name = "index_politics", columnList = "politics")  })
@ApiModel(description = "团学会学生信息管理:")
public class TsStudentInfo extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="学号",comment="请输入学号",component="文本",where=false)
	@ApiModelProperty(value = "学号:请输入学号")
	@Column(name="student_id",length=255,nullable=false,unique=true)
	private String studentId;

	@DColumn(index=4,label="学院",foreignEntity="TsCollege",comment="请输入学院",component="对象选择",where=false)
	@ApiModelProperty(value = "学院:请输入学院")
	@Column(name="college_id",length=255,nullable=false,unique=false)
	private Long collegeId;

	@Transient
	private TsCollege college;

	@Transient
	@ApiModelProperty(value = "学院名称:请输入学院")
	@DColumn(index=4,label="学院",foreignEntity="TsCollege",comment="请输入学院")
	private String collegeName;

	@DColumn(index=5,label="专业",comment="请输入专业",component="文本",where=false)
	@ApiModelProperty(value = "专业:请输入专业")
	@Column(name="major",length=255,nullable=true,unique=false)
	private String major;

	@DColumn(index=6,label="年级",comment="请输入年级",component="文本",where=false)
	@ApiModelProperty(value = "年级:请输入年级")
	@Column(name="grade",length=255,nullable=true,unique=false)
	private String grade;

	@DColumn(index=7,label="班级",comment="请输入班级",component="文本",where=false)
	@ApiModelProperty(value = "班级:请输入班级")
	@Column(name="class_grade",length=255,nullable=true,unique=false)
	private String classGrade;

	@DColumn(index=8,label="联系电话",comment="请输入电话",component="文本",where=false)
	@ApiModelProperty(value = "联系电话:请输入电话")
	@Column(name="contact_tel",length=255,nullable=true,unique=false)
	private String contactTel;

	@DColumn(index=9,label="联系邮箱",comment="请输入邮箱",component="文本",where=false)
	@ApiModelProperty(value = "联系邮箱:请输入邮箱")
	@Column(name="email",length=255,nullable=true,unique=false)
	private String email;

	@DColumn(index=10,label="政治面貌",codeTable="政治面貌",codeTableOptions="中共党员,预备党员,共青团员,群众,其他",comment="请选择政治面貌",component="字典下拉单选",where=false)
	@ApiModelProperty(value = "政治面貌:请选择政治面貌")
	@Column(name="politics",length=255,nullable=true,unique=false)
	private String politics;


	//111

	public String getStudentId() {
		return this.studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Long getCollegeId() {
		return this.collegeId;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClassGrade() {
		return this.classGrade;
	}

	public void setClassGrade(String classGrade) {
		this.classGrade = classGrade;
	}

	public String getContactTel() {
		return this.contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPolitics() {
		return this.politics;
	}

	public void setPolitics(String politics) {
		this.politics = politics;
	}

	public TsCollege getCollege() {
		return this.college;
	}

	public void setCollege(TsCollege college) {
		if(college == null){
		}
		else
		{
		this.collegeId = college.getEid();
		this.college = college;		
		}
}

	public String getCollegeName() {
		return this.collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}



}
