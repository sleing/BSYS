package com.mt.ams.entity.teacherEntity;

import javax.persistence.*;
import java.io.Serializable;

import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import com.mt.ams.entity.collegeEntity.College;

@DEntity(label = "老师信息", comment = "", moduleLabel = "老师")
@Entity(name = "ams_teacher_info")
@Table(name = "ams_teacher_info", indexes = {@Index(name = "index_college_id", columnList = "college_id")})
@ApiModel(description = "老师信息:")
public class TeacherInfo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @DColumn(index = 3, label = "老师工号", comment = "老师工号", component = "文本", where = false)
    @ApiModelProperty(value = "老师工号:老师工号")
    @Column(name = "teacher_id", length = 255, nullable = false, unique = true)
    private String teacherId;

    @DColumn(index = 4, label = "学院", foreignEntity = "College", comment = "老师所在学院", component = "对象选择", where = false)
    @ApiModelProperty(value = "学院:老师所在学院")
    @Column(name = "college_id", length = 255, nullable = true, unique = false)
    private Long collegeId;

    @Transient
    private College college;

    @Transient
    @ApiModelProperty(value = "学院名称:老师所在学院")
    @DColumn(index = 4, label = "学院", foreignEntity = "College", comment = "老师所在学院")
    private String collegeName;

    @DColumn(index = 5, label = "邮箱", comment = "老师邮箱", component = "文本", where = false)
    @ApiModelProperty(value = "邮箱:老师邮箱")
    @Column(name = "email", length = 255, nullable = true, unique = false)
    private String email;


    public String getTeacherId() {
        return this.teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Long getCollegeId() {
        return this.collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public College getCollege() {
        return this.college;
    }

    public void setCollege(College college) {
        if (college == null) {
        } else {
            this.collegeId = college.getEid();
            this.college = college;
        }
    }

    public String getCollegeName() {
        return this.collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }


}
