package com.mt.ams.entity.collegeEntity;

import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


@DEntity(label = "学院信息", comment = "", moduleLabel = "学院")
@Entity(name = "ams_college")
@Table(name = "ams_college", indexes = {})
@ApiModel(description = "学院信息:")
public class College extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


}
