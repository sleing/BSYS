package com.mt.bms.dao.rewardEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.bms.entity.rewardEntity.Reward;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "rewardDao")
public interface RewardDao {

    /**
    * 根据分页参数查询获奖登记集合
    *
    * @param pageDTO 分页条件
    */
    public List<Reward> findRewards(PageDTO pageDTO);

    /**
    * 查询全部获奖登记集合
    *
    */
    public List<Reward> findAllRewards();

    /**
    * 查询所有获奖登记集合(只提取ID 和 Name)
    *
    */
    public List<Reward> findAllRewardsWithIdName();

    /**
    * 根据名称查询获奖登记集合(只提取ID 和 Name)
    *
    * @param rewardName 名称
    */
    public List<Reward> findRewardsWithIdNameByName(@Param("rewardName") String rewardName);

    /**
    * 根据ID查询指定的获奖登记(只提取ID 和 Name)
    *
    * @param rewardId Id
    */
    public Reward findRewardsWithIdNameById(@Param(" rewardId") Long rewardId);

    /**
    * 根据分页参数查询获奖登记集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findRewardTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的获奖登记
    *
    * @param rewardId Id
    */
    public Reward findReward(@Param("rewardId") Long rewardId);

    /**
    * 根据ID查询指定的获奖登记(包含外键)
    *
    * @param rewardId Id
    */
    public Reward findRewardWithForeignName(@Param("rewardId") Long rewardId);

    /**
    * 新增获奖登记
    *
    * @param reward 实体对象
    */
    public Long saveReward(Reward reward);

    /**
    * 更新获奖登记
    *
    * @param reward 实体对象
    */
    public Long updateReward(Reward reward);

    /**
    * 根据ID删除获奖登记
    *
    * @param rewardId ID
    */
    public Long deleteReward(@Param("rewardId") Long rewardId);
}
