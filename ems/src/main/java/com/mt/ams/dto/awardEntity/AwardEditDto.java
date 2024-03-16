package com.mt.ams.dto.awardEntity;


import com.mt.ams.entity.awardEntity.Award;

import java.util.List;

import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.ams.entity.collegeEntity.College;
import com.mt.ams.entity.competitionEntity.Competition;
import com.mt.ams.entity.unitEntity.Unit;
import com.mt.ams.entity.teacherEntity.TeacherInfo;
import com.mt.common.system.entity.User;

public class AwardEditDto {

    private Award award;


    //外键实体是：StudentInfo
    private List<StudentInfo> firstAwardeeStudentInfos;
    //外键实体是：College
    private List<College> collegeColleges;
    //外键实体是：Competition
    private List<Competition> competitionNameCompetitions;
    //外键实体是：Unit
    private List<Unit> organizationUnits;
    //外键实体是：TeacherInfo
    private List<TeacherInfo> firstInstructorTeacherInfos;
    //外键实体是：User
    private List<User> reviewerUsers;


    public Award getAward() {
        return this.award;
    }

    public void setAward(Award award) {
        this.award = award;
    }


    public List<StudentInfo> getFirstAwardeeStudentInfos() {
        return this.firstAwardeeStudentInfos;
    }

    public void setFirstAwardeeStudentInfos(List<StudentInfo> firstAwardeeStudentInfos) {
        this.firstAwardeeStudentInfos = firstAwardeeStudentInfos;
    }

    public List<College> getCollegeColleges() {
        return this.collegeColleges;
    }

    public void setCollegeColleges(List<College> collegeColleges) {
        this.collegeColleges = collegeColleges;
    }

    public List<Competition> getCompetitionNameCompetitions() {
        return this.competitionNameCompetitions;
    }

    public void setCompetitionNameCompetitions(List<Competition> competitionNameCompetitions) {
        this.competitionNameCompetitions = competitionNameCompetitions;
    }

    public List<Unit> getOrganizationUnits() {
        return this.organizationUnits;
    }

    public void setOrganizationUnits(List<Unit> organizationUnits) {
        this.organizationUnits = organizationUnits;
    }

    public List<TeacherInfo> getFirstInstructorTeacherInfos() {
        return this.firstInstructorTeacherInfos;
    }

    public void setFirstInstructorTeacherInfos(List<TeacherInfo> firstInstructorTeacherInfos) {
        this.firstInstructorTeacherInfos = firstInstructorTeacherInfos;
    }

    public List<User> getReviewerUsers() {
        return this.reviewerUsers;
    }

    public void setReviewerUsers(List<User> reviewerUsers) {
        this.reviewerUsers = reviewerUsers;
    }
}
