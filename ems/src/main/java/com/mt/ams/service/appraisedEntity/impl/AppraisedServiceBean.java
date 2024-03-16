package com.mt.ams.service.appraisedEntity.impl;

import com.mt.ams.dao.appraisedEntity.AppraisedDao;
import com.mt.ams.dao.appraisingEntity.AppraisingDao;
import com.mt.ams.dao.appraisingTypeEntity.AppraisingTypeDao;
import com.mt.ams.dao.studentEntity.StudentInfoDao;
import com.mt.ams.dao.teacherEntity.TeacherInfoDao;
import com.mt.ams.entity.appraisedEntity.Appraised;
import com.mt.ams.service.appraisedEntity.AppraisedService;
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

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AppraisedServiceBean extends BaseService implements AppraisedService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private AppraisedDao appraisedDao;
    @Autowired
    private AppraisingDao appraisingDao;
    @Autowired
    private AppraisingTypeDao appraisingTypeDao;
    @Autowired
    private StudentInfoDao studentInfoDao;
    @Autowired
    private TeacherInfoDao teacherInfoDao;
    @Resource
    private RedisTemplate<String, List<Appraised>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据分页参数查询学生处评优评先关联集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findAppraiseds(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindAppraiseds(pageDTO);
        List<Appraised> appraisedDTOS = this.appraisedDao.findAppraiseds(pageDTO);
        Long totalCount = this.appraisedDao.findAppraisedTotalCount(pageDTO);
        List<Appraised> appraiseds = new ArrayList();


        System.out.println("Appraised totalCount-----:"+totalCount);
        for (int i = 0 ; i<appraisedDTOS.size();i++){
            //String tempName = appraisingTypeDao.findAppraisingType(appraisingDao.findAppraisingsWithIdNameById(appraisedDTOS.get(i).getAppraisingId()).getTargetNameId()).getName();
            //获取评选类别名称
            String tempName = appraisingTypeDao.findAppraisingType(appraisingDao.findAppraising(appraisedDTOS.get(i).getAppraisingId()).getTargetNameId()).getName();
            //获取审核状态
            String auditStatus = appraisingDao.findAppraising(appraisedDTOS.get(i).getAppraisingId()).getAuditStatus();
            //获取审核人账号
            Long reviewerId = appraisingDao.findAppraising(appraisedDTOS.get(i).getAppraisingId()).getReviewerId();
            //获取审核人信息
            String id = reviewerId.toString();
            String reviewerName = teacherInfoDao.findTeacherInfosById(id).get(0).getName();

            if(auditStatus.equals("审核通过")){
                appraisedDTOS.get(i).setAppraisingName(tempName);
                appraisedDTOS.get(i).setRemark(reviewerName);

                appraiseds.add(appraisedDTOS.get(i));
            }
        }

        PageResultDTO pageResultDTO = new PageResultDTO();



        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(appraiseds);

        return pageResultDTO;
    }

    /**
     * 查询全部学生处评优评先关联集合
     */
    @Override
    public List<Appraised> findAllAppraiseds() {
        return this.appraisedDao.findAllAppraiseds();
    }

    /**
     * 查询所有学生处评优评先关联集合(只提取ID 和 Name)
     */
    @Override
    public List<Appraised> findAllAppraisedsWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllAppraisedsWithIdName();
        return this.appraisedDao.findAllAppraisedsWithIdName();
    }

    /**
     * 根据名称查询学生处评优评先关联集合(只提取ID 和 Name)
     *
     * @param appraisedName 名称
     */
    @Override
    public List<Appraised> findAppraisedsWithIdNameByName(String appraisedName) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisedsWithIdNameByName(appraisedName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:Appraised_where_appraisedName_" + appraisedName);
        List<Appraised> appraiseds = new ArrayList<>();
        if (keys.isEmpty()) {
            appraiseds = this.appraisedDao.findAppraisedsWithIdNameByName(appraisedName);
            redisTemplate.opsForValue().set("searchData:Appraised_where_appraisedName_" + appraisedName, appraiseds, 30, TimeUnit.DAYS);
        } else {
            appraiseds = redisTemplate.opsForValue().get("searchData:Appraised_where_appraisedName_" + appraisedName);
        }
        return appraiseds;
    }

    /**
     * 根据ID查询指定的学生处评优评先关联(只提取ID 和 Name)
     *
     * @param appraisedId Id
     */
    @Override
    public Appraised findAppraisedsWithIdNameById(Long appraisedId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisedsWithIdNameById(appraisedId);
        return this.appraisedDao.findAppraisedsWithIdNameById(appraisedId);
    }

    /**
     * 根据ID查询指定的学生处评优评先关联
     *
     * @param appraisedId Id
     */
    @Override
    public Appraised findAppraised(Long appraisedId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraised(appraisedId);
        return this.appraisedDao.findAppraised(appraisedId);
    }

    /**
     * 根据ID查询指定的学生处评优评先关联(包含外键)
     *
     * @param appraisedId Id
     */
    @Override
    public Appraised findAppraisedWithForeignName(Long appraisedId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisedWithForeignName(appraisedId);
        Appraised appraised = new Appraised();
        appraised = this.appraisedDao.findAppraisedWithForeignName(appraisedId);
        String tempName = appraisingTypeDao.findAppraisingType(appraisingDao.findAppraising(appraised.getEid()).getEid()).getName();
        appraised.setAppraisingName(tempName);
        return appraised;
    }

    /**
     * 新增学生处评优评先关联
     *
     * @param appraised 实体对象
     */
    @Override
    public Appraised saveAppraised(Appraised appraised) {
        //TODO:请在此校验参数的合法性
        this.validateSaveAppraised(appraised);
        //TODO:填充公共参数
        this.setSavePulicColumns(appraised);

        Long rows = this.appraisedDao.saveAppraised(appraised);
        if (rows != 1) {
            String error = "新增保存学生处评优评先关联出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return appraised;
    }

    /**
     * 更新学生处评优评先关联
     *
     * @param appraised 实体对象
     */
    @Override
    public Appraised updateAppraised(Appraised appraised) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateAppraised(appraised);
        Long rows = this.appraisedDao.updateAppraised(appraised);
        if (rows != 1) {
            String error = "修改保存学生处评优评先关联出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return appraised;
    }

    /**
     * 根据ID删除学生处评优评先关联
     *
     * @param appraisedId ID
     */
    @Override
    public void deleteAppraised(Long appraisedId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteAppraised(appraisedId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Appraised.class, appraisedId);
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

        Long rows = this.appraisedDao.deleteAppraised(appraisedId);
        if (rows != 1) {
            String error = "删除学生处评优评先关联出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    //TODO:---------------验证-------------------

    private void validateFindAppraiseds(PageDTO pageDTO) {
        List<String> roles = getLoginUser().getRoles();
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            String r = it.next().toString();
            System.out.println("role-----"+r);
            if (r.equals("admin") || r.equals("teacher")) {
                return;
            }
        }
        //Long studentId = Long.valueOf(getLoginUser().getNickname());
        Long studentEid = studentInfoDao.getStudentEid(getLoginUser().getNickname());
        //String studentId = studentInfoDao.findStudentInfo()
        pageDTO.addFilter("studentId",studentEid);
    }

    private void validateFindAppraisedsWithIdNameByName(String appraisedName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraised()写法
    }


    private void validateFindAllAppraisedsWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraised()写法
    }

    private void validateFindAppraisedsWithIdNameById(Long appraisedId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraised()写法
    }

    private void validateFindAppraised(Long appraisedId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraised()写法
    }

    private void validateFindAppraisedWithForeignName(Long appraisedId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraised()写法
    }

    private void validateSaveAppraised(Appraised appraised) {
        //不为空判断
        if (appraised.getEid() != null || appraised.getCreatorId() != null || appraised.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraised()写法
    }

    private void validateUpdateAppraised(Appraised appraised) {
        //不为空判断
        if (appraised.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.appraisedDao.findAppraisedTotalCount(PageDTO.create(Appraised.FIELD_ID, appraised.getEid())) == 0) {
            throw new BusinessException("修改的学生处评优评先关联 " + appraised.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraised()写法
    }

    private void validateDeleteAppraised(Long appraisedId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraised()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
