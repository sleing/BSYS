package com.mt.ams.entity.unitEntity;

import javax.persistence.*;
import java.io.Serializable;

import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@DEntity(label = "举办单位信息", comment = "", moduleLabel = "举办单位")
@Entity(name = "ams_unit")
@Table(name = "ams_unit", indexes = {})
@ApiModel(description = "举办单位信息:")
public class Unit extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


}
