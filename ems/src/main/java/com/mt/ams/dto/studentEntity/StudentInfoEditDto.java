package com.mt.ams.dto.studentEntity;


import com.mt.ams.entity.studentEntity.StudentInfo;

import java.util.List;

import com.mt.ams.entity.collegeEntity.College;

public class StudentInfoEditDto {

    private StudentInfo studentInfo;


    //外键实体是：College
    private List<College> collegeColleges;


    public StudentInfo getStudentInfo() {
        return this.studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }


    public List<College> getCollegeColleges() {
        return this.collegeColleges;
    }

    public void setCollegeColleges(List<College> collegeColleges) {
        this.collegeColleges = collegeColleges;
    }
}
