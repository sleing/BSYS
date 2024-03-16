package com.mt.tms.entity.tsteacherEntity;

import javax.persistence.*;
import java.io.Serializable;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



import com.mt.tms.entity.tspositionEntity.TsPosition;
import com.mt.tms.entity.tsCollegeEntity.TsCollege;

@DEntity(label="老师信息管理",comment="",moduleLabel="老师信息管理")
@Entity(name="tms_ts_teacher_info")
@Table(name = "tms_ts_teacher_info" , indexes = { @Index(name = "index_college_id", columnList = "college_id"),@Index(name = "index_position_id", columnList = "position_id")  })
@ApiModel(description = "老师信息管理:")
	public class TsTeacherInfo extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="老师工号",comment="老师工号",component="文本",where=false)
	@ApiModelProperty(value = "老师工号:老师工号")
	@Column(name="teacher_id",length=255,nullable=false,unique=true)
	private String teacherId;

	@DColumn(index=4,label="学院",foreignEntity="TsCollege",comment="老师所在学院",component="对象选择",where=false)
	@ApiModelProperty(value = "学院:老师所在学院")
	@Column(name="college_id",length=255,nullable=true,unique=false)
	private Long collegeId;

	@Transient
	private TsCollege college;

	@Transient
	@ApiModelProperty(value = "学院名称:老师所在学院")
	@DColumn(index=4,label="学院",foreignEntity="TsCollege",comment="老师所在学院")
	private String collegeName;

	@DColumn(index=5,label="邮箱",comment="老师邮箱",component="文本",where=false)
	@ApiModelProperty(value = "邮箱:老师邮箱")
	@Column(name="email",length=255,nullable=true,unique=false)
	private String email;

	@DColumn(index=6,label="职务",foreignEntity="TsPosition",comment="",component="对象选择",where=false)
	@ApiModelProperty(value = "职务:")
	@Column(name="position_id",length=255,nullable=true,unique=false)
	private Long positionId;

	@Transient
	private TsPosition position;

	@Transient
	@ApiModelProperty(value = "职务名称:")
	@DColumn(index=6,label="职务",foreignEntity="TsPosition",comment="")
	private String positionName;

	@DColumn(index=7,label="联系电话",comment="",component="文本",where=false)
	@ApiModelProperty(value = "联系电话:")
	@Column(name="tel_number",length=255,nullable=true,unique=false)
	private String telNumber;


	public String getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public Long getCollegeId() {
		return this.collegeId;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getTelNumber() {
		return this.telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
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

	public TsPosition getPosition() {
		return this.position;
	}

	public void setPosition(TsPosition position) {
		if(position == null){
		}
		else
		{
		this.positionId = position.getEid();
		this.position = position;		
		}
}

	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}



}
