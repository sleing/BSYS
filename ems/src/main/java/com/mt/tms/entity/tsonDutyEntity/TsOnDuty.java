package com.mt.tms.entity.tsonDutyEntity;

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
import com.mt.tms.entity.tsworklaceEntity.TsWorkPlace;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@DEntity(label="值班管理",comment="",moduleLabel="值班信息")
@Entity(name="tms_ts_on_duty")
@Table(name = "tms_ts_on_duty" , indexes = { @Index(name = "index_on_duty_address_id", columnList = "on_duty_address_id"),@Index(name = "index_on_duty_stu_id", columnList = "on_duty_stu_id")  })
@ApiModel(description = "值班管理:")
public class TsOnDuty extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="值班时间",comment="老师工号",component="日期选择",where=false)
	@ApiModelProperty(value = "值班时间:老师工号")
	@Column(name="on_duty_time",length=255,nullable=false,unique=true)
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date onDutyTime;

	@DColumn(index=4,label="值班地点",foreignEntity="TsWorkPlace",comment="",component="对象选择",where=false)
	@ApiModelProperty(value = "值班地点:")
	@Column(name="on_duty_address_id",length=255,nullable=true,unique=false)
	private Long onDutyAddressId;

	@Transient
	private TsWorkPlace onDutyAddress;

	@Transient
	@ApiModelProperty(value = "值班地点名称:")
	@DColumn(index=4,label="值班地点",foreignEntity="TsWorkPlace",comment="")
	private String onDutyAddressName;

	@DColumn(index=5,label="值班人员",foreignEntity="TsStudentInfo",comment="",component="对象选择",where=false)
	@ApiModelProperty(value = "值班人员:")
	@Column(name="on_duty_stu_id",length=255,nullable=true,unique=false)
	private Long onDutyStuId;

	@Transient
	private TsStudentInfo onDutyStu;

	@Transient
	@ApiModelProperty(value = "值班人员名称:")
	@DColumn(index=5,label="值班人员",foreignEntity="TsStudentInfo",comment="")
	private String onDutyStuName;


	public Date getOnDutyTime() {
		return this.onDutyTime;
	}

	public void setOnDutyTime(Date onDutyTime) {
		this.onDutyTime = onDutyTime;
	}

	public Long getOnDutyAddressId() {
		return this.onDutyAddressId;
	}

	public void setOnDutyAddressId(Long onDutyAddressId) {
		this.onDutyAddressId = onDutyAddressId;
	}

	public Long getOnDutyStuId() {
		return this.onDutyStuId;
	}

	public void setOnDutyStuId(Long onDutyStuId) {
		this.onDutyStuId = onDutyStuId;
	}

	public TsWorkPlace getOnDutyAddress() {
		return this.onDutyAddress;
	}

	public void setOnDutyAddress(TsWorkPlace onDutyAddress) {
		if(onDutyAddress == null){
		}
		else
		{
		this.onDutyAddressId = onDutyAddress.getEid();
		this.onDutyAddress = onDutyAddress;		
		}
}

	public String getOnDutyAddressName() {
		return this.onDutyAddressName;
	}

	public void setOnDutyAddressName(String onDutyAddressName) {
		this.onDutyAddressName = onDutyAddressName;
	}

	public TsStudentInfo getOnDutyStu() {
		return this.onDutyStu;
	}

	public void setOnDutyStu(TsStudentInfo onDutyStu) {
		if(onDutyStu == null){
		}
		else
		{
		this.onDutyStuId = onDutyStu.getEid();
		this.onDutyStu = onDutyStu;		
		}
}

	public String getOnDutyStuName() {
		return this.onDutyStuName;
	}

	public void setOnDutyStuName(String onDutyStuName) {
		this.onDutyStuName = onDutyStuName;
	}



}
