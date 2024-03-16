package com.mt.ams.entity.studentEntity;

import javax.persistence.*;
import java.io.Serializable;

import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import com.mt.ams.entity.collegeEntity.College;

@DEntity(label = "学生信息", comment = "", moduleLabel = "学生")
@Entity(name = "ams_student_info")
@Table(name = "ams_student_info", indexes = {@Index(name = "index_college_id", columnList = "college_id")})
@ApiModel(description = "学生信息:")
public class StudentInfo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @DColumn(index = 3, label = "学号", comment = "请输入学号", component = "文本", where = false)
    @ApiModelProperty(value = "学号:请输入学号")
    @Column(name = "student_id", length = 255, nullable = false, unique = true)
    private String studentId;

    @DColumn(index = 4, label = "学院", foreignEntity = "College", comment = "请输入学院", component = "对象选择", where = false)
    @ApiModelProperty(value = "学院:请输入学院")
    @Column(name = "college_id", length = 255, nullable = false, unique = false)
    private Long collegeId;

    @Transient
    private College college;

    @Transient
    @ApiModelProperty(value = "学院名称:请输入学院")
    @DColumn(index = 4, label = "学院", foreignEntity = "College", comment = "请输入学院")
    private String collegeName;

    @DColumn(index = 5, label = "专业", comment = "请输入专业", component = "文本", where = false)
    @ApiModelProperty(value = "专业:请输入专业")
    @Column(name = "major", length = 255, nullable = true, unique = false)
    private String major;

    @DColumn(index = 6, label = "年级", comment = "请输入年级", component = "文本", where = false)
    @ApiModelProperty(value = "年级:请输入年级")
    @Column(name = "grade", length = 255, nullable = true, unique = false)
    private String grade;

    @DColumn(index = 7, label = "班级", comment = "请输入班级", component = "文本", where = false)
    @ApiModelProperty(value = "班级:请输入班级")
    @Column(name = "class_grade", length = 255, nullable = true, unique = false)
    private String classGrade;

    @DColumn(index = 8, label = "联系电话", comment = "请输入电话", component = "文本", where = false)
    @ApiModelProperty(value = "联系电话:请输入电话")
    @Column(name = "contact_tel", length = 255, nullable = true, unique = false)
    private String contactTel;

    @DColumn(index = 9, label = "联系邮箱", comment = "请输入邮箱", component = "文本", where = false)
    @ApiModelProperty(value = "联系邮箱:请输入邮箱")
    @Column(name = "email", length = 255, nullable = false, unique = false)
    private String email;


    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Long getCollegeId() {
        return this.collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassGrade() {
        return this.classGrade;
    }

    public void setClassGrade(String classGrade) {
        this.classGrade = classGrade;
    }

    public String getContactTel() {
        return this.contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
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
