package com.mt.tms.entity.tsCollegeEntity;

import javax.persistence.*;
import java.io.Serializable;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;




@DEntity(label="院系信息",comment="",moduleLabel="院系信息")
@Entity(name="tms_ts_college")
@Table(name = "tms_ts_college" , indexes = {   })
@ApiModel(description = "院系信息:")
public class TsCollege extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="备注",comment="备注",component="文本",where=false)
	@ApiModelProperty(value = "备注:备注")
	@Column(name="ts_remark",length=255,nullable=true,unique=false)
	private String tsRemark;


	public String getTsRemark() {
		return this.tsRemark;
	}

	public void setTsRemark(String tsRemark) {
		this.tsRemark = tsRemark;
	}



}
