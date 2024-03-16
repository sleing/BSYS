package com.mt.common.system.mapper;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.system.entity.BusinessLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "businessLogDao")
public interface BusinessLogDao {

    /**
     * 根据分页参数查询业务日志集合
     *
     * @param pageDTO 分页条件
     */
    public List<BusinessLog> findBusinessLogs(PageDTO pageDTO);

    /**
     * 查询全部业务日志集合
     */
    public List<BusinessLog> findAllBusinessLogs();

    /**
     * 查询所有业务日志集合(只提取ID 和 Name)
     */
    public List<BusinessLog> findAllBusinessLogsWithIdName();

    /**
     * 根据名称查询业务日志集合(只提取ID 和 Name)
     *
     * @param businessLogName 名称
     */
    public List<BusinessLog> findBusinessLogsWithIdNameByName(@Param("businessLogName") String businessLogName);

    /**
     * 根据ID查询指定的业务日志(只提取ID 和 Name)
     *
     * @param businessLogId Id
     */
    public BusinessLog findBusinessLogsWithIdNameById(@Param(" businessLogId") Long businessLogId);

    /**
     * 根据分页参数查询业务日志集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findBusinessLogTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的业务日志
     *
     * @param businessLogId Id
     */
    public BusinessLog findBusinessLog(@Param("businessLogId") Long businessLogId);

    /**
     * 根据ID查询指定的业务日志(包含外键)
     *
     * @param businessLogId Id
     */
    public BusinessLog findBusinessLogWithForeignName(@Param("businessLogId") Long businessLogId);

    /**
     * 新增业务日志
     *
     * @param businessLog 实体对象
     */
    public Long saveBusinessLog(BusinessLog businessLog);

    /**
     * 更新业务日志
     *
     * @param businessLog 实体对象
     */
    public Long updateBusinessLog(BusinessLog businessLog);

    /**
     * 根据ID删除业务日志
     *
     * @param businessLogId ID
     */
    public Long deleteBusinessLog(@Param("businessLogId") Long businessLogId);
}
