package com.mt.tms.entity.tsworklaceEntity;

import javax.persistence.*;
import java.io.Serializable;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;




@DEntity(label="办公地点管理",comment="",moduleLabel="办公地点管理")
@Entity(name="tms_ts_work_place")
@Table(name = "tms_ts_work_place" , indexes = {   })
@ApiModel(description = "办公地点管理:")
public class TsWorkPlace extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="办公地点",comment="办公地点",component="文本",where=false)
	@ApiModelProperty(value = "办公地点:办公地点")
	@Column(name="address",length=255,nullable=true,unique=false)
	private String address;


	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}



}
