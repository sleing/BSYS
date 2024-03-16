package com.mt.ams.entity.competitionEntity;

import javax.persistence.*;
import java.io.Serializable;

import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import com.mt.ams.entity.unitEntity.Unit;

@DEntity(label = "竞赛信息", comment = "", moduleLabel = "竞赛")
@Entity(name = "ams_competition")
@Table(name = "ams_competition", indexes = {@Index(name = "index_award_level", columnList = "award_level"), @Index(name = "index_units_id", columnList = "units_id"), @Index(name = "index_competition_category", columnList = "competition_category"), @Index(name = "index_competition_level", columnList = "competition_level")})
@ApiModel(description = "竞赛信息:")
public class Competition extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @DColumn(index = 3, label = "获奖级别", codeTable = "获奖级别", codeTableOptions = "国家级，省市级，省级，市级，校级，院级", comment = "获奖的级别", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "获奖级别:获奖的级别")
    @Column(name = "award_level", length = 255, nullable = true, unique = false)
    private String awardLevel;

    @DColumn(index = 4, label = "主办方", foreignEntity = "Unit", comment = "竞赛主办方", component = "对象选择", where = false)
    @ApiModelProperty(value = "主办方:竞赛主办方")
    @Column(name = "units_id", length = 255, nullable = true, unique = false)
    private Long unitsId;

    @Transient
    private Unit units;

    @Transient
    @ApiModelProperty(value = "主办方名称:竞赛主办方")
    @DColumn(index = 4, label = "主办方", foreignEntity = "Unit", comment = "竞赛主办方")
    private String unitsName;

    @DColumn(index = 5, label = "类别", codeTable = "竞赛类别", codeTableOptions = "政府类,学会类,未定类", comment = "未认定", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "类别:未认定")
    @Column(name = "competition_category", length = 255, nullable = true, unique = false)
    private String competitionCategory;

    @DColumn(index = 6, label = "级别", codeTable = "竞赛级别", codeTableOptions = "I,II,未定级", comment = "未认定", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "级别:未认定")
    @Column(name = "competition_level", length = 255, nullable = true, unique = false)
    private String competitionLevel;


    public String getAwardLevel() {
        return this.awardLevel;
    }

    public void setAwardLevel(String awardLevel) {
        this.awardLevel = awardLevel;
    }

    public Long getUnitsId() {
        return this.unitsId;
    }

    public void setUnitsId(Long unitsId) {
        this.unitsId = unitsId;
    }

    public String getCompetitionCategory() {
        return this.competitionCategory;
    }

    public void setCompetitionCategory(String competitionCategory) {
        this.competitionCategory = competitionCategory;
    }

    public String getCompetitionLevel() {
        return this.competitionLevel;
    }

    public void setCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    public Unit getUnits() {
        return this.units;
    }

    public void setUnits(Unit units) {
        if (units == null) {
        } else {
            this.unitsId = units.getEid();
            this.units = units;
        }
    }

    public String getUnitsName() {
        return this.unitsName;
    }

    public void setUnitsName(String unitsName) {
        this.unitsName = unitsName;
    }


}
