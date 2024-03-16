package com.mt.ams.dto.teacherEntity;


import com.mt.ams.entity.teacherEntity.TeacherInfo;

import java.util.List;

import com.mt.ams.entity.collegeEntity.College;

public class TeacherInfoEditDto {

    private TeacherInfo teacherInfo;


    //外键实体是：College
    private List<College> collegeColleges;


    public TeacherInfo getTeacherInfo() {
        return this.teacherInfo;
    }

    public void setTeacherInfo(TeacherInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }


    public List<College> getCollegeColleges() {
        return this.collegeColleges;
    }

    public void setCollegeColleges(List<College> collegeColleges) {
        this.collegeColleges = collegeColleges;
    }
}
