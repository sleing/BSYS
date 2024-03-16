package com.mt.ams.dao.teacherEntity;

import com.mt.ams.entity.teacherEntity.TeacherInfo;
import com.mt.common.core.web.base.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "teacherInfoDao")
public interface TeacherInfoDao {

    /**
     * 根据分页参数查询老师信息集合
     *
     * @param pageDTO 分页条件
     */
    public List<TeacherInfo> findTeacherInfos(PageDTO pageDTO);

    /**
     * 查询全部老师信息集合
     */
    public List<TeacherInfo> findAllTeacherInfos();

    /**
     * 查询所有老师信息集合(只提取ID 和 Name)
     */
    public List<TeacherInfo> findAllTeacherInfosWithIdName();

    /**
     * 根据名称查询老师信息集合(只提取ID 和 Name)
     *
     * @param teacherInfoName 名称
     */
    public List<TeacherInfo> findTeacherInfosWithIdNameByName(@Param("teacherInfoName") String teacherInfoName);

    /**
     * 根据ID查询指定的老师信息(只提取ID 和 Name)
     *
     * @param teacherInfoId Id
     */
    public TeacherInfo findTeacherInfosWithIdNameById(@Param(" teacherInfoId") Long teacherInfoId);

    /**
     * 根据分页参数查询老师信息集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findTeacherInfoTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的老师信息
     *
     * @param teacherInfoId Id
     */
    public TeacherInfo findTeacherInfo(@Param("teacherInfoId") Long teacherInfoId);

    /**
     * 根据ID查询指定的老师信息(包含外键)
     *
     * @param teacherInfoId Id
     */
    public TeacherInfo findTeacherInfoWithForeignName(@Param("teacherInfoId") Long teacherInfoId);

    /**
     * 新增老师信息
     *
     * @param teacherInfo 实体对象
     */
    public Long saveTeacherInfo(TeacherInfo teacherInfo);

    /**
     * 更新老师信息
     *
     * @param teacherInfo 实体对象
     */
    public Long updateTeacherInfo(TeacherInfo teacherInfo);

    /**
     * 根据ID删除老师信息
     *
     * @param teacherInfoId ID
     */
    public Long deleteTeacherInfo(@Param("teacherInfoId") Long teacherInfoId);

    /**
     * 根据工号查询老师信息
     *
     * @param teacherInfoId ID
     */
    public List<TeacherInfo> findTeacherInfosById(@Param("teacherInfoId") String teacherInfoId);


    /**
     * 根据ID查找老师
     *
     * @param teacherId 老师实体
     */
    public List<TeacherInfo> findTeacherById(String teacherId);

    Integer getTeacherEid(String teacherId);
}
