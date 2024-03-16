package com.mt.tms.entity.tsactivityEntity;

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
import com.mt.tms.entity.tsstudentEntity.TsStudentInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@DEntity(label="活动中心",comment="",moduleLabel="活动中心")
@Entity(name="tms_ts_activity")
@Table(name = "tms_ts_activity" , indexes = { @Index(name = "index_activity_host_id", columnList = "activity_host_id")  })
@ApiModel(description = "活动中心:")
public class TsActivity extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="序号",comment="序号",component="数字，整数",where=false)
	@ApiModelProperty(value = "序号:序号")
	@Column(name="activity_index",length=255,nullable=false,unique=false)
	private Integer activityIndex;

	@DColumn(index=4,label="活动类型",comment="活动类型",component="文本",where=false)
	@ApiModelProperty(value = "活动类型:活动类型")
	@Column(name="activity_type",length=255,nullable=true,unique=false)
	private String activityType;

	@DColumn(index=5,label="活动时间",comment="活动时间",component="日期选择",where=false)
	@ApiModelProperty(value = "活动时间:活动时间")
	@Column(name="activity_date",length=255,nullable=false,unique=false)
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date activityDate;

	@DColumn(index=6,label="活动主持人",foreignEntity="TsStudentInfo",comment="活动主持人",component="对象选择",where=false)
	@ApiModelProperty(value = "活动主持人:活动主持人")
	@Column(name="activity_host_id",length=255,nullable=true,unique=false)
	private Long activityHostId;

	@Transient
	private TsStudentInfo activityHost;

	@Transient
	@ApiModelProperty(value = "活动主持人名称:活动主持人")
	@DColumn(index=6,label="活动主持人",foreignEntity="TsStudentInfo",comment="活动主持人")
	private String activityHostName;

	@DColumn(index=7,label="活动地点",comment="活动地点",component="文本",where=false)
	@ApiModelProperty(value = "活动地点:活动地点")
	@Column(name="activity_add",length=255,nullable=true,unique=false)
	private String activityAdd;

	@DColumn(index=8,label="参与人员",comment="参与人员",component="文本",where=false)
	@ApiModelProperty(value = "参与人员:参与人员")
	@Column(name="participant",length=255,nullable=true,unique=false)
	private String participant;

	@DColumn(index=9,label="活动内容",comment="活动内容",component="文本",where=false)
	@ApiModelProperty(value = "活动内容:活动内容")
	@Column(name="activity_content",length=255,nullable=true,unique=false)
	private String activityContent;

	@DColumn(index=10,label="活动评价",comment="活动评价",component="文本",where=false)
	@ApiModelProperty(value = "活动评价:活动评价")
	@Column(name="activity_eval",length=255,nullable=true,unique=false)
	private String activityEval;

	@DColumn(index=11,label="活动资料",comment="",component="文本",where=false)
	@ApiModelProperty(value = "活动资料:")
	@Column(name="activity_file",length=255,nullable=true,unique=false)
	private String activityFile;

	@DColumn(index=12,label="举办时长",comment="",component="文本",where=false)
	@ApiModelProperty(value = "举办时长:")
	@Column(name="hold_time",length=255,nullable=true,unique=false)
	private String holdTime;


	public Integer getActivityIndex() {
		return this.activityIndex;
	}

	public void setActivityIndex(Integer activityIndex) {
		this.activityIndex = activityIndex;
	}

	public String getActivityType() {
		return this.activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Date getActivityDate() {
		return this.activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Long getActivityHostId() {
		return this.activityHostId;
	}

	public void setActivityHostId(Long activityHostId) {
		this.activityHostId = activityHostId;
	}

	public String getActivityAdd() {
		return this.activityAdd;
	}

	public void setActivityAdd(String activityAdd) {
		this.activityAdd = activityAdd;
	}

	public String getParticipant() {
		return this.participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getActivityContent() {
		return this.activityContent;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}

	public String getActivityEval() {
		return this.activityEval;
	}

	public void setActivityEval(String activityEval) {
		this.activityEval = activityEval;
	}

	public String getActivityFile() {
		return this.activityFile;
	}

	public void setActivityFile(String activityFile) {
		this.activityFile = activityFile;
	}

	public String getHoldTime() {
		return this.holdTime;
	}

	public void setHoldTime(String holdTime) {
		this.holdTime = holdTime;
	}

	public TsStudentInfo getActivityHost() {
		return this.activityHost;
	}

	public void setActivityHost(TsStudentInfo activityHost) {
		if(activityHost == null){
		}
		else
		{
		this.activityHostId = activityHost.getEid();
		this.activityHost = activityHost;		
		}
}

	public String getActivityHostName() {
		return this.activityHostName;
	}

	public void setActivityHostName(String activityHostName) {
		this.activityHostName = activityHostName;
	}



}
