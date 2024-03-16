package com.mt.ams.dao.awardEntity;

import com.mt.ams.entity.awardEntity.Award;
import com.mt.common.core.web.base.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "awardDao")
public interface AwardDao {

    /**
     * 根据分页参数查询获奖登记集合
     *
     * @param pageDTO 分页条件
     */
    public List<Award> findAwards(PageDTO pageDTO);

    /**
     * 查询全部获奖登记集合
     */
    public List<Award> findAllAwards();

    /**
     * 查询所有获奖登记集合(只提取ID 和 Name)
     */
    public List<Award> findAllAwardsWithIdName();

    /**
     * 根据名称查询获奖登记集合(只提取ID 和 Name)
     *
     * @param awardName 名称
     */
    public List<Award> findAwardsWithIdNameByName(@Param("awardName") String awardName);

    /**
     * 根据ID查询指定的获奖登记(只提取ID 和 Name)
     *
     * @param awardId Id
     */
    public Award findAwardsWithIdNameById(@Param(" awardId") Long awardId);

    /**
     * 根据分页参数查询获奖登记集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findAwardTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的获奖登记
     *
     * @param awardId Id
     */
    public Award findAward(@Param("awardId") Long awardId);

    /**
     * 根据ID查询指定的获奖登记（审核已通过的）
     *
     * @param awardId Id
     */
    public Award findAuditAward(@Param("awardId") Long awardId);

    /**
     * 根据ID查询指定的获奖登记(包含外键)
     *
     * @param awardId Id
     */
    public Award findAwardWithForeignName(@Param("awardId") Long awardId);

    /**
     * 新增获奖登记
     *
     * @param award 实体对象
     */
    public Long saveAward(Award award);

    /**
     * 更新获奖登记
     *
     * @param award 实体对象
     */
    public Long updateAward(Award award);

    /**
     * 根据ID删除获奖登记
     *
     * @param awardId ID
     */
    public Long deleteAward(@Param("awardId") Long awardId);

    public Award findAwardNC(Long awardId);

    public void myUpdate(Long awardId);

    public void auditForCon(Long awardId, String remarkContent);

    public List<Award> findAwardById(Long collegeId);

    public String fileUrl(Long eid);

    public String resourcesUrl(Long eid);

    public void updateReviewer(Long Id,Long eid);

}
