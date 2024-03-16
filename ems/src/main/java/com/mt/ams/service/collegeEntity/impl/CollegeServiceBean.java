package com.mt.ams.service.collegeEntity.impl;

import com.mt.ams.dao.collegeEntity.CollegeDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.ams.entity.collegeEntity.College;
import com.mt.ams.service.collegeEntity.CollegeService;
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
public class CollegeServiceBean extends BaseService implements CollegeService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private CollegeDao collegeDao;

    @Resource
    private RedisTemplate<String, List<College>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据分页参数查询学院信息集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findColleges(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindColleges(pageDTO);
        List<College> collegeDTOS = this.collegeDao.findColleges(pageDTO);
        Long totalCount = this.collegeDao.findCollegeTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(collegeDTOS);

        return pageResultDTO;
    }

    /**
     * 查询全部学院信息集合
     */
    @Override
    public List<College> findAllColleges() {
        return this.collegeDao.findAllColleges();
    }

    /**
     * 查询所有学院信息集合(只提取ID 和 Name)
     */
    @Override
    public List<College> findAllCollegesWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllCollegesWithIdName();
        return this.collegeDao.findAllCollegesWithIdName();
    }

    /**
     * 根据名称查询学院信息集合(只提取ID 和 Name)
     *
     * @param collegeName 名称
     */
    @Override
    public List<College> findCollegesWithIdNameByName(String collegeName) {
        //TODO:请在此校验参数的合法性
        this.validateFindCollegesWithIdNameByName(collegeName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:College_where_collegeName_" + collegeName);
        List<College> colleges = new ArrayList<>();
        if (keys.isEmpty()) {
            colleges = this.collegeDao.findCollegesWithIdNameByName(collegeName);
            redisTemplate.opsForValue().set("searchData:College_where_collegeName_" + collegeName, colleges, 30, TimeUnit.DAYS);
        } else {
            colleges = redisTemplate.opsForValue().get("searchData:College_where_collegeName_" + collegeName);
        }
        return colleges;
    }

    /**
     * 根据ID查询指定的学院信息(只提取ID 和 Name)
     *
     * @param collegeId Id
     */
    @Override
    public College findCollegesWithIdNameById(Long collegeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindCollegesWithIdNameById(collegeId);
        return this.collegeDao.findCollegesWithIdNameById(collegeId);
    }

    /**
     * 根据ID查询指定的学院信息
     *
     * @param collegeId Id
     */
    @Override
    public College findCollege(Long collegeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindCollege(collegeId);
        return this.collegeDao.findCollege(collegeId);
    }

    /**
     * 根据ID查询指定的学院信息(包含外键)
     *
     * @param collegeId Id
     */
    @Override
    public College findCollegeWithForeignName(Long collegeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindCollegeWithForeignName(collegeId);
        return this.collegeDao.findCollegeWithForeignName(collegeId);
    }

    /**
     * 新增学院信息
     *
     * @param college 实体对象
     */
    @Override
    public College saveCollege(College college) {
        //TODO:请在此校验参数的合法性
        this.validateSaveCollege(college);
        //TODO:填充公共参数
        this.setSavePulicColumns(college);
        Long rows = this.collegeDao.saveCollege(college);
        if (rows != 1) {
            String error = "新增保存学院信息出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return college;
    }

    /**
     * 更新学院信息
     *
     * @param college 实体对象
     */
    @Override
    public College updateCollege(College college) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateCollege(college);
        Long rows = this.collegeDao.updateCollege(college);
        if (rows != 1) {
            String error = "修改保存学院信息出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return college;
    }

    /**
     * 根据ID删除学院信息
     *
     * @param collegeId ID
     */
    @Override
    public void deleteCollege(Long collegeId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteCollege(collegeId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(College.class, collegeId);
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

        Long rows = this.collegeDao.deleteCollege(collegeId);
        if (rows != 1) {
            String error = "删除学院信息出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    //TODO:---------------验证-------------------

    private void validateFindColleges(PageDTO pageDTO) {
        //TODO:请使用下面方法添加数据过滤条件
        //		pageDTO.addFilter("creatorId",this.getLoginUserId());
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCollege()写法
    }

    private void validateFindCollegesWithIdNameByName(String collegeName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCollege()写法
    }


    private void validateFindAllCollegesWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCollege()写法
    }

    private void validateFindCollegesWithIdNameById(Long collegeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCollege()写法
    }

    private void validateFindCollege(Long collegeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCollege()写法
    }

    private void validateFindCollegeWithForeignName(Long collegeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCollege()写法
    }

    private void validateSaveCollege(College college) {
        //不为空判断
        if (college.getEid() != null || college.getCreatorId() != null || college.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCollege()写法
    }

    private void validateUpdateCollege(College college) {
        //不为空判断
        if (college.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.collegeDao.findCollegeTotalCount(PageDTO.create(College.FIELD_ID, college.getEid())) == 0) {
            throw new BusinessException("修改的学院信息 " + college.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCollege()写法
    }

    private void validateDeleteCollege(Long collegeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCollege()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
