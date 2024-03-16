package com.mt.ams.service.instructorEntity.impl;

import com.mt.ams.dao.instructorEntity.InstructorDao;
import com.mt.ams.entity.instructorEntity.Instructor;
import com.mt.ams.service.instructorEntity.InstructorService;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.BaseService;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class InstructorServiceBean extends BaseService implements InstructorService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private InstructorDao instructorDao;

    @Resource
    private RedisTemplate<String, List<Instructor>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据分页参数查询指导老师信息集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findInstructors(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindInstructors(pageDTO);
        List<Instructor> instructorDTOS = this.instructorDao.findInstructors(pageDTO);
        Long totalCount = this.instructorDao.findInstructorTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(instructorDTOS);

        return pageResultDTO;
    }

    /**
     * 查询全部指导老师信息集合
     */
    @Override
    public List<Instructor> findAllInstructors() {
        return this.instructorDao.findAllInstructors();
    }

    /**
     * 查询所有指导老师信息集合(只提取ID 和 Name)
     */
    @Override
    public List<Instructor> findAllInstructorsWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllInstructorsWithIdName();
        return this.instructorDao.findAllInstructorsWithIdName();
    }

    /**
     * 根据名称查询指导老师信息集合(只提取ID 和 Name)
     *
     * @param instructorName 名称
     */
    @Override
    public List<Instructor> findInstructorsWithIdNameByName(String instructorName) {
        //TODO:请在此校验参数的合法性
        this.validateFindInstructorsWithIdNameByName(instructorName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:Instructor_where_instructorName_" + instructorName);
        List<Instructor> instructors = new ArrayList<>();
        if (keys.isEmpty()) {
            instructors = this.instructorDao.findInstructorsWithIdNameByName(instructorName);
            redisTemplate.opsForValue().set("searchData:Instructor_where_instructorName_" + instructorName, instructors, 30, TimeUnit.DAYS);
        } else {
            instructors = redisTemplate.opsForValue().get("searchData:Instructor_where_instructorName_" + instructorName);
        }
        return instructors;
    }

    /**
     * 根据ID查询指定的指导老师信息(只提取ID 和 Name)
     *
     * @param instructorId Id
     */
    @Override
    public Instructor findInstructorsWithIdNameById(Long instructorId) {
        //TODO:请在此校验参数的合法性
        this.validateFindInstructorsWithIdNameById(instructorId);
        return this.instructorDao.findInstructorsWithIdNameById(instructorId);
    }

    /**
     * 根据ID查询指定的指导老师信息
     *
     * @param instructorId Id
     */
    @Override
    public Instructor findInstructor(Long instructorId) {
        //TODO:请在此校验参数的合法性
        this.validateFindInstructor(instructorId);
        return this.instructorDao.findInstructor(instructorId);
    }

    /**
     * 根据ID查询指定的指导老师信息(包含外键)
     *
     * @param instructorId Id
     */
    @Override
    public Instructor findInstructorWithForeignName(Long instructorId) {
        //TODO:请在此校验参数的合法性
        this.validateFindInstructorWithForeignName(instructorId);
        return this.instructorDao.findInstructorWithForeignName(instructorId);
    }

    /**
     * 新增指导老师信息
     *
     * @param instructor 实体对象
     */
    @Override
    public Instructor saveInstructor(Instructor instructor) {
        //TODO:请在此校验参数的合法性
        this.validateSaveInstructor(instructor);
        //TODO:填充公共参数
        this.setSavePulicColumns(instructor);
        Long rows = this.instructorDao.saveInstructor(instructor);
        if (rows != 1) {
            String error = "新增保存指导老师信息出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return instructor;
    }

    /**
     * 更新指导老师信息
     *
     * @param instructor 实体对象
     */
    @Override
    public Instructor updateInstructor(Instructor instructor) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateInstructor(instructor);
        Long rows = this.instructorDao.updateInstructor(instructor);
        if (rows != 1) {
            String error = "修改保存指导老师信息出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return instructor;
    }

    /**
     * 根据ID删除指导老师信息
     *
     * @param instructorId ID
     */
    @Override
    public void deleteInstructor(Long instructorId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteInstructor(instructorId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Instructor.class, instructorId);
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

        Long rows = this.instructorDao.deleteInstructor(instructorId);
        if (rows != 1) {
            String error = "删除指导老师信息出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    /**
     * 根据ID删除指导老师信息
     *
     * @param awardId ID
     */
    @Override
    public void deleteInstructorByAwardId(@RequestParam Long awardId) {
        Long rows = this.instructorDao.deleteInstructorByAwardId(awardId);
    }

    /**
     * 根据AwardID查询指导老师信息
     *
     * @param awardId ID
     */
    @Override
    public List<Instructor> findInstructorByAwardId(@RequestParam Long awardId) {
        return this.instructorDao.findInstructorByAwardId(awardId);
    }

    //TODO:---------------验证-------------------

    private void validateFindInstructors(PageDTO pageDTO) {
        //TODO:请使用下面方法添加数据过滤条件
        //		pageDTO.addFilter("creatorId",this.getLoginUserId());
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateInstructor()写法
    }

    private void validateFindInstructorsWithIdNameByName(String instructorName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateInstructor()写法
    }


    private void validateFindAllInstructorsWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateInstructor()写法
    }

    private void validateFindInstructorsWithIdNameById(Long instructorId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateInstructor()写法
    }

    private void validateFindInstructor(Long instructorId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateInstructor()写法
    }

    private void validateFindInstructorWithForeignName(Long instructorId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateInstructor()写法
    }

    private void validateSaveInstructor(Instructor instructor) {
        //不为空判断
        if (instructor.getEid() != null || instructor.getCreatorId() != null || instructor.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateInstructor()写法
    }

    private void validateUpdateInstructor(Instructor instructor) {
        //不为空判断
        if (instructor.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.instructorDao.findInstructorTotalCount(PageDTO.create(Instructor.FIELD_ID, instructor.getEid())) == 0) {
            throw new BusinessException("修改的指导老师信息 " + instructor.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateInstructor()写法
    }

    private void validateDeleteInstructor(Long instructorId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateInstructor()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
