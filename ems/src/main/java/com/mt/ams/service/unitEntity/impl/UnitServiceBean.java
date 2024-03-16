package com.mt.ams.service.unitEntity.impl;

import com.mt.ams.dao.unitEntity.UnitDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.ams.entity.unitEntity.Unit;
import com.mt.ams.service.unitEntity.UnitService;
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
public class UnitServiceBean extends BaseService implements UnitService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private UnitDao unitDao;

    @Resource
    private RedisTemplate<String, List<Unit>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据分页参数查询举办单位信息集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findUnits(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindUnits(pageDTO);
        List<Unit> unitDTOS = this.unitDao.findUnits(pageDTO);
        Long totalCount = this.unitDao.findUnitTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(unitDTOS);

        return pageResultDTO;
    }

    /**
     * 查询全部举办单位信息集合
     */
    @Override
    public List<Unit> findAllUnits() {
        return this.unitDao.findAllUnits();
    }

    /**
     * 查询所有举办单位信息集合(只提取ID 和 Name)
     */
    @Override
    public List<Unit> findAllUnitsWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllUnitsWithIdName();
        return this.unitDao.findAllUnitsWithIdName();
    }

    /**
     * 根据名称查询举办单位信息集合(只提取ID 和 Name)
     *
     * @param unitName 名称
     */
    @Override
    public List<Unit> findUnitsWithIdNameByName(String unitName) {
        //TODO:请在此校验参数的合法性
        this.validateFindUnitsWithIdNameByName(unitName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:Unit_where_unitName_" + unitName);
        List<Unit> units = new ArrayList<>();
        if (keys.isEmpty()) {
            units = this.unitDao.findUnitsWithIdNameByName(unitName);
            redisTemplate.opsForValue().set("searchData:Unit_where_unitName_" + unitName, units, 30, TimeUnit.DAYS);
        } else {
            units = redisTemplate.opsForValue().get("searchData:Unit_where_unitName_" + unitName);
        }
        return units;
    }

    /**
     * 根据ID查询指定的举办单位信息(只提取ID 和 Name)
     *
     * @param unitId Id
     */
    @Override
    public Unit findUnitsWithIdNameById(Long unitId) {
        //TODO:请在此校验参数的合法性
        this.validateFindUnitsWithIdNameById(unitId);
        return this.unitDao.findUnitsWithIdNameById(unitId);
    }

    /**
     * 根据ID查询指定的举办单位信息
     *
     * @param unitId Id
     */
    @Override
    public Unit findUnit(Long unitId) {
        //TODO:请在此校验参数的合法性
        this.validateFindUnit(unitId);
        return this.unitDao.findUnit(unitId);
    }

    /**
     * 根据ID查询指定的举办单位信息(包含外键)
     *
     * @param unitId Id
     */
    @Override
    public Unit findUnitWithForeignName(Long unitId) {
        //TODO:请在此校验参数的合法性
        this.validateFindUnitWithForeignName(unitId);
        return this.unitDao.findUnitWithForeignName(unitId);
    }

    /**
     * 新增举办单位信息
     *
     * @param unit 实体对象
     */
    @Override
    public Unit saveUnit(Unit unit) {
        //TODO:请在此校验参数的合法性
        this.validateSaveUnit(unit);
        //TODO:填充公共参数
        this.setSavePulicColumns(unit);
        Long rows = this.unitDao.saveUnit(unit);
        if (rows != 1) {
            String error = "新增保存举办单位信息出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return unit;
    }

    /**
     * 更新举办单位信息
     *
     * @param unit 实体对象
     */
    @Override
    public Unit updateUnit(Unit unit) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateUnit(unit);
        Long rows = this.unitDao.updateUnit(unit);
        if (rows != 1) {
            String error = "修改保存举办单位信息出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return unit;
    }

    /**
     * 根据ID删除举办单位信息
     *
     * @param unitId ID
     */
    @Override
    public void deleteUnit(Long unitId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteUnit(unitId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Unit.class, unitId);
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

        Long rows = this.unitDao.deleteUnit(unitId);
        if (rows != 1) {
            String error = "删除举办单位信息出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    @Override
    public Boolean getUnitName(String name) {
        String n = name.trim();
        Integer id = this.unitDao.getUnitName(n);
        if (id != null) {
            System.out.println(2);
            return true;
        } else {
            System.out.println(1);
            return false; //不存在
        }

    }

    //TODO:---------------验证-------------------

    private void validateFindUnits(PageDTO pageDTO) {
        //TODO:请使用下面方法添加数据过滤条件
        //		pageDTO.addFilter("creatorId",this.getLoginUserId());
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateUnit()写法
    }

    private void validateFindUnitsWithIdNameByName(String unitName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateUnit()写法
    }


    private void validateFindAllUnitsWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateUnit()写法
    }

    private void validateFindUnitsWithIdNameById(Long unitId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateUnit()写法
    }

    private void validateFindUnit(Long unitId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateUnit()写法
    }

    private void validateFindUnitWithForeignName(Long unitId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateUnit()写法
    }

    private void validateSaveUnit(Unit unit) {
        //不为空判断
        if (unit.getEid() != null || unit.getCreatorId() != null || unit.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateUnit()写法
    }

    private void validateUpdateUnit(Unit unit) {
        //不为空判断
        if (unit.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.unitDao.findUnitTotalCount(PageDTO.create(Unit.FIELD_ID, unit.getEid())) == 0) {
            throw new BusinessException("修改的举办单位信息 " + unit.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateUnit()写法
    }

    private void validateDeleteUnit(Long unitId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateUnit()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
