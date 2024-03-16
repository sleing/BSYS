package com.mt.common.system.service;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.system.entity.BusinessLog;

import java.util.List;

public interface BusinessLogService {
    /**
     * 根据分页参数查询业务日志集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findBusinessLogs(PageDTO pageDTO);

    /**
     * 查询全部业务日志集合
     */
    public List<BusinessLog> findAllBusinessLogs();

    /**
     * 根据名称查询业务日志集合(只提取ID 和 Name)
     *
     * @param businessLogName 名称
     */
    public List<BusinessLog> findBusinessLogsWithIdNameByName(String businessLogName);

    /**
     * 查询所有业务日志集合(只提取ID 和 Name)
     */
    public List<BusinessLog> findAllBusinessLogsWithIdName();

    /**
     * 根据ID查询指定的业务日志(只提取ID 和 Name)
     *
     * @param businessLogId Id
     */
    public BusinessLog findBusinessLogsWithIdNameById(Long businessLogId);

    /**
     * 根据ID查询指定的业务日志
     *
     * @param businessLogId Id
     */
    public BusinessLog findBusinessLog(Long businessLogId);

    /**
     * 根据ID查询指定的业务日志(包含外键)
     *
     * @param businessLogId Id
     */
    public BusinessLog findBusinessLogWithForeignName(Long businessLogId);

    /**
     * 新增业务日志
     *
     * @param businessLog 实体对象
     */
    public BusinessLog saveBusinessLog(BusinessLog businessLog);

    /**
     * 更新业务日志
     *
     * @param businessLog 实体对象
     */
    public BusinessLog updateBusinessLog(BusinessLog businessLog);

    /**
     * 根据ID删除业务日志
     *
     * @param businessLogId ID
     */
    public void deleteBusinessLog(Long businessLogId);


    /**
     * 异步添加
     */
    void saveAsync(BusinessLog businessLog);
}
