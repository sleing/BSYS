package com.mt.ams.dto.appraisedEntity;


import com.mt.ams.entity.appraisedEntity.Appraised;

import java.util.List;

import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.ams.entity.appraisingEntity.Appraising;

public class AppraisedEditDto {

    private Appraised appraised;


    //外键实体是：StudentInfo
    private List<StudentInfo> studentStudentInfos;
    //外键实体是：Appraising
    private List<Appraising> appraisingAppraisings;


    public Appraised getAppraised() {
        return this.appraised;
    }

    public void setAppraised(Appraised appraised) {
        this.appraised = appraised;
    }


    public List<StudentInfo> getStudentStudentInfos() {
        return this.studentStudentInfos;
    }

    public void setStudentStudentInfos(List<StudentInfo> studentStudentInfos) {
        this.studentStudentInfos = studentStudentInfos;
    }

    public List<Appraising> getAppraisingAppraisings() {
        return this.appraisingAppraisings;
    }

    public void setAppraisingAppraisings(List<Appraising> appraisingAppraisings) {
        this.appraisingAppraisings = appraisingAppraisings;
    }
}
