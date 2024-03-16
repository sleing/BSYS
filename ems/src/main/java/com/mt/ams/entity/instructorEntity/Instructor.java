package com.mt.ams.entity.instructorEntity;

import javax.persistence.*;
import java.io.Serializable;

import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import com.mt.ams.entity.teacherEntity.TeacherInfo;
import com.mt.ams.entity.awardEntity.Award;

@DEntity(label = "指导老师信息", comment = "", moduleLabel = "指导老师")
@Entity(name = "ams_instructor")
@Table(name = "ams_instructor", indexes = {@Index(name = "index_teacher_id", columnList = "teacher_id"), @Index(name = "index_award_id", columnList = "award_id")})
@ApiModel(description = "指导老师信息:")
public class Instructor extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @DColumn(index = 3, label = "序号", comment = "序号", component = "数字，整数", where = false)
    @ApiModelProperty(value = "序号:序号")
    @Column(name = "display_index", length = 255, nullable = false, unique = false)
    private Integer displayIndex;

    @DColumn(index = 4, label = "老师实体", foreignEntity = "TeacherInfo", comment = "老师实体", component = "对象选择", where = false)
    @ApiModelProperty(value = "老师实体:老师实体")
    @Column(name = "teacher_id", length = 255, nullable = false, unique = false)
    private Long teacherId;

    @Transient
    private TeacherInfo teacher;

    @Transient
    @ApiModelProperty(value = "老师实体名称:老师实体")
    @DColumn(index = 4, label = "老师实体", foreignEntity = "TeacherInfo", comment = "老师实体")
    private String teacherName;

    @DColumn(index = 5, label = "获奖信息", foreignEntity = "Award", comment = "获奖的信息", component = "对象选择", where = false)
    @ApiModelProperty(value = "获奖信息:获奖的信息")
    @Column(name = "award_id", length = 255, nullable = false, unique = false)
    private Long awardId;

    @Transient
    private Award award;

    @Transient
    @ApiModelProperty(value = "获奖信息名称:获奖的信息")
    @DColumn(index = 5, label = "获奖信息", foreignEntity = "Award", comment = "获奖的信息")
    private String awardName;


    public Integer getDisplayIndex() {
        return this.displayIndex;
    }

    public void setDisplayIndex(Integer displayIndex) {
        this.displayIndex = displayIndex;
    }

    public Long getTeacherId() {
        return this.teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getAwardId() {
        return this.awardId;
    }

    public void setAwardId(Long awardId) {
        this.awardId = awardId;
    }

    public TeacherInfo getTeacher() {
        return this.teacher;
    }

    public void setTeacher(TeacherInfo teacher) {
        if (teacher == null) {
        } else {
            this.teacherId = teacher.getEid();
            this.teacher = teacher;
        }
    }

    public String getTeacherName() {
        return this.teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Award getAward() {
        return this.award;
    }

    public void setAward(Award award) {
        if (award == null) {
        } else {
            this.awardId = award.getEid();
            this.award = award;
        }
    }

    public String getAwardName() {
        return this.awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }


}
