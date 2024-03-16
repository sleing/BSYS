package com.mt.ams.entity.appraisingEntity;

import javax.persistence.*;
import java.io.Serializable;

import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.Date;

import com.mt.ams.entity.appraisingTypeEntity.AppraisingType;
import com.mt.common.system.entity.User;
import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.ams.entity.collegeEntity.College;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@DEntity(label = "评优评先登记", comment = "", moduleLabel = "学生处评优评先管理")
@Entity(name = "ams_appraising")
@Table(name = "ams_appraising", indexes = {@Index(name = "index_student_id", columnList = "student_id"), @Index(name = "index_sex", columnList = "sex"), @Index(name = "index_college_id", columnList = "college_id"), @Index(name = "index_school_punishment", columnList = "school_punishment"), @Index(name = "index_punishment", columnList = "punishment"), @Index(name = "index_is_civilized_dormitory", columnList = "is_civilized_dormitory"), @Index(name = "index_target_name_id", columnList = "target_name_id"), @Index(name = "index_audit_status", columnList = "audit_status"), @Index(name = "index_reviewer_id", columnList = "reviewer_id")})
@ApiModel(description = "评优评先登记:")
public class Appraising extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @DColumn(index = 3, label = "申报人", foreignEntity = "StudentInfo", comment = "姓名", component = "对象选择", where = false)
    @ApiModelProperty(value = "申报人:姓名")
    @Column(name = "student_id", length = 255, nullable = false, unique = false)
    private Long studentId;

    @Transient
    private StudentInfo student;

    @Transient
    @ApiModelProperty(value = "申报人名称:姓名")
    @DColumn(index = 3, label = "申报人", foreignEntity = "StudentInfo", comment = "姓名")
    private String studentName;

    @DColumn(index = 4, label = "性别", codeTable = "性别", codeTableOptions = "男,女", comment = "性别", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "性别:性别")
    @Column(name = "sex", length = 255, nullable = false, unique = false)
    private String sex;

    @DColumn(index = 5, label = "申报人学院", foreignEntity = "College", comment = "学院", component = "对象选择", where = false)
    @ApiModelProperty(value = "申报人学院:学院")
    @Column(name = "college_id", length = 255, nullable = false, unique = false)
    private Long collegeId;

    @Transient
    private College college;

    @Transient
    @ApiModelProperty(value = "申报人学院名称:学院")
    @DColumn(index = 5, label = "申报人学院", foreignEntity = "College", comment = "学院")
    private String collegeName;

    @DColumn(index = 6, label = "申报人专业", comment = "专业", component = "文本", where = false)
    @ApiModelProperty(value = "申报人专业:专业")
    @Column(name = "major", length = 255, nullable = false, unique = false)
    private String major;

    @DColumn(index = 7, label = "申报人年级", comment = "年级", component = "文本", where = false)
    @ApiModelProperty(value = "申报人年级:年级")
    @Column(name = "grade", length = 255, nullable = false, unique = false)
    private String grade;

    @DColumn(index = 8, label = "辅导员", comment = "辅导员姓名", component = "文本", where = false)
    @ApiModelProperty(value = "辅导员:辅导员姓名")
    @Column(name = "instructor", length = 255, nullable = false, unique = false)
    private String instructor;

    @DColumn(index = 9, label = "民族", comment = "民族", component = "文本", where = false)
    @ApiModelProperty(value = "民族:民族")
    @Column(name = "nation", length = 255, nullable = false, unique = false)
    private String nation;

    @DColumn(index = 10, label = "政治面貌", comment = "政治面貌", component = "文本", where = false)
    @ApiModelProperty(value = "政治面貌:政治面貌")
    @Column(name = "political", length = 255, nullable = false, unique = false)
    private String political;

    @DColumn(index = 11, label = "担任的职务", comment = "职务", component = "文本", where = false)
    @ApiModelProperty(value = "担任的职务:职务")
    @Column(name = "job", length = 255, nullable = true, unique = false)
    private String job;

    @DColumn(index = 12, label = "第一学期平均成绩排名", comment = "平均成绩", component = "数字，整数", where = false)
    @ApiModelProperty(value = "第一学期平均成绩排名:平均成绩")
    @Column(name = "first_average_score", length = 255, nullable = true, unique = false)
    private Integer firstAverageScore;

    @DColumn(index = 13, label = "第一学期平均成绩排名", comment = "平均成绩", component = "数字，整数", where = false)
    @ApiModelProperty(value = "第一学期平均成绩排名:平均成绩")
    @Column(name = "second_average_score", length = 255, nullable = true, unique = false)
    private Integer secondAverageScore;

    @DColumn(index = 14, label = "第一学期奖学金排名", comment = "综合成绩", component = "数字，整数", where = false)
    @ApiModelProperty(value = "第一学期奖学金排名:综合成绩")
    @Column(name = "frist_scholarship_ranking", length = 255, nullable = true, unique = false)
    private Integer fristScholarshipRanking;

    @DColumn(index = 15, label = "第二学期奖学金排名", comment = "综合成绩", component = "数字，整数", where = false)
    @ApiModelProperty(value = "第二学期奖学金排名:综合成绩")
    @Column(name = "second_scholarship_ranking", length = 255, nullable = true, unique = false)
    private Integer secondScholarshipRanking;

    @DColumn(index = 16, label = "英语成绩", comment = "英语成绩", component = "数字，小数", where = false)
    @ApiModelProperty(value = "英语成绩:英语成绩")
    @Column(name = "english_score", length = 255, nullable = true, unique = false)
    private Double englishScore;

    @DColumn(index = 17, label = "计算机成绩", comment = "计算机成绩", component = "数字，小数", where = false)
    @ApiModelProperty(value = "计算机成绩:计算机成绩")
    @Column(name = "computer_score", length = 255, nullable = true, unique = false)
    private Double computerScore;

    @DColumn(index = 18, label = "体育成绩", comment = "第一学期体育成绩", component = "数字，小数", where = false)
    @ApiModelProperty(value = "体育成绩:第一学期体育成绩")
    @Column(name = "first_sports_score", length = 255, nullable = true, unique = false)
    private Double firstSportsScore;

    @DColumn(index = 19, label = "体育成绩", comment = "第二学期体育成绩", component = "数字，小数", where = false)
    @ApiModelProperty(value = "体育成绩:第二学期体育成绩")
    @Column(name = "second_sports_score", length = 255, nullable = true, unique = false)
    private Double secondSportsScore;

    @DColumn(index = 20, label = "有无校级以上处分", codeTable = "有无", codeTableOptions = "有,无", comment = "有无校级以上处分", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "有无校级以上处分:有无校级以上处分")
    @Column(name = "school_punishment", length = 255, nullable = true, unique = false)
    private String schoolPunishment;

    @DColumn(index = 21, label = "有无违纪处分", codeTable = "有无", codeTableOptions = "有,无", comment = "有无违纪处分", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "有无违纪处分:有无违纪处分")
    @Column(name = "punishment", length = 255, nullable = true, unique = false)
    private String punishment;

    @DColumn(index = 22, label = "不及格科目数(全学年)", comment = "不及格科目数", component = "数字，整数", where = false)
    @ApiModelProperty(value = "不及格科目数(全学年):不及格科目数")
    @Column(name = "failed_subjects", length = 255, nullable = true, unique = false)
    private Integer failedSubjects;

    @DColumn(index = 23, label = "是否曾是文明寝室成员", codeTable = "是否", codeTableOptions = "是,否", comment = "是否曾是文明寝室成员", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "是否曾是文明寝室成员:是否曾是文明寝室成员")
    @Column(name = "is_civilized_dormitory", length = 255, nullable = true, unique = false)
    private String isCivilizedDormitory;

    @DColumn(index = 24, label = "获奖情况", comment = "获奖情况", component = "文本", where = false)
    @ApiModelProperty(value = "获奖情况:获奖情况")
    @Column(name = "award", length = 255, nullable = true, unique = false)
    private String award;

    @DColumn(index = 25, label = "主要突出事迹", comment = "主要突出事迹", component = "文本", where = false)
    @ApiModelProperty(value = "主要突出事迹:主要突出事迹")
    @Column(name = "deeds", length = 255, nullable = true, unique = false)
    private String deeds;

    @DColumn(index = 26, label = "学院负责人", comment = "学院负责人", component = "文本", where = false)
    @ApiModelProperty(value = "学院负责人:学院负责人")
    @Column(name = "college_opinion", length = 255, nullable = true, unique = false)
    private String collegeOpinion;

    @DColumn(index = 27, label = "学校负责人", comment = "学校负责人", component = "文本", where = false)
    @ApiModelProperty(value = "学校负责人:学校负责人")
    @Column(name = "school_opinion", length = 255, nullable = true, unique = false)
    private String schoolOpinion;

    @DColumn(index = 28, label = "证明材料", comment = "证明材料", component = "文本", where = false)
    @ApiModelProperty(value = "证明材料:证明材料")
    @Column(name = "prove", length = 255, nullable = true, unique = false)
    private String prove;

    @DColumn(index = 29, label = "分数1", comment = "分数1", component = "文本", where = false)
    @ApiModelProperty(value = "分数1:分数1")
    @Column(name = "mark_1", length = 255, nullable = true, unique = false)
    private String mark1;

    @DColumn(index = 30, label = "分数2", comment = "分数2", component = "文本", where = false)
    @ApiModelProperty(value = "分数2:分数2")
    @Column(name = "mark_2", length = 255, nullable = true, unique = false)
    private String mark2;

    @DColumn(index = 31, label = "评选排名", comment = "排名", component = "数字，整数", where = false)
    @ApiModelProperty(value = "评选排名:排名")
    @Column(name = "ranking", length = 255, nullable = true, unique = false)
    private Integer ranking;

    @DColumn(index = 32, label = "日期", comment = "填报日期", component = "日期选择", where = false)
    @ApiModelProperty(value = "日期:填报日期")
    @Column(name = "date", length = 255, nullable = true, unique = false)
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @DColumn(index = 33, label = "申报项目名称", foreignEntity = "AppraisingType", comment = "", component = "对象选择", where = false)
    @ApiModelProperty(value = "申报项目名称:")
    @Column(name = "target_name_id", length = 255, nullable = false, unique = false)
    private Long targetNameId;

    @Transient
    private AppraisingType targetName;

    @Transient
    @ApiModelProperty(value = "申报项目名称名称:")
    @DColumn(index = 33, label = "申报项目名称", foreignEntity = "AppraisingType", comment = "")
    private String targetNameName;

    @DColumn(index = 34, label = "审核状态", codeTable = "审核状态", codeTableOptions = "未审核,审核通过,审核未通过", comment = "审核状态", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "审核状态:审核状态")
    @Column(name = "audit_status", length = 255, nullable = true, unique = false)
    private String auditStatus;

    @DColumn(index = 35, label = "审核人", foreignEntity = "User", comment = "审核材料", component = "对象选择", where = false)
    @ApiModelProperty(value = "审核人:审核材料")
    @Column(name = "reviewer_id", length = 255, nullable = true, unique = false)
    private Long reviewerId;

    @Transient
    private User reviewer;

    @Transient
    @ApiModelProperty(value = "审核人名称:审核材料")
    @DColumn(index = 35, label = "审核人", foreignEntity = "User", comment = "审核材料")
    private String reviewerName;

    @DColumn(index = 36, label = "审核意见", comment = "审核意见", component = "文本", where = false)
    @ApiModelProperty(value = "审核意见:审核意见")
    @Column(name = "feed_back", length = 255, nullable = true, unique = false)
    private String feedBack;


    public Long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getInstructor() {
        return this.instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getNation() {
        return this.nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPolitical() {
        return this.political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getFirstAverageScore() {
        return this.firstAverageScore;
    }

    public void setFirstAverageScore(Integer firstAverageScore) {
        this.firstAverageScore = firstAverageScore;
    }

    public Integer getSecondAverageScore() {
        return this.secondAverageScore;
    }

    public void setSecondAverageScore(Integer secondAverageScore) {
        this.secondAverageScore = secondAverageScore;
    }

    public Integer getFristScholarshipRanking() {
        return this.fristScholarshipRanking;
    }

    public void setFristScholarshipRanking(Integer fristScholarshipRanking) {
        this.fristScholarshipRanking = fristScholarshipRanking;
    }

    public Integer getSecondScholarshipRanking() {
        return this.secondScholarshipRanking;
    }

    public void setSecondScholarshipRanking(Integer secondScholarshipRanking) {
        this.secondScholarshipRanking = secondScholarshipRanking;
    }

    public Double getEnglishScore() {
        return this.englishScore;
    }

    public void setEnglishScore(Double englishScore) {
        this.englishScore = englishScore;
    }

    public Double getComputerScore() {
        return this.computerScore;
    }

    public void setComputerScore(Double computerScore) {
        this.computerScore = computerScore;
    }

    public Double getFirstSportsScore() {
        return this.firstSportsScore;
    }

    public void setFirstSportsScore(Double firstSportsScore) {
        this.firstSportsScore = firstSportsScore;
    }

    public Double getSecondSportsScore() {
        return this.secondSportsScore;
    }

    public void setSecondSportsScore(Double secondSportsScore) {
        this.secondSportsScore = secondSportsScore;
    }

    public String getSchoolPunishment() {
        return this.schoolPunishment;
    }

    public void setSchoolPunishment(String schoolPunishment) {
        this.schoolPunishment = schoolPunishment;
    }

    public String getPunishment() {
        return this.punishment;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
    }

    public Integer getFailedSubjects() {
        return this.failedSubjects;
    }

    public void setFailedSubjects(Integer failedSubjects) {
        this.failedSubjects = failedSubjects;
    }

    public String getIsCivilizedDormitory() {
        return this.isCivilizedDormitory;
    }

    public void setIsCivilizedDormitory(String isCivilizedDormitory) {
        this.isCivilizedDormitory = isCivilizedDormitory;
    }

    public String getAward() {
        return this.award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getDeeds() {
        return this.deeds;
    }

    public void setDeeds(String deeds) {
        this.deeds = deeds;
    }

    public String getCollegeOpinion() {
        return this.collegeOpinion;
    }

    public void setCollegeOpinion(String collegeOpinion) {
        this.collegeOpinion = collegeOpinion;
    }

    public String getSchoolOpinion() {
        return this.schoolOpinion;
    }

    public void setSchoolOpinion(String schoolOpinion) {
        this.schoolOpinion = schoolOpinion;
    }

    public String getProve() {
        return this.prove;
    }

    public void setProve(String prove) {
        this.prove = prove;
    }

    public String getMark1() {
        return this.mark1;
    }

    public void setMark1(String mark1) {
        this.mark1 = mark1;
    }

    public String getMark2() {
        return this.mark2;
    }

    public void setMark2(String mark2) {
        this.mark2 = mark2;
    }

    public Integer getRanking() {
        return this.ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTargetNameId() {
        return this.targetNameId;
    }

    public void setTargetNameId(Long targetNameId) {
        this.targetNameId = targetNameId;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Long getReviewerId() {
        return this.reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getFeedBack() {
        return this.feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
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

    public AppraisingType getTargetName() {
        return this.targetName;
    }

    public void setTargetName(AppraisingType targetName) {
        if (targetName == null) {
        } else {
            this.targetNameId = targetName.getEid();
            this.targetName = targetName;
        }
    }

    public String getTargetNameName() {
        return this.targetNameName;
    }

    public void setTargetNameName(String targetNameName) {
        this.targetNameName = targetNameName;
    }

    public User getReviewer() {
        return this.reviewer;
    }

    public void setReviewer(User reviewer) {
        if (reviewer == null) {
        } else {
            this.reviewerId = reviewer.getEid();
            this.reviewer = reviewer;
        }
    }

    public String getReviewerName() {
        return this.reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }


}
