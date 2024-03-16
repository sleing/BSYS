package com.mt.ams.dto.instructorEntity;


import com.mt.ams.entity.instructorEntity.Instructor;

import java.util.List;

import com.mt.ams.entity.teacherEntity.TeacherInfo;
import com.mt.ams.entity.awardEntity.Award;

public class InstructorEditDto {

    private Instructor instructor;


    //外键实体是：TeacherInfo
    private List<TeacherInfo> teacherTeacherInfos;
    //外键实体是：Award
    private List<Award> awardAwards;


    public Instructor getInstructor() {
        return this.instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }


    public List<TeacherInfo> getTeacherTeacherInfos() {
        return this.teacherTeacherInfos;
    }

    public void setTeacherTeacherInfos(List<TeacherInfo> teacherTeacherInfos) {
        this.teacherTeacherInfos = teacherTeacherInfos;
    }

    public List<Award> getAwardAwards() {
        return this.awardAwards;
    }

    public void setAwardAwards(List<Award> awardAwards) {
        this.awardAwards = awardAwards;
    }
}
