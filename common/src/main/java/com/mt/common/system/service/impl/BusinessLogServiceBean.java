package com.mt.common.system.service.impl;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.system.entity.BusinessLog;
import com.mt.common.system.mapper.BusinessLogDao;
import com.mt.common.system.service.BusinessLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class BusinessLogServiceBean implements BusinessLogService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private BusinessLogDao businessLogDao;

    @Resource
    private RedisTemplate<String, List<BusinessLog>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据分页参数查询业务日志集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findBusinessLogs(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindBusinessLogs(pageDTO);
        List<BusinessLog> businessLogDTOS = this.businessLogDao.findBusinessLogs(pageDTO);
        Long totalCount = this.businessLogDao.findBusinessLogTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(businessLogDTOS);

        return pageResultDTO;
    }

    /**
     * 查询全部业务日志集合
     */
    @Override
    public List<BusinessLog> findAllBusinessLogs() {
        return this.businessLogDao.findAllBusinessLogs();
    }

    /**
     * 查询所有业务日志集合(只提取ID 和 Name)
     */
    @Override
    public List<BusinessLog> findAllBusinessLogsWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllBusinessLogsWithIdName();
        return this.businessLogDao.findAllBusinessLogsWithIdName();
    }

    /**
     * 根据名称查询业务日志集合(只提取ID 和 Name)
     *
     * @param businessLogName 名称
     */
    @Override
    public List<BusinessLog> findBusinessLogsWithIdNameByName(String businessLogName) {
        //TODO:请在此校验参数的合法性
        this.validateFindBusinessLogsWithIdNameByName(businessLogName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:BusinessLog_where_businessLogName_" + businessLogName);
        List<BusinessLog> businessLogs = new ArrayList<>();
        if (keys.isEmpty()) {
            businessLogs = this.businessLogDao.findBusinessLogsWithIdNameByName(businessLogName);
            redisTemplate.opsForValue().set("searchData:BusinessLog_where_businessLogName_" + businessLogName, businessLogs, 30, TimeUnit.DAYS);
        } else {
            businessLogs = redisTemplate.opsForValue().get("searchData:BusinessLog_where_businessLogName_" + businessLogName);
        }
        return businessLogs;
    }

    /**
     * 根据ID查询指定的业务日志(只提取ID 和 Name)
     *
     * @param businessLogId Id
     */
    @Override
    public BusinessLog findBusinessLogsWithIdNameById(Long businessLogId) {
        //TODO:请在此校验参数的合法性
        this.validateFindBusinessLogsWithIdNameById(businessLogId);
        return this.businessLogDao.findBusinessLogsWithIdNameById(businessLogId);
    }

    /**
     * 根据ID查询指定的业务日志
     *
     * @param businessLogId Id
     */
    @Override
    public BusinessLog findBusinessLog(Long businessLogId) {
        //TODO:请在此校验参数的合法性
        this.validateFindBusinessLog(businessLogId);
        return this.businessLogDao.findBusinessLog(businessLogId);
    }

    /**
     * 根据ID查询指定的业务日志(包含外键)
     *
     * @param businessLogId Id
     */
    @Override
    public BusinessLog findBusinessLogWithForeignName(Long businessLogId) {
        //TODO:请在此校验参数的合法性
        this.validateFindBusinessLogWithForeignName(businessLogId);
        return this.businessLogDao.findBusinessLogWithForeignName(businessLogId);
    }

    /**
     * 新增业务日志
     *
     * @param businessLog 实体对象
     */
    @Override
    public BusinessLog saveBusinessLog(BusinessLog businessLog) {
        //TODO:请在此校验参数的合法性
        this.validateSaveBusinessLog(businessLog);
        //TODO:填充公共参数
        Long rows = this.businessLogDao.saveBusinessLog(businessLog);
        if (rows != 1) {
            String error = "新增保存业务日志出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return businessLog;
    }

    /**
     * 更新业务日志
     *
     * @param businessLog 实体对象
     */
    @Override
    public BusinessLog updateBusinessLog(BusinessLog businessLog) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateBusinessLog(businessLog);
        Long rows = this.businessLogDao.updateBusinessLog(businessLog);
        if (rows != 1) {
            String error = "修改保存业务日志出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return businessLog;
    }

    /**
     * 根据ID删除业务日志
     *
     * @param businessLogId ID
     */
    @Override
    public void deleteBusinessLog(Long businessLogId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteBusinessLog(businessLogId);
        Long rows = this.businessLogDao.deleteBusinessLog(businessLogId);
        if (rows != 1) {
            String error = "删除业务日志出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    @Async
    @Override
    public void saveAsync(BusinessLog businessLog) {
        System.out.println(222);
        this.businessLogDao.saveBusinessLog(businessLog);
    }

    //TODO:---------------验证-------------------

    private void validateFindBusinessLogs(PageDTO pageDTO) {
        //TODO:请使用下面方法添加数据过滤条件
        //		pageDTO.addFilter("creatorId",this.getLoginUserId());
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateBusinessLog()写法
    }

    private void validateFindBusinessLogsWithIdNameByName(String businessLogName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateBusinessLog()写法
    }


    private void validateFindAllBusinessLogsWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateBusinessLog()写法
    }

    private void validateFindBusinessLogsWithIdNameById(Long businessLogId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateBusinessLog()写法
    }

    private void validateFindBusinessLog(Long businessLogId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateBusinessLog()写法
    }

    private void validateFindBusinessLogWithForeignName(Long businessLogId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateBusinessLog()写法
    }

    private void validateSaveBusinessLog(BusinessLog businessLog) {
        //不为空判断
        if (businessLog.getEid() != null || businessLog.getCreatorId() != null || businessLog.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateBusinessLog()写法
    }

    private void validateUpdateBusinessLog(BusinessLog businessLog) {
        //不为空判断
        if (businessLog.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.businessLogDao.findBusinessLogTotalCount(PageDTO.create(BusinessLog.FIELD_ID, businessLog.getEid())) == 0) {
            throw new BusinessException("修改的业务日志 " + businessLog.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateBusinessLog()写法
    }

    private void validateDeleteBusinessLog(Long businessLogId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateBusinessLog()写法
    }
}
