package com.mt.ams.entity.awardEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.mt.ams.entity.awardeeEntity.Awardee;
import com.mt.ams.entity.collegeEntity.College;
import com.mt.ams.entity.competitionEntity.Competition;
import com.mt.ams.entity.instructorEntity.Instructor;
import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.ams.entity.teacherEntity.TeacherInfo;
import com.mt.ams.entity.unitEntity.Unit;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.system.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@DEntity(label = "获奖登记", comment = "", moduleLabel = "学生获奖登记管理")
@Entity(name = "ams_award")
@Table(name = "ams_award", indexes = {@Index(name = "index_first_awardee_id", columnList = "first_awardee_id"), @Index(name = "index_college_id", columnList = "college_id"), @Index(name = "index_competition_name_id", columnList = "competition_name_id"), @Index(name = "index_award_level", columnList = "award_level"), @Index(name = "index_award_grade", columnList = "award_grade"), @Index(name = "index_competition_category", columnList = "competition_category"), @Index(name = "index_competition_level", columnList = "competition_level"), @Index(name = "index_organization_id", columnList = "organization_id"), @Index(name = "index_first_instructor_id", columnList = "first_instructor_id"), @Index(name = "index_audit_status", columnList = "audit_status"), @Index(name = "index_reviewer_id", columnList = "reviewer_id")})
@ApiModel(description = "获奖登记:")
public class Award extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @DColumn(index = 3, label = "第一获奖人", foreignEntity = "StudentInfo", comment = "第一获奖人", component = "对象选择", where = false)
    @ApiModelProperty(value = "第一获奖人:第一获奖人")
    @Column(name = "first_awardee_id", length = 255, nullable = false, unique = false)
    private Long firstAwardeeId;

    @Transient
    private StudentInfo firstAwardee;

    @Transient
    @ApiModelProperty(value = "第一获奖人名称:第一获奖人")
    @DColumn(index = 3, label = "第一获奖人", foreignEntity = "StudentInfo", comment = "第一获奖人")
    private String firstAwardeeName;

    @DColumn(index = 4, label = "第一获奖人学院", foreignEntity = "College", comment = "第一获奖人学院", component = "对象选择", where = false)
    @ApiModelProperty(value = "第一获奖人学院:第一获奖人学院")
    @Column(name = "college_id", length = 255, nullable = true, unique = false)
    private Long collegeId;

    @Transient
    private College college;

    @Transient
    @ApiModelProperty(value = "第一获奖人学院名称:第一获奖人学院")
    @DColumn(index = 4, label = "第一获奖人学院", foreignEntity = "College", comment = "第一获奖人学院")
    private String collegeName;

    @DColumn(index = 5, label = "第一获奖人专业", comment = "第一获奖人专业", component = "文本", where = false)
    @ApiModelProperty(value = "第一获奖人专业:第一获奖人专业")
    @Column(name = "major", length = 255, nullable = true, unique = false)
    private String major;

    @DColumn(index = 6, label = "第一获奖人年级", comment = "第一获奖人年级", component = "文本", where = false)
    @ApiModelProperty(value = "第一获奖人年级:第一获奖人年级")
    @Column(name = "grade", length = 255, nullable = true, unique = false)
    private String grade;

    @DColumn(index = 7, label = "第一获奖人联系电话", comment = "第一获奖人员电话", component = "文本", where = false)
    @ApiModelProperty(value = "第一获奖人联系电话:第一获奖人员电话")
    @Column(name = "contact_tel", length = 255, nullable = true, unique = false)
    private String contactTel;

    @Transient
    private List<Awardee> awardees;

    @DColumn(index = 9, label = "比赛项目名称", foreignEntity = "Competition", comment = "比赛项目名称", component = "对象选择", where = false)
    @ApiModelProperty(value = "比赛项目名称:比赛项目名称")
    @Column(name = "competition_name_id", length = 255, nullable = false, unique = false)
    private Long competitionNameId;

    @Transient
    private Competition competitionName;

    @Transient
    @ApiModelProperty(value = "比赛项目名称名称:比赛项目名称")
    @DColumn(index = 9, label = "比赛项目名称", foreignEntity = "Competition", comment = "比赛项目名称")
    private String competitionNameName;

    @DColumn(index = 10, label = "作品名，参赛队名", comment = "作品名，参赛队名", component = "文本", where = false)
    @ApiModelProperty(value = "作品名，参赛队名:作品名，参赛队名")
    @Column(name = "work_name", length = 255, nullable = true, unique = false)
    private String workName;

    @DColumn(index = 11, label = "证书编号", comment = "证书（奖状）编号", component = "文本", where = false)
    @ApiModelProperty(value = "证书编号:证书（奖状）编号")
    @Column(name = "certificate_no", length = 255, nullable = true, unique = false)
    private String certificateNo;

    @DColumn(index = 12, label = "竞赛组别", comment = "比赛组别", component = "文本", where = false)
    @ApiModelProperty(value = "竞赛组别:比赛组别")
    @Column(name = "competition_group", length = 255, nullable = true, unique = false)
    private String competitionGroup;

    @DColumn(index = 13, label = "获奖级别", codeTable = "获奖级别", codeTableOptions = "国家级,省市级,省级,市级,校级,院级", comment = "获奖级别", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "获奖级别:获奖级别")
    @Column(name = "award_level", length = 255, nullable = true, unique = false)
    private String awardLevel;

    @DColumn(index = 14, label = "获奖等级", codeTable = "获奖等级", codeTableOptions = "特等,一等,二等,三等,金奖,银奖,铜奖,参与奖,优胜奖,其他", comment = "获奖等级", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "获奖等级:获奖等级")
    @Column(name = "award_grade", length = 255, nullable = true, unique = false)
    private String awardGrade;

    @DColumn(index = 15, label = "类别", codeTable = "竞赛类别", codeTableOptions = "政府类,学会类,未定类", comment = "未认定", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "类别:未认定")
    @Column(name = "competition_category", length = 255, nullable = true, unique = false)
    private String competitionCategory;

    @DColumn(index = 16, label = "举办单位等级", codeTable = "举办单位等级", codeTableOptions = "I,II,未定级", comment = "未认定", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "举办单位等级:未认定")
    @Column(name = "competition_level", length = 255, nullable = true, unique = false)
    private String competitionLevel;

    @DColumn(index = 17, label = "举办单位", foreignEntity = "Unit", comment = "比赛举办单位", component = "对象选择", where = false)
    @ApiModelProperty(value = "举办单位:比赛举办单位")
    @Column(name = "organization_id", length = 255, nullable = false, unique = false)
    private Long organizationId;

    @Transient
    private Unit organization;

    @Transient
    @ApiModelProperty(value = "举办单位名称:比赛举办单位")
    @DColumn(index = 17, label = "举办单位", foreignEntity = "Unit", comment = "比赛举办单位")
    private String organizationName;

    @DColumn(index = 18, label = "获奖日期", comment = "获奖日期", component = "日期选择", where = false)
    @ApiModelProperty(value = "获奖日期:获奖日期")
    @Column(name = "award_date", length = 255, nullable = false, unique = false)
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date awardDate;

    @DColumn(index = 19, label = "第一指导教师", foreignEntity = "TeacherInfo", comment = "第一指导老师", component = "对象选择", where = false)
    @ApiModelProperty(value = "第一指导教师:第一指导老师")
    @Column(name = "first_instructor_id", length = 255, nullable = true, unique = false)
    private Long firstInstructorId;

    @Transient
    private TeacherInfo firstInstructor;

    @Transient
    @ApiModelProperty(value = "第一指导教师名称:第一指导老师")
    @DColumn(index = 19, label = "第一指导教师", foreignEntity = "TeacherInfo", comment = "第一指导老师")
    private String firstInstructorName;

    @Transient
    private List<Instructor> instructors;

    @DColumn(index = 21, label = "证明文件", comment = "证明文件", component = "文本", where = false)
    @ApiModelProperty(value = "证明文件:证明文件")
    @Column(name = "evidence", length = 255, nullable = true, unique = false)
    private String evidence;

    @DColumn(index = 22, label = "所有获奖学生", comment = "所有获奖学生", component = "文本", where = false)
    @ApiModelProperty(value = "所有获奖学生:所有获奖学生")
    @Column(name = "all_students", length = 255, nullable = true, unique = false)
    private String allStudents;

    @DColumn(index = 22, label = "指导教师", comment = "指导教师", component = "文本", where = false)
    @ApiModelProperty(value = "指导教师:指导教师")
    @Column(name = "all_instructors", length = 255, nullable = true, unique = false)
    private String allInstructors;

    @DColumn(index = 23, label = "审核状态", codeTable = "审核状态", codeTableOptions = "未审核,审核通过,审核未通过", comment = "审核状态", component = "字典下拉单选", where = false)
    @ApiModelProperty(value = "审核状态:审核状态")
    @Column(name = "audit_status", length = 255, nullable = true, unique = false)
    private String auditStatus;

    @DColumn(index = 24, label = "审核人", foreignEntity = "User", comment = "审核材料", component = "对象选择", where = false)
    @ApiModelProperty(value = "审核人:审核材料")
    @Column(name = "reviewer_id", length = 255, nullable = true, unique = false)
    private Long reviewerId;

    @Transient
    private User reviewer;

    @Transient
    @ApiModelProperty(value = "审核人名称:审核材料")
    @DColumn(index = 24, label = "审核人", foreignEntity = "User", comment = "审核材料")
    private String reviewerName;


    public Long getFirstAwardeeId() {
        return this.firstAwardeeId;
    }

    public void setFirstAwardeeId(Long firstAwardeeId) {
        this.firstAwardeeId = firstAwardeeId;
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

    public String getContactTel() {
        return this.contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public List<Awardee> getAwardees() {
        return this.awardees;
    }

    public void setAwardees(List<Awardee> awardees) {
        this.awardees = awardees;
    }

    public Long getCompetitionNameId() {
        return this.competitionNameId;
    }

    public void setCompetitionNameId(Long competitionNameId) {
        this.competitionNameId = competitionNameId;
    }

    public String getWorkName() {
        return this.workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getCertificateNo() {
        return this.certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getCompetitionGroup() {
        return this.competitionGroup;
    }

    public void setCompetitionGroup(String competitionGroup) {
        this.competitionGroup = competitionGroup;
    }

    public String getAwardLevel() {
        return this.awardLevel;
    }

    public void setAwardLevel(String awardLevel) {
        this.awardLevel = awardLevel;
    }

    public String getAwardGrade() {
        return this.awardGrade;
    }

    public void setAwardGrade(String awardGrade) {
        this.awardGrade = awardGrade;
    }

    public String getCompetitionCategory() {
        return this.competitionCategory;
    }

    public void setCompetitionCategory(String competitionCategory) {
        this.competitionCategory = competitionCategory;
    }

    public String getCompetitionLevel() {
        return this.competitionLevel;
    }

    public void setCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    public Long getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Date getAwardDate() {
        return this.awardDate;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }

    public Long getFirstInstructorId() {
        return this.firstInstructorId;
    }

    public void setFirstInstructorId(Long firstInstructorId) {
        this.firstInstructorId = firstInstructorId;
    }

    public List<Instructor> getInstructors() {
        return this.instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public String getEvidence() {
        return this.evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getAllStudents() {
        return this.allStudents;
    }

    public void setAllStudents(String allStudents) {
        this.allStudents = allStudents;
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

    public StudentInfo getFirstAwardee() {
        return this.firstAwardee;
    }

    public void setFirstAwardee(StudentInfo firstAwardee) {
        if (firstAwardee == null) {
        } else {
            this.firstAwardeeId = firstAwardee.getEid();
            this.firstAwardee = firstAwardee;
        }
    }

    public String getFirstAwardeeName() {
        return this.firstAwardeeName;
    }

    public void setFirstAwardeeName(String firstAwardeeName) {
        this.firstAwardeeName = firstAwardeeName;
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

    public Competition getCompetitionName() {
        return this.competitionName;
    }

    public void setCompetitionName(Competition competitionName) {
        if (competitionName == null) {
        } else {
            this.competitionNameId = competitionName.getEid();
            this.competitionName = competitionName;
        }
    }

    public String getCompetitionNameName() {
        return this.competitionNameName;
    }

    public void setCompetitionNameName(String competitionNameName) {
        this.competitionNameName = competitionNameName;
    }

    public Unit getOrganization() {
        return this.organization;
    }

    public void setOrganization(Unit organization) {
        if (organization == null) {
        } else {
            this.organizationId = organization.getEid();
            this.organization = organization;
        }
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public TeacherInfo getFirstInstructor() {
        return this.firstInstructor;
    }

    public void setFirstInstructor(TeacherInfo firstInstructor) {
        if (firstInstructor == null) {
        } else {
            this.firstInstructorId = firstInstructor.getEid();
            this.firstInstructor = firstInstructor;
        }
    }

    public String getFirstInstructorName() {
        return this.firstInstructorName;
    }

    public void setFirstInstructorName(String firstInstructorName) {
        this.firstInstructorName = firstInstructorName;
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

    public String getAllInstructors() {
        return allInstructors;
    }

    public void setAllInstructors(String allInstructors) {
        this.allInstructors = allInstructors;
    }
}
