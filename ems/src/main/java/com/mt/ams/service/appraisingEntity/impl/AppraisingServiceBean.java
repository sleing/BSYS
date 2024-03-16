package com.mt.ams.service.appraisingEntity.impl;

import com.mt.ams.dao.appraisingEntity.AppraisingDao;
import com.mt.ams.dao.teacherEntity.TeacherInfoDao;
import com.mt.ams.entity.appraisingEntity.Appraising;
import com.mt.ams.service.appraisingEntity.AppraisingService;
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
public class AppraisingServiceBean extends BaseService implements AppraisingService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private AppraisingDao appraisingDao;

    @Autowired
    private TeacherInfoDao teacherInfoDao;

//    @Autowired
//    private StudentInfoDao studentInfoDao;


    @Resource
    private RedisTemplate<String, List<Appraising>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据分页参数查询评优评先登记集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findAppraisings(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisings(pageDTO);
        List<Appraising> appraisingDTOS = this.appraisingDao.findAppraisings(pageDTO);
        Long totalCount = this.appraisingDao.findAppraisingTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(appraisingDTOS);

        return pageResultDTO;
    }

    /**
     * 查询全部评优评先登记集合
     */
    @Override
    public List<Appraising> findAllAppraisings() {
        return this.appraisingDao.findAllAppraisings();
    }

    /**
     * 查询所有评优评先登记集合(只提取ID 和 Name)
     */
    @Override
    public List<Appraising> findAllAppraisingsWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllAppraisingsWithIdName();
        return this.appraisingDao.findAllAppraisingsWithIdName();
    }

    /**
     * 根据名称查询评优评先登记集合(只提取ID 和 Name)
     *
     * @param appraisingName 名称
     */
    @Override
    public List<Appraising> findAppraisingsWithIdNameByName(String appraisingName) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisingsWithIdNameByName(appraisingName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:Appraising_where_appraisingName_" + appraisingName);
        List<Appraising> appraisings = new ArrayList<>();
        if (keys.isEmpty()) {
            appraisings = this.appraisingDao.findAppraisingsWithIdNameByName(appraisingName);
            redisTemplate.opsForValue().set("searchData:Appraising_where_appraisingName_" + appraisingName, appraisings, 30, TimeUnit.DAYS);
        } else {
            appraisings = redisTemplate.opsForValue().get("searchData:Appraising_where_appraisingName_" + appraisingName);
        }
        return appraisings;
    }

    /**
     * 根据ID查询指定的评优评先登记(只提取ID 和 Name)
     *
     * @param appraisingId Id
     */
    @Override
    public Appraising findAppraisingsWithIdNameById(Long appraisingId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisingsWithIdNameById(appraisingId);
        return this.appraisingDao.findAppraisingsWithIdNameById(appraisingId);
    }

    /**
     * 根据ID查询指定的评优评先登记
     *
     * @param appraisingId Id
     */
    @Override
    public Appraising findAppraising(Long appraisingId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraising(appraisingId);
        return this.appraisingDao.findAppraising(appraisingId);
    }

    /**
     * 根据ID查询指定的评优评先登记(包含外键)
     *
     * @param appraisingId Id
     */
    @Override
    public Appraising findAppraisingWithForeignName(Long appraisingId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAppraisingWithForeignName(appraisingId);
        return this.appraisingDao.findAppraisingWithForeignName(appraisingId);
    }

    /**
     * 新增评优评先登记
     *
     * @param appraising 实体对象
     */
    @Override
    public Appraising saveAppraising(Appraising appraising) {
        //保存默认信息
        appraising.setAuditStatus("未审核");

        //TODO:请在此校验参数的合法性
        this.validateSaveAppraising(appraising);
        //TODO:填充公共参数
        this.setSavePulicColumns(appraising);
        Long rows = this.appraisingDao.saveAppraising(appraising);
        if (rows != 1) {
            String error = "新增保存评优评先登记出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return appraising;
    }

    /**
     * 更新评优评先登记
     *
     * @param appraising 实体对象
     */
    @Override
    public Appraising updateAppraising(Appraising appraising) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateAppraising(appraising);
        Long rows = this.appraisingDao.updateAppraising(appraising);
        if (rows != 1) {
            String error = "修改保存评优评先登记出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return appraising;
    }

    /**
     * 根据ID删除评优评先登记
     *
     * @param appraisingId ID
     */
    @Override
    public void deleteAppraising(Long appraisingId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteAppraising(appraisingId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Appraising.class, appraisingId);
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

        Long rows = this.appraisingDao.deleteAppraising(appraisingId);
        if (rows != 1) {
            String error = "删除评优评先登记出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    @Override
    public PageResultDTO myfindAppraisings(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.myvalidateFindAppraisings(pageDTO);
        List<Appraising> appraisingDTOS = this.appraisingDao.findAppraisings(pageDTO);
        Long totalCount = this.appraisingDao.findAppraisingTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(appraisingDTOS);
        return pageResultDTO;
    }

    @Override
    public void auditForCon(Long Id, String remarkContent) {
        this.appraisingDao.auditForCon(Id,remarkContent);
    }

    @Override
    public void myUpdate(Long Id){
        this.appraisingDao.myUpdate(Id);
    }

    @Override
    public void updateReviewer(Long Id,Long eid) {
        this.appraisingDao.updateReviewer(Id,eid);
    }

    //TODO:---------------验证-------------------


    private void myvalidateFindAppraisings(PageDTO pageDTO) {
        String userID = getLoginUser().getNickname();

        Long collegeId = teacherInfoDao.findTeacherById(userID).get(0).getCollegeId();

        List<String> roles = getLoginUser().getRoles();
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            String r = it.next().toString();

//            if (!r.equals("admin")) {
//                pageDTO.addFilter("creatorName", this.getUser().getNickname());
//                break;
//            }
            if (r.equals("teacher")) {
                pageDTO.addFilter("collegeId", collegeId);
                pageDTO.addFilter("auditStatus", "未审核");
                break;
            }
        }
    }

    private void validateFindAppraisings(PageDTO pageDTO) {
        List<String> roles = getLoginUser().getRoles();
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            String r = it.next().toString();
            //System.out.println(r + "-------role");
            if (!r.equals("admin")) {
                pageDTO.addFilter("creatorName", this.getUser().getNickname());
                break;
            }
        }
    }

    private void validateFindAppraisingsWithIdNameByName(String appraisingName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraising()写法
    }


    private void validateFindAllAppraisingsWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraising()写法
    }

    private void validateFindAppraisingsWithIdNameById(Long appraisingId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraising()写法
    }

    private void validateFindAppraising(Long appraisingId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraising()写法
    }

    private void validateFindAppraisingWithForeignName(Long appraisingId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraising()写法
    }

    private void validateSaveAppraising(Appraising appraising) {
        //不为空判断
        if (appraising.getEid() != null || appraising.getCreatorId() != null || appraising.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraising()写法
    }

    private void validateUpdateAppraising(Appraising appraising) {
        //不为空判断
        if (appraising.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.appraisingDao.findAppraisingTotalCount(PageDTO.create(Appraising.FIELD_ID, appraising.getEid())) == 0) {
            throw new BusinessException("修改的评优评先登记 " + appraising.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraising()写法
    }

    private void validateDeleteAppraising(Long appraisingId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAppraising()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
