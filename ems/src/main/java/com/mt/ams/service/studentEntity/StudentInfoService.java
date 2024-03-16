package com.mt.ams.service.studentEntity;

import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentInfoService {
    /**
     * 根据分页参数查询学生信息集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findStudentInfos(PageDTO pageDTO);

    /**
     * 查询全部学生信息集合
     */
    public List<StudentInfo> findAllStudentInfos();

    /**
     * 根据名称查询学生信息集合(只提取ID 和 Name)
     *
     * @param studentInfoName 名称
     */
    public List<StudentInfo> findStudentInfosWithIdNameByName(String studentInfoName);

    /**
     * 查询所有学生信息集合(只提取ID 和 Name)
     */
    public List<StudentInfo> findAllStudentInfosWithIdName();

    /**
     * 根据ID查询指定的学生信息(只提取ID 和 Name)
     *
     * @param studentInfoId Id
     */
    public StudentInfo findStudentInfosWithIdNameById(Long studentInfoId);

    /**
     * 根据ID查询指定的学生信息
     *
     * @param studentInfoId Id
     */
    public StudentInfo findStudentInfo(Long studentInfoId);

    /**
     * 根据ID查询指定的学生信息(包含外键)
     *
     * @param studentInfoId Id
     */
    public StudentInfo findStudentInfoWithForeignName(Long studentInfoId);

    /**
     * 新增学生信息
     *
     * @param studentInfo 实体对象
     */
    public StudentInfo saveStudentInfo(StudentInfo studentInfo);

    /**
     * 更新学生信息
     *
     * @param studentInfo 实体对象
     */
    public StudentInfo updateStudentInfo(StudentInfo studentInfo);

    /**
     * 根据ID删除学生信息
     *
     * @param studentInfoId ID
     */
    public void deleteStudentInfo(Long studentInfoId);

    /**
     * 根据学号查询学生信息
     *
     * @param studentInfoId Id
     */
    public List<StudentInfo> findStudentInfoNumById(String studentInfoId);

    public Long getStudentEid(String studentId);

    public Long getStudentEidByEmail(String email);

    public void updateStudentInfoMy(String studentId, String name, String major,
                                    String collegeId, String grade, String classGrade,
                                    String contactTel, String email, String remark);


    public String sendCheckCode(String email);

    public boolean checkCode(String code, String email);

    /**
     * 根据excel导入老师信息
     *
     * @param uploadStudentInfos
     */
    public int uploadStudentInfos(@RequestParam("file") MultipartFile uploadStudentInfos);

    void saveStudentInfoMy(String studentId, String name, String major, String collegeId, String grade, String classGrade, String contactTel, String email, String remark);
}

