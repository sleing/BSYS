package com.mt.tms.service.tsteacherEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsteacherEntity.TsTeacherInfo;

import java.util.List;

public interface TsTeacherInfoService {
    /**
     * 根据分页参数查询老师信息管理集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTsTeacherInfos(PageDTO pageDTO);

    /**
     * 查询全部老师信息管理集合
     *
     */
    public List<TsTeacherInfo> findAllTsTeacherInfos();

    /**
     * 根据名称查询老师信息管理集合(只提取ID 和 Name)
     *
     * @param tsTeacherInfoName 名称
     */
    public List<TsTeacherInfo> findTsTeacherInfosWithIdNameByName(String tsTeacherInfoName);

    /**
     * 查询所有老师信息管理集合(只提取ID 和 Name)
     *
     */
    public List<TsTeacherInfo> findAllTsTeacherInfosWithIdName();

    /**
     * 根据ID查询指定的老师信息管理(只提取ID 和 Name)
     *
     * @param tsTeacherInfoId Id
     */
    public TsTeacherInfo findTsTeacherInfosWithIdNameById(Long tsTeacherInfoId);

    /**
     * 根据ID查询指定的老师信息管理
     *
     * @param tsTeacherInfoId Id
     */
    public TsTeacherInfo findTsTeacherInfo(Long tsTeacherInfoId);

    /**
     * 根据ID查询指定的老师信息管理(包含外键)
     *
     * @param tsTeacherInfoId Id
     */
    public TsTeacherInfo findTsTeacherInfoWithForeignName(Long tsTeacherInfoId);

    /**
     * 新增老师信息管理
     *
     * @param tsTeacherInfo 实体对象
     */
    public TsTeacherInfo saveTsTeacherInfo(TsTeacherInfo tsTeacherInfo);

    /**
     * 更新老师信息管理
     *
     * @param tsTeacherInfo 实体对象
     */
    public TsTeacherInfo updateTsTeacherInfo(TsTeacherInfo tsTeacherInfo);

    /**
     * 根据ID删除老师信息管理
     *
     * @param tsTeacherInfoId ID
     */
    public void deleteTsTeacherInfo(Long tsTeacherInfoId);
}
