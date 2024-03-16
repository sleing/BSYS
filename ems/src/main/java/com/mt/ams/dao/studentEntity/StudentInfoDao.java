package com.mt.ams.dao.studentEntity;

import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.common.core.web.base.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "studentInfoDao")
public interface StudentInfoDao {

    /**
     * 根据分页参数查询学生信息集合
     *
     * @param pageDTO 分页条件
     */
    public List<StudentInfo> findStudentInfos(PageDTO pageDTO);

    /**
     * 查询全部学生信息集合
     */
    public List<StudentInfo> findAllStudentInfos();

    /**
     * 查询所有学生信息集合(只提取ID 和 Name)
     */
    public List<StudentInfo> findAllStudentInfosWithIdName();

    /**
     * 根据名称查询学生信息集合(只提取ID 和 Name)
     *
     * @param studentInfoName 名称
     */
    public List<StudentInfo> findStudentInfosWithIdNameByName(@Param("studentInfoName") String studentInfoName);

    /**
     * 根据ID查询指定的学生信息(只提取ID 和 Name)
     *
     * @param studentInfoId Id
     */
    public StudentInfo findStudentInfosWithIdNameById(@Param(" studentInfoId") Long studentInfoId);

    /**
     * 根据分页参数查询学生信息集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findStudentInfoTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的学生信息
     *
     * @param studentInfoId Id
     */
    public StudentInfo findStudentInfo(@Param("studentInfoId") Long studentInfoId);

    /**
     * 根据ID查询指定的学生信息(包含外键)
     *
     * @param studentInfoId Id
     */
    public StudentInfo findStudentInfoWithForeignName(@Param("studentInfoId") Long studentInfoId);

    /**
     * 新增学生信息
     *
     * @param studentInfo 实体对象
     */
    public Long saveStudentInfo(StudentInfo studentInfo);

    /**
     * 更新学生信息
     *
     * @param studentInfo 实体对象
     */
    public Long updateStudentInfo(StudentInfo studentInfo);

    /**
     * 根据ID删除学生信息
     *
     * @param studentInfoId ID
     */
    public Long deleteStudentInfo(@Param("studentInfoId") Long studentInfoId);

    /**
     * 根据学号查询学生信息
     *
     * @param studentInfoId studentId
     */
    public List<StudentInfo> findStudentInfoNumById(@Param("studentInfoId") String studentInfoId);

    String findStudentInfoNumByUniqueId(Long studentId);

    public Long getStudentEid(@Param("studentId") String studentId);

    public Long getStudentEidByEmail(@Param("email") String email);

    public void updateStudentInfoMy(@Param("studentId") String studentId, @Param("name") String name, @Param("major") String major,
                                    @Param("collegeId") String collegeId, @Param("grade") String grade, @Param("classGrade") String classGrade,
                                    @Param("contactTel") String contactTel, @Param("email") String email,@Param("remark") String remark);


    void saveStudentInfoMy(@Param("studentId") String studentId, @Param("name") String name, @Param("major") String major,
                           @Param("collegeId") String collegeId, @Param("grade") String grade, @Param("classGrade") String classGrade,
                           @Param("contactTel") String contactTel, @Param("email") String email,@Param("remark") String remark);
}
