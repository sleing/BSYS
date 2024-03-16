package com.mt.tms.dao.tsteacherEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsteacherEntity.TsTeacherInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "tsTeacherInfoDao")
public interface TsTeacherInfoDao {

    /**
    * 根据分页参数查询老师信息管理集合
    *
    * @param pageDTO 分页条件
    */
    public List<TsTeacherInfo> findTsTeacherInfos(PageDTO pageDTO);

    /**
    * 查询全部老师信息管理集合
    *
    */
    public List<TsTeacherInfo> findAllTsTeacherInfos();

    /**
    * 查询所有老师信息管理集合(只提取ID 和 Name)
    *
    */
    public List<TsTeacherInfo> findAllTsTeacherInfosWithIdName();

    /**
    * 根据名称查询老师信息管理集合(只提取ID 和 Name)
    *
    * @param tsTeacherInfoName 名称
    */
    public List<TsTeacherInfo> findTsTeacherInfosWithIdNameByName(@Param("tsTeacherInfoName") String tsTeacherInfoName);

    /**
    * 根据ID查询指定的老师信息管理(只提取ID 和 Name)
    *
    * @param tsTeacherInfoId Id
    */
    public TsTeacherInfo findTsTeacherInfosWithIdNameById(@Param(" tsTeacherInfoId") Long tsTeacherInfoId);

    /**
    * 根据分页参数查询老师信息管理集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTsTeacherInfoTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的老师信息管理
    *
    * @param tsTeacherInfoId Id
    */
    public TsTeacherInfo findTsTeacherInfo(@Param("tsTeacherInfoId") Long tsTeacherInfoId);

    /**
    * 根据ID查询指定的老师信息管理(包含外键)
    *
    * @param tsTeacherInfoId Id
    */
    public TsTeacherInfo findTsTeacherInfoWithForeignName(@Param("tsTeacherInfoId") Long tsTeacherInfoId);

    /**
    * 新增老师信息管理
    *
    * @param tsTeacherInfo 实体对象
    */
    public Long saveTsTeacherInfo(TsTeacherInfo tsTeacherInfo);

    /**
    * 更新老师信息管理
    *
    * @param tsTeacherInfo 实体对象
    */
    public Long updateTsTeacherInfo(TsTeacherInfo tsTeacherInfo);

    /**
    * 根据ID删除老师信息管理
    *
    * @param tsTeacherInfoId ID
    */
    public Long deleteTsTeacherInfo(@Param("tsTeacherInfoId") Long tsTeacherInfoId);
}
