package com.mt.ams.dao.competitionEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ams.entity.competitionEntity.Competition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@Component(value = "competitionDao")
public interface CompetitionDao {

    /**
     * 根据分页参数查询竞赛信息集合
     *
     * @param pageDTO 分页条件
     */
    public List<Competition> findCompetitions(PageDTO pageDTO);

    /**
     * 查询全部竞赛信息集合
     */
    public List<Competition> findAllCompetitions();

    /**
     * 查询所有竞赛信息集合(只提取ID 和 Name)
     */
    public List<Competition> findAllCompetitionsWithIdName();

    /**
     * 根据名称查询竞赛信息集合(只提取ID 和 Name)
     *
     * @param competitionName 名称
     */
    public List<Competition> findCompetitionsWithIdNameByName(@Param("competitionName") String competitionName);

    /**
     * 根据ID查询指定的竞赛信息(只提取ID 和 Name)
     *
     * @param competitionId Id
     */
    public Competition findCompetitionsWithIdNameById(@Param(" competitionId") Long competitionId);

    /**
     * 根据分页参数查询竞赛信息集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findCompetitionTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的竞赛信息
     *
     * @param competitionId Id
     */
    public Competition findCompetition(@Param("competitionId") Long competitionId);

    /**
     * 根据ID查询指定的竞赛信息(包含外键)
     *
     * @param competitionId Id
     */
    public Competition findCompetitionWithForeignName(@Param("competitionId") Long competitionId);

    /**
     * 新增竞赛信息
     *
     * @param competition 实体对象
     */
    public Long saveCompetition(Competition competition);

    /**
     * 更新竞赛信息
     *
     * @param competition 实体对象
     */
    public Long updateCompetition(Competition competition);

    /**
     * 根据ID删除竞赛信息
     *
     * @param competitionId ID
     */
    public Long deleteCompetition(@Param("competitionId") Long competitionId);

    List<Integer> getCompetitionEidByName(@Param("competitionName")String competitionName);
}
