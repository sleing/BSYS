package com.mt.ams.entity.awardeeEntity;

import javax.persistence.*;
import java.io.Serializable;

import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.ams.entity.awardEntity.Award;

@DEntity(label = "获奖学生信息", comment = "", moduleLabel = "获奖学生")
@Entity(name = "ams_awardee")
@Table(name = "ams_awardee", indexes = {@Index(name = "index_student_id", columnList = "student_id"), @Index(name = "index_award_id", columnList = "award_id")})
@ApiModel(description = "获奖学生信息:")
public class Awardee extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @DColumn(index = 3, label = "序号", comment = "序号", component = "数字，整数", where = false)
    @ApiModelProperty(value = "序号:序号")
    @Column(name = "display_index", length = 255, nullable = false, unique = false)
    private Integer displayIndex;

    @DColumn(index = 4, label = "学生实体", foreignEntity = "StudentInfo", comment = "学生信息", component = "对象选择", where = false)
    @ApiModelProperty(value = "学生实体:学生信息")
    @Column(name = "student_id", length = 255, nullable = false, unique = false)
    private Long studentId;

    @Transient
    private StudentInfo student;

    @Transient
    @ApiModelProperty(value = "学生实体名称:学生信息")
    @DColumn(index = 4, label = "学生实体", foreignEntity = "StudentInfo", comment = "学生信息")
    private String studentName;

    @DColumn(index = 5, label = "获奖信息", foreignEntity = "Award", comment = "获奖信息", component = "对象选择", where = false)
    @ApiModelProperty(value = "获奖信息:获奖信息")
    @Column(name = "award_id", length = 255, nullable = false, unique = false)
    private Long awardId;

    @Transient
    private Award award;

    @Transient
    @ApiModelProperty(value = "获奖信息名称:获奖信息")
    @DColumn(index = 5, label = "获奖信息", foreignEntity = "Award", comment = "获奖信息")
    private String awardName;


    public Integer getDisplayIndex() {
        return this.displayIndex;
    }

    public void setDisplayIndex(Integer displayIndex) {
        this.displayIndex = displayIndex;
    }

    public Long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getAwardId() {
        return this.awardId;
    }

    public void setAwardId(Long awardId) {
        this.awardId = awardId;
    }

    public StudentInfo getStudent() {
        return this.student;
    }

    public void setStudent(StudentInfo student) {
        if (student == null) {
        } else {
            this.studentId = student.getEid();
            this.student = student;
        }
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
