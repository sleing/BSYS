package com.mt.ams.service.appraisingTypeEntity.impl;

import com.mt.ams.dao.appraisingTypeEntity.AppraisingTypeDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.ams.entity.appraisingTypeEntity.AppraisingType;
import com.mt.ams.service.appraisingTypeEntity.AppraisingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class AppraisingTypeServiceBean extends BaseService implements AppraisingTypeService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private AppraisingTypeDao appraisingTypeDao;

    @Resource
    private RedisTemplate<String, List<AppraisingType>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据分页参数查询学生处评优评先类别信息集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findAppraisingTypes(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisingTypes(pageDTO);
        List<AppraisingType> appraisingTypeDTOS = this.appraisingTypeDao.findAppraisingTypes(pageDTO);
        Long totalCount = this.appraisingTypeDao.findAppraisingTypeTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(appraisingTypeDTOS);

        return pageResultDTO;
    }

    /**
     * 查询全部学生处评优评先类别信息集合
     */
    @Override
    public List<AppraisingType> findAllAppraisingTypes() {
        return this.appraisingTypeDao.findAllAppraisingTypes();
    }

    /**
     * 查询所有学生处评优评先类别信息集合(只提取ID 和 Name)
     */
    @Override
    public List<AppraisingType> findAllAppraisingTypesWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllAppraisingTypesWithIdName();
        return this.appraisingTypeDao.findAllAppraisingTypesWithIdName();
    }

    /**
     * 根据名称查询学生处评优评先类别信息集合(只提取ID 和 Name)
     *
     * @param appraisingTypeName 名称
     */
    @Override
    public List<AppraisingType> findAppraisingTypesWithIdNameByName(String appraisingTypeName) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisingTypesWithIdNameByName(appraisingTypeName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:AppraisingType_where_appraisingTypeName_" + appraisingTypeName);
        List<AppraisingType> appraisingTypes = new ArrayList<>();
        if (keys.isEmpty()) {
            appraisingTypes = this.appraisingTypeDao.findAppraisingTypesWithIdNameByName(appraisingTypeName);
            redisTemplate.opsForValue().set("searchData:AppraisingType_where_appraisingTypeName_" + appraisingTypeName, appraisingTypes, 30, TimeUnit.DAYS);
        } else {
            appraisingTypes = redisTemplate.opsForValue().get("searchData:AppraisingType_where_appraisingTypeName_" + appraisingTypeName);
        }
        return appraisingTypes;
    }

    /**
     * 根据ID查询指定的学生处评优评先类别信息(只提取ID 和 Name)
     *
     * @param appraisingTypeId Id
     */
    @Override
    public AppraisingType findAppraisingTypesWithIdNameById(Long appraisingTypeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisingTypesWithIdNameById(appraisingTypeId);
        return this.appraisingTypeDao.findAppraisingTypesWithIdNameById(appraisingTypeId);
    }

    /**
     * 根据ID查询指定的学生处评优评先类别信息
     *
     * @param appraisingTypeId Id
     */
    @Override
    public AppraisingType findAppraisingType(Long appraisingTypeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisingType(appraisingTypeId);
        return this.appraisingTypeDao.findAppraisingType(appraisingTypeId);
    }

    /**
     * 根据ID查询指定的学生处评优评先类别信息(包含外键)
     *
     * @param appraisingTypeId Id
     */
    @Override
    public AppraisingType findAppraisingTypeWithForeignName(Long appraisingTypeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisingTypeWithForeignName(appraisingTypeId);
        return this.appraisingTypeDao.findAppraisingTypeWithForeignName(appraisingTypeId);
    }

    /**
     * 新增学生处评优评先类别信息
     *
     * @param appraisingType 实体对象
     */
    @Override
    public AppraisingType saveAppraisingType(AppraisingType appraisingType) {
        //TODO:请在此校验参数的合法性
        this.validateSaveAppraisingType(appraisingType);
        //TODO:填充公共参数
        this.setSavePulicColumns(appraisingType);
        Long rows = this.appraisingTypeDao.saveAppraisingType(appraisingType);
        if (rows != 1) {
            String error = "新增保存学生处评优评先类别信息出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return appraisingType;
    }

    /**
     * 更新学生处评优评先类别信息
     *
     * @param appraisingType 实体对象
     */
    @Override
    public AppraisingType updateAppraisingType(AppraisingType appraisingType) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateAppraisingType(appraisingType);
        Long rows = this.appraisingTypeDao.updateAppraisingType(appraisingType);
        if (rows != 1) {
            String error = "修改保存学生处评优评先类别信息出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return appraisingType;
    }

    /**
     * 根据ID删除学生处评优评先类别信息
     *
     * @param appraisingTypeId ID
     */
    @Override
    public void deleteAppraisingType(Long appraisingTypeId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteAppraisingType(appraisingTypeId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(AppraisingType.class, appraisingTypeId);
        if (entityUsageMap != null && entityUsageMap.size() > 0) {
            StringBuilder errors = new StringBuilder();
            errors.append("计划删除的数据正在被以下数引用\n");
            for (EntityUsage entityUsage : entityUsageMap.values()) {
                errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
                for (Map.Entry<Long, String> entry : entityUsage.getUsageIdNames().entrySet()) {
                    errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
                }
            }
            errors.append("，不能删除，请检查处理后再删除");
            throw new BusinessException(errors.toString());
        }

        Long rows = this.appraisingTypeDao.deleteAppraisingType(appraisingTypeId);
        if (rows != 1) {
            String error = "删除学生处评优评先类别信息出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    //TODO:---------------验证-------------------

    private void validateFindAppraisingTypes(PageDTO pageDTO) {

    }

    private void validateFindAppraisingTypesWithIdNameByName(String appraisingTypeName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraisingType()写法
    }


    private void validateFindAllAppraisingTypesWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraisingType()写法
    }

    private void validateFindAppraisingTypesWithIdNameById(Long appraisingTypeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraisingType()写法
    }

    private void validateFindAppraisingType(Long appraisingTypeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraisingType()写法
    }

    private void validateFindAppraisingTypeWithForeignName(Long appraisingTypeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraisingType()写法
    }

    private void validateSaveAppraisingType(AppraisingType appraisingType) {
        //不为空判断
        if (appraisingType.getEid() != null || appraisingType.getCreatorId() != null || appraisingType.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraisingType()写法
    }

    private void validateUpdateAppraisingType(AppraisingType appraisingType) {
        //不为空判断
        if (appraisingType.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.appraisingTypeDao.findAppraisingTypeTotalCount(PageDTO.create(AppraisingType.FIELD_ID, appraisingType.getEid())) == 0) {
            throw new BusinessException("修改的学生处评优评先类别信息 " + appraisingType.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraisingType()写法
    }

    private void validateDeleteAppraisingType(Long appraisingTypeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraisingType()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
