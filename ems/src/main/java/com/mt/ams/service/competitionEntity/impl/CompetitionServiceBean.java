package com.mt.ams.service.competitionEntity.impl;

import com.mt.ams.dao.competitionEntity.CompetitionDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.ams.entity.competitionEntity.Competition;
import com.mt.ams.service.competitionEntity.CompetitionService;
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
public class CompetitionServiceBean extends BaseService implements CompetitionService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private CompetitionDao competitionDao;

    @Resource
    private RedisTemplate<String, List<Competition>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据分页参数查询竞赛信息集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findCompetitions(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindCompetitions(pageDTO);
        List<Competition> competitionDTOS = this.competitionDao.findCompetitions(pageDTO);
        Long totalCount = this.competitionDao.findCompetitionTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(competitionDTOS);

        return pageResultDTO;
    }

    /**
     * 查询全部竞赛信息集合
     */
    @Override
    public List<Competition> findAllCompetitions() {
        return this.competitionDao.findAllCompetitions();
    }

    /**
     * 查询所有竞赛信息集合(只提取ID 和 Name)
     */
    @Override
    public List<Competition> findAllCompetitionsWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllCompetitionsWithIdName();
        return this.competitionDao.findAllCompetitionsWithIdName();
    }

    /**
     * 根据名称查询竞赛信息集合(只提取ID 和 Name)
     *
     * @param competitionName 名称
     */
    @Override
    public List<Competition> findCompetitionsWithIdNameByName(String competitionName) {
        //TODO:请在此校验参数的合法性
        this.validateFindCompetitionsWithIdNameByName(competitionName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:Competition_where_competitionName_" + competitionName);
        List<Competition> competitions = new ArrayList<>();
        if (keys.isEmpty()) {
            competitions = this.competitionDao.findCompetitionsWithIdNameByName(competitionName);
            redisTemplate.opsForValue().set("searchData:Competition_where_competitionName_" + competitionName, competitions, 30, TimeUnit.DAYS);
        } else {
            competitions = redisTemplate.opsForValue().get("searchData:Competition_where_competitionName_" + competitionName);
        }
        return competitions;
    }

    /**
     * 根据ID查询指定的竞赛信息(只提取ID 和 Name)
     *
     * @param competitionId Id
     */
    @Override
    public Competition findCompetitionsWithIdNameById(Long competitionId) {
        //TODO:请在此校验参数的合法性
        this.validateFindCompetitionsWithIdNameById(competitionId);
        return this.competitionDao.findCompetitionsWithIdNameById(competitionId);
    }

    /**
     * 根据ID查询指定的竞赛信息
     *
     * @param competitionId Id
     */
    @Override
    public Competition findCompetition(Long competitionId) {
        //TODO:请在此校验参数的合法性
        this.validateFindCompetition(competitionId);
        return this.competitionDao.findCompetition(competitionId);
    }

    /**
     * 根据ID查询指定的竞赛信息(包含外键)
     *
     * @param competitionId Id
     */
    @Override
    public Competition findCompetitionWithForeignName(Long competitionId) {
        //TODO:请在此校验参数的合法性
        this.validateFindCompetitionWithForeignName(competitionId);
        return this.competitionDao.findCompetitionWithForeignName(competitionId);
    }

    /**
     * 新增竞赛信息
     *
     * @param competition 实体对象
     */
    @Override
    public Competition saveCompetition(Competition competition) {
        //TODO:请在此校验参数的合法性
        this.validateSaveCompetition(competition);
        //TODO:填充公共参数
        this.setSavePulicColumns(competition);
        Long rows = this.competitionDao.saveCompetition(competition);
        if (rows != 1) {
            String error = "新增保存竞赛信息出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return competition;
    }

    /**
     * 更新竞赛信息
     *
     * @param competition 实体对象
     */
    @Override
    public Competition updateCompetition(Competition competition) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateCompetition(competition);
        Long rows = this.competitionDao.updateCompetition(competition);
        if (rows != 1) {
            String error = "修改保存竞赛信息出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return competition;
    }

    /**
     * 根据ID删除竞赛信息
     *
     * @param competitionId ID
     */
    @Override
    public void deleteCompetition(Long competitionId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteCompetition(competitionId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Competition.class, competitionId);
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

        Long rows = this.competitionDao.deleteCompetition(competitionId);
        if (rows != 1) {
            String error = "删除竞赛信息出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    @Override
    public Boolean getCompetitionEidByName(String competitionName) {
        String n = competitionName.trim();
        List<Integer> ids = this.competitionDao.getCompetitionEidByName(n);
        if(ids.size() != 0) {
            return true;
        }else
            return false;
    }

    //TODO:---------------验证-------------------

    private void validateFindCompetitions(PageDTO pageDTO) {
        //TODO:请使用下面方法添加数据过滤条件
        //		pageDTO.addFilter("creatorId",this.getLoginUserId());
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCompetition()写法
    }

    private void validateFindCompetitionsWithIdNameByName(String competitionName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCompetition()写法
    }


    private void validateFindAllCompetitionsWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCompetition()写法
    }

    private void validateFindCompetitionsWithIdNameById(Long competitionId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCompetition()写法
    }

    private void validateFindCompetition(Long competitionId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCompetition()写法
    }

    private void validateFindCompetitionWithForeignName(Long competitionId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCompetition()写法
    }

    private void validateSaveCompetition(Competition competition) {
        //不为空判断
        if (competition.getEid() != null || competition.getCreatorId() != null || competition.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCompetition()写法
    }

    private void validateUpdateCompetition(Competition competition) {
        //不为空判断
        if (competition.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.competitionDao.findCompetitionTotalCount(PageDTO.create(Competition.FIELD_ID, competition.getEid())) == 0) {
            throw new BusinessException("修改的竞赛信息 " + competition.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCompetition()写法
    }

    private void validateDeleteCompetition(Long competitionId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateCompetition()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
