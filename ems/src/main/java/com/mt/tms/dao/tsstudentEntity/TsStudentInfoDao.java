package com.mt.tms.dao.tsstudentEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsstudentEntity.TsStudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "tsStudentInfoDao")
public interface TsStudentInfoDao {

    /**
    * 根据分页参数查询团学会学生信息管理集合
    *
    * @param pageDTO 分页条件
    */
    public List<TsStudentInfo> findTsStudentInfos(PageDTO pageDTO);

    /**
    * 查询全部团学会学生信息管理集合
    *
    */
    public List<TsStudentInfo> findAllTsStudentInfos();

    /**
    * 查询所有团学会学生信息管理集合(只提取ID 和 Name)
    *
    */
    public List<TsStudentInfo> findAllTsStudentInfosWithIdName();

    /**
    * 根据名称查询团学会学生信息管理集合(只提取ID 和 Name)
    *
    * @param tsStudentInfoName 名称
    */
    public List<TsStudentInfo> findTsStudentInfosWithIdNameByName(@Param("tsStudentInfoName") String tsStudentInfoName);

    /**
    * 根据ID查询指定的团学会学生信息管理(只提取ID 和 Name)
    *
    * @param tsStudentInfoId Id
    */
    public TsStudentInfo findTsStudentInfosWithIdNameById(@Param(" tsStudentInfoId") Long tsStudentInfoId);

    /**
    * 根据分页参数查询团学会学生信息管理集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTsStudentInfoTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的团学会学生信息管理
    *
    * @param tsStudentInfoId Id
    */
    public TsStudentInfo findTsStudentInfo(@Param("tsStudentInfoId") Long tsStudentInfoId);

    /**
    * 根据ID查询指定的团学会学生信息管理(包含外键)
    *
    * @param tsStudentInfoId Id
    */
    public TsStudentInfo findTsStudentInfoWithForeignName(@Param("tsStudentInfoId") Long tsStudentInfoId);

    /**
    * 新增团学会学生信息管理
    *
    * @param tsStudentInfo 实体对象
    */
    public Long saveTsStudentInfo(TsStudentInfo tsStudentInfo);

    /**
    * 更新团学会学生信息管理
    *
    * @param tsStudentInfo 实体对象
    */
    public Long updateTsStudentInfo(TsStudentInfo tsStudentInfo);

    /**
    * 根据ID删除团学会学生信息管理
    *
    * @param tsStudentInfoId ID
    */
    public Long deleteTsStudentInfo(@Param("tsStudentInfoId") Long tsStudentInfoId);

    /**
     * 批量导入学生信息
     * @param dataInsert
     */
    void mutiImport(List<TsStudentInfo> dataInsert);

    /**
     * 查询所有的学生邮箱
     */
    public List<String> getStudentsEmails();
}
