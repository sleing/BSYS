package com.mt.tms.service.tsstudentEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsstudentEntity.TsStudentInfo;

import java.util.List;

public interface TsStudentInfoService {
    /**
     * 根据分页参数查询团学会学生信息管理集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTsStudentInfos(PageDTO pageDTO);

    /**
     * 查询全部团学会学生信息管理集合
     *
     */
    public List<TsStudentInfo> findAllTsStudentInfos();

    /**
     * 根据名称查询团学会学生信息管理集合(只提取ID 和 Name)
     *
     * @param tsStudentInfoName 名称
     */
    public List<TsStudentInfo> findTsStudentInfosWithIdNameByName(String tsStudentInfoName);

    /**
     * 查询所有团学会学生信息管理集合(只提取ID 和 Name)
     *
     */
    public List<TsStudentInfo> findAllTsStudentInfosWithIdName();

    /**
     * 根据ID查询指定的团学会学生信息管理(只提取ID 和 Name)
     *
     * @param tsStudentInfoId Id
     */
    public TsStudentInfo findTsStudentInfosWithIdNameById(Long tsStudentInfoId);

    /**
     * 根据ID查询指定的团学会学生信息管理
     *
     * @param tsStudentInfoId Id
     */
    public TsStudentInfo findTsStudentInfo(Long tsStudentInfoId);

    /**
     * 根据ID查询指定的团学会学生信息管理(包含外键)
     *
     * @param tsStudentInfoId Id
     */
    public TsStudentInfo findTsStudentInfoWithForeignName(Long tsStudentInfoId);

    /**
     * 新增团学会学生信息管理
     *
     * @param tsStudentInfo 实体对象
     */
    public TsStudentInfo saveTsStudentInfo(TsStudentInfo tsStudentInfo);

    /**
     * 更新团学会学生信息管理
     *
     * @param tsStudentInfo 实体对象
     */
    public TsStudentInfo updateTsStudentInfo(TsStudentInfo tsStudentInfo);

    /**
     * 根据ID删除团学会学生信息管理
     *
     * @param tsStudentInfoId ID
     */
    public void deleteTsStudentInfo(Long tsStudentInfoId);

    /**
     * 学生信息批量导入
     */
    void mutiImport(String data);
}
