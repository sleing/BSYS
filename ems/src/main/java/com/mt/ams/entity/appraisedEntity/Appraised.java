package com.mt.ams.entity.appraisedEntity;

import javax.persistence.*;
import java.io.Serializable;

import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.ams.entity.appraisingEntity.Appraising;

@DEntity(label = "学生处评优评先关联", comment = "", moduleLabel = "学生处评优评先关联表")
@Entity(name = "ams_appraised")
@Table(name = "ams_appraised", indexes = {@Index(name = "index_student_id", columnList = "student_id"), @Index(name = "index_appraising_id", columnList = "appraising_id")})
@ApiModel(description = "学生处评优评先关联:")
public class Appraised extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @DColumn(index = 3, label = "序号", comment = "序号", component = "数字，整数", where = false)
    @ApiModelProperty(value = "序号:序号")
    @Column(name = "display_index", length = 255, nullable = false, unique = false)
    private Integer displayIndex;

    @DColumn(index = 4, label = "学生", foreignEntity = "StudentInfo", comment = "学生名称", component = "对象选择", where = false)
    @ApiModelProperty(value = "学生:学生名称")
    @Column(name = "student_id", length = 255, nullable = false, unique = false)
    private Long studentId;

    @Transient
    private StudentInfo student;

    @Transient
    @ApiModelProperty(value = "学生名称:学生名称")
    @DColumn(index = 4, label = "学生", foreignEntity = "StudentInfo", comment = "学生名称")
    private String studentName;

    @DColumn(index = 5, label = "评优信息", foreignEntity = "Appraising", comment = "评优信息", component = "对象选择", where = false)
    @ApiModelProperty(value = "评优信息:评优信息")
    @Column(name = "appraising_id", length = 255, nullable = false, unique = false)
    private Long appraisingId;

    @Transient
    private Appraising appraising;

    @Transient
    @ApiModelProperty(value = "评优信息名称:评优信息")
    @DColumn(index = 5, label = "评优信息", foreignEntity = "Appraising", comment = "评优信息")
    private String appraisingName;


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

    public Long getAppraisingId() {
        return this.appraisingId;
    }

    public void setAppraisingId(Long appraisingId) {
        this.appraisingId = appraisingId;
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

    public Appraising getAppraising() {
        return this.appraising;
    }

    public void setAppraising(Appraising appraising) {
        if (appraising == null) {
        } else {
            this.appraisingId = appraising.getEid();
            this.appraising = appraising;
        }
    }

    public String getAppraisingName() {
        return this.appraisingName;
    }

    public void setAppraisingName(String appraisingName) {
        this.appraisingName = appraisingName;
    }


}
