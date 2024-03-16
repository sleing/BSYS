package com.mt.ams.dto.appraisingEntity;


import com.mt.ams.entity.appraisingEntity.Appraising;

import java.util.List;

import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.ams.entity.collegeEntity.College;
import com.mt.ams.entity.appraisingTypeEntity.AppraisingType;
import com.mt.common.system.entity.User;

public class AppraisingEditDto {

    private Appraising appraising;


    //外键实体是：StudentInfo
    private List<StudentInfo> studentStudentInfos;
    //外键实体是：College
    private List<College> collegeColleges;
    //外键实体是：AppraisingType
    private List<AppraisingType> targetNameAppraisingTypes;
    //外键实体是：User
    private List<User> reviewerUsers;


    public Appraising getAppraising() {
        return this.appraising;
    }

    public void setAppraising(Appraising appraising) {
        this.appraising = appraising;
    }


    public List<StudentInfo> getStudentStudentInfos() {
        return this.studentStudentInfos;
    }

    public void setStudentStudentInfos(List<StudentInfo> studentStudentInfos) {
        this.studentStudentInfos = studentStudentInfos;
    }

    public List<College> getCollegeColleges() {
        return this.collegeColleges;
    }

    public void setCollegeColleges(List<College> collegeColleges) {
        this.collegeColleges = collegeColleges;
    }

    public List<AppraisingType> getTargetNameAppraisingTypes() {
        return this.targetNameAppraisingTypes;
    }

    public void setTargetNameAppraisingTypes(List<AppraisingType> targetNameAppraisingTypes) {
        this.targetNameAppraisingTypes = targetNameAppraisingTypes;
    }

    public List<User> getReviewerUsers() {
        return this.reviewerUsers;
    }

    public void setReviewerUsers(List<User> reviewerUsers) {
        this.reviewerUsers = reviewerUsers;
    }
}
