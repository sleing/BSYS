package com.mt.ams.dto.awardeeEntity;


import com.mt.ams.entity.awardeeEntity.Awardee;

import java.util.List;

import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.ams.entity.awardEntity.Award;

public class AwardeeEditDto {

    private Awardee awardee;


    //外键实体是：StudentInfo
    private List<StudentInfo> studentStudentInfos;
    //外键实体是：Award
    private List<Award> awardAwards;


    public Awardee getAwardee() {
        return this.awardee;
    }

    public void setAwardee(Awardee awardee) {
        this.awardee = awardee;
    }


    public List<StudentInfo> getStudentStudentInfos() {
        return this.studentStudentInfos;
    }

    public void setStudentStudentInfos(List<StudentInfo> studentStudentInfos) {
        this.studentStudentInfos = studentStudentInfos;
    }

    public List<Award> getAwardAwards() {
        return this.awardAwards;
    }

    public void setAwardAwards(List<Award> awardAwards) {
        this.awardAwards = awardAwards;
    }
}
