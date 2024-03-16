package com.mt.ams.entity.appraisingTypeEntity;

import javax.persistence.*;
import java.io.Serializable;

import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@DEntity(label = "学生处评优评先类别信息", comment = "", moduleLabel = "学生处评优评先类别")
@Entity(name = "ams_appraising_type")
@Table(name = "ams_appraising_type", indexes = {})
@ApiModel(description = "学生处评优评先类别信息:")
public class AppraisingType extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @DColumn(index = 3, label = "介绍", comment = "介绍", component = "文本", where = false)
    @ApiModelProperty(value = "介绍:介绍")
    @Column(name = "introduction", length = 255, nullable = true, unique = false)
    private String introduction;


    public String getIntroduction() {
        return this.introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


}
