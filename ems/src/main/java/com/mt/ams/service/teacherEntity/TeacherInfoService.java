package com.mt.ams.service.teacherEntity;

import com.mt.ams.entity.teacherEntity.TeacherInfo;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherInfoService {
    /**
     * 根据分页参数查询老师信息集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTeacherInfos(PageDTO pageDTO);

    /**
     * 查询全部老师信息集合
     */
    public List<TeacherInfo> findAllTeacherInfos();

    /**
     * 根据名称查询老师信息集合(只提取ID 和 Name)
     *
     * @param teacherInfoName 名称
     */
    public List<TeacherInfo> findTeacherInfosWithIdNameByName(String teacherInfoName);

    /**
     * 查询所有老师信息集合(只提取ID 和 Name)
     */
    public List<TeacherInfo> findAllTeacherInfosWithIdName();

    /**
     * 根据ID查询指定的老师信息(只提取ID 和 Name)
     *
     * @param teacherInfoId Id
     */
    public TeacherInfo findTeacherInfosWithIdNameById(Long teacherInfoId);

    /**
     * 根据ID查询指定的老师信息
     *
     * @param teacherInfoId Id
     */
    public TeacherInfo findTeacherInfo(Long teacherInfoId);

    /**
     * 根据ID查询指定的老师信息(包含外键)
     *
     * @param teacherInfoId Id
     */
    public TeacherInfo findTeacherInfoWithForeignName(Long teacherInfoId);

    /**
     * 新增老师信息
     *
     * @param teacherInfo 实体对象
     */
    public TeacherInfo saveTeacherInfo(TeacherInfo teacherInfo);

    /**
     * 更新老师信息
     *
     * @param teacherInfo 实体对象
     */
    public TeacherInfo updateTeacherInfo(TeacherInfo teacherInfo);

    /**
     * 根据ID删除老师信息
     *
     * @param teacherInfoId ID
     */
    public void deleteTeacherInfo(Long teacherInfoId);

    /**
     * 根据工号查询老师信息
     *
     * @param teacherInfoId Id
     */
    public List<TeacherInfo> findTeacherInfosById(String teacherInfoId);

    /**
     * 根据excel导入老师信息
     *
     * @param uploadTeacherInfos
     */
    public int uploadTeacherInfos(@RequestParam("file") MultipartFile uploadTeacherInfos);

    Boolean getTeacherEid(String teacherId);
}
