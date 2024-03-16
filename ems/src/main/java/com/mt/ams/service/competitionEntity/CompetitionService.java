package com.mt.ams.service.competitionEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ams.entity.competitionEntity.Competition;

import java.util.List;

public interface CompetitionService {
    /**
     * 根据分页参数查询竞赛信息集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findCompetitions(PageDTO pageDTO);

    /**
     * 查询全部竞赛信息集合
     */
    public List<Competition> findAllCompetitions();

    /**
     * 根据名称查询竞赛信息集合(只提取ID 和 Name)
     *
     * @param competitionName 名称
     */
    public List<Competition> findCompetitionsWithIdNameByName(String competitionName);

    /**
     * 查询所有竞赛信息集合(只提取ID 和 Name)
     */
    public List<Competition> findAllCompetitionsWithIdName();

    /**
     * 根据ID查询指定的竞赛信息(只提取ID 和 Name)
     *
     * @param competitionId Id
     */
    public Competition findCompetitionsWithIdNameById(Long competitionId);

    /**
     * 根据ID查询指定的竞赛信息
     *
     * @param competitionId Id
     */
    public Competition findCompetition(Long competitionId);

    /**
     * 根据ID查询指定的竞赛信息(包含外键)
     *
     * @param competitionId Id
     */
    public Competition findCompetitionWithForeignName(Long competitionId);

    /**
     * 新增竞赛信息
     *
     * @param competition 实体对象
     */
    public Competition saveCompetition(Competition competition);

    /**
     * 更新竞赛信息
     *
     * @param competition 实体对象
     */
    public Competition updateCompetition(Competition competition);

    /**
     * 根据ID删除竞赛信息
     *
     * @param competitionId ID
     */
    public void deleteCompetition(Long competitionId);

    Boolean getCompetitionEidByName(String competitionName);
}
