package com.mt.tms.entity.tspositionEntity;

import javax.persistence.*;
import java.io.Serializable;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;




@DEntity(label="职务管理",comment="",moduleLabel="职务管理")
@Entity(name="tms_ts_position")
@Table(name = "tms_ts_position" , indexes = {   })
@ApiModel(description = "职务管理:")
public class TsPosition extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="职务描述",comment="职务描述",component="文本",where=false)
	@ApiModelProperty(value = "职务描述:职务描述")
	@Column(name="position_detail",length=255,nullable=true,unique=false)
	private String positionDetail;


	public String getPositionDetail() {
		return this.positionDetail;
	}

	public void setPositionDetail(String positionDetail) {
		this.positionDetail = positionDetail;
	}



}
