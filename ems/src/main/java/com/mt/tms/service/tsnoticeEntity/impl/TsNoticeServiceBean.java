package com.mt.tms.service.tsnoticeEntity.impl;

import com.mt.tms.dao.tsnoticeEntity.TsNoticeDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import com.mt.tms.dao.tsstudentEntity.TsStudentInfoDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.tms.entity.tsnoticeEntity.TsNotice;
import com.mt.tms.service.tsnoticeEntity.TsNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
public class TsNoticeServiceBean extends BaseService implements TsNoticeService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private TsNoticeDao tsNoticeDao;

    @Resource
    private RedisTemplate<String, List<TsNotice>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TsStudentInfoDao tsStudentInfoDao;

    @Value("${spring.mail.username}")
    private String account;
    private final JavaMailSender javaMailSender;

    public TsNoticeServiceBean(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 根据分页参数查询通知管理集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findTsNotices(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindTsNotices(pageDTO);
        List<TsNotice> tsNoticeDTOS = this.tsNoticeDao.findTsNotices(pageDTO);
        Long totalCount = this.tsNoticeDao.findTsNoticeTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(tsNoticeDTOS);

        return pageResultDTO;
    }

    @Override
    public PageResultDTO findTsNoticesWithoutAudition(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindTsNoticesWithoutAudition(pageDTO);
        List<TsNotice> tsNoticeDTOS = this.tsNoticeDao.findTsNotices(pageDTO);
        Long totalCount = this.tsNoticeDao.findTsNoticeTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(tsNoticeDTOS);

        return pageResultDTO;
    }


    /**
     * 审核通过
     *
     * @param eid
     * @return
     */
    @Override
    public void receiveEvent(Long eid, String remark) {
        this.tsNoticeDao.receiveEvent(eid, remark);
    }

    /**
     * 驳回
     *
     * @param eid
     * @return
     */
    @Override
    public void rejectEvent(Long eid, String remark) {
        this.tsNoticeDao.rejectEvent(eid, remark);
    }

    /**
     * 邮箱通知
     *
     * @param subject
     * @param body
     */
    @Override
    public void sendEmail(String subject, String body) {
        List<String> userAddresses = new ArrayList<String>();
        userAddresses = this.tsStudentInfoDao.getStudentsEmails();
        String address = userAddresses.toString();
        address = address.substring(1, address.length() - 1);
        System.out.println(address);
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(account);
        //设置多个收件人
        smm.setTo(address.split(","));
        smm.setSubject(subject);
        smm.setText(body);
        javaMailSender.send(smm);
    }

    /**
     * 查询全部通知管理集合
     */
    @Override
    public List<TsNotice> findAllTsNotices() {
        return this.tsNoticeDao.findAllTsNotices();
    }

    /**
     * 查询所有通知管理集合(只提取ID 和 Name)
     */
    @Override
    public List<TsNotice> findAllTsNoticesWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllTsNoticesWithIdName();
        return this.tsNoticeDao.findAllTsNoticesWithIdName();
    }

    /**
     * 根据名称查询通知管理集合(只提取ID 和 Name)
     *
     * @param tsNoticeName 名称
     */
    @Override
    public List<TsNotice> findTsNoticesWithIdNameByName(String tsNoticeName) {
        //TODO:请在此校验参数的合法性
        this.validateFindTsNoticesWithIdNameByName(tsNoticeName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:TsNotice_where_tsNoticeName_" + tsNoticeName);
        List<TsNotice> tsNotices = new ArrayList<>();
        if (keys.isEmpty()) {
            tsNotices = this.tsNoticeDao.findTsNoticesWithIdNameByName(tsNoticeName);
            redisTemplate.opsForValue().set("searchData:TsNotice_where_tsNoticeName_" + tsNoticeName, tsNotices, 30, TimeUnit.DAYS);
        } else {
            tsNotices = redisTemplate.opsForValue().get("searchData:TsNotice_where_tsNoticeName_" + tsNoticeName);
        }
        return tsNotices;
    }

    /**
     * 根据ID查询指定的通知管理(只提取ID 和 Name)
     *
     * @param tsNoticeId Id
     */
    @Override
    public TsNotice findTsNoticesWithIdNameById(Long tsNoticeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindTsNoticesWithIdNameById(tsNoticeId);
        return this.tsNoticeDao.findTsNoticesWithIdNameById(tsNoticeId);
    }

    /**
     * 根据ID查询指定的通知管理
     *
     * @param tsNoticeId Id
     */
    @Override
    public TsNotice findTsNotice(Long tsNoticeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindTsNotice(tsNoticeId);
        return this.tsNoticeDao.findTsNotice(tsNoticeId);
    }

    /**
     * 根据ID查询指定的通知管理(包含外键)
     *
     * @param tsNoticeId Id
     */
    @Override
    public TsNotice findTsNoticeWithForeignName(Long tsNoticeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindTsNoticeWithForeignName(tsNoticeId);
        return this.tsNoticeDao.findTsNoticeWithForeignName(tsNoticeId);
    }

    /**
     * 新增通知管理
     *
     * @param tsNotice 实体对象
     */
    @Override
    public TsNotice saveTsNotice(TsNotice tsNotice) {
        //TODO:请在此校验参数的合法性
        this.validateSaveTsNotice(tsNotice);
        //TODO:填充公共参数
        this.setSavePulicColumns(tsNotice);
        Long rows = this.tsNoticeDao.saveTsNotice(tsNotice);
        if (rows != 1) {
            String error = "新增保存通知管理出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return tsNotice;
    }

    /**
     * 更新通知管理
     *
     * @param tsNotice 实体对象
     */
    @Override
    public TsNotice updateTsNotice(TsNotice tsNotice) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateTsNotice(tsNotice);
        Long rows = this.tsNoticeDao.updateTsNotice(tsNotice);
        if (rows != 1) {
            String error = "修改保存通知管理出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return tsNotice;
    }

    /**
     * 根据ID删除通知管理
     *
     * @param tsNoticeId ID
     */
    @Override
    public void deleteTsNotice(Long tsNoticeId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteTsNotice(tsNoticeId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(TsNotice.class, tsNoticeId);
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

        Long rows = this.tsNoticeDao.deleteTsNotice(tsNoticeId);
        if (rows != 1) {
            String error = "删除通知管理出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }


    //TODO:---------------验证-------------------

    private void validateFindTsNotices(PageDTO pageDTO) {
        //TODO:请使用下面方法添加数据过滤条件
        //		pageDTO.addFilter("creatorId",this.getLoginUserId());
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsNotice()写法
    }

    private void validateFindTsNoticesWithoutAudition(PageDTO pageDTO) {
        pageDTO.addFilter("auditStatu", "未审核");
        //TODO:请使用下面方法添加数据过滤条件
        //		pageDTO.addFilter("creatorId",this.getLoginUserId());
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsNotice()写法
    }

    private void validateFindTsNoticesWithIdNameByName(String tsNoticeName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsNotice()写法
    }


    private void validateFindAllTsNoticesWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsNotice()写法
    }

    private void validateFindTsNoticesWithIdNameById(Long tsNoticeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsNotice()写法
    }

    private void validateFindTsNotice(Long tsNoticeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsNotice()写法
    }

    private void validateFindTsNoticeWithForeignName(Long tsNoticeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsNotice()写法
    }

    private void validateSaveTsNotice(TsNotice tsNotice) {
        //不为空判断
        if (tsNotice.getEid() != null || tsNotice.getCreatorId() != null || tsNotice.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsNotice()写法
    }

    private void validateUpdateTsNotice(TsNotice tsNotice) {
        //不为空判断
        if (tsNotice.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.tsNoticeDao.findTsNoticeTotalCount(PageDTO.create(TsNotice.FIELD_ID, tsNotice.getEid())) == 0) {
            throw new BusinessException("修改的通知管理 " + tsNotice.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsNotice()写法
    }

    private void validateDeleteTsNotice(Long tsNoticeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsNotice()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
