package com.mt.bms.service.rewardEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.bms.entity.rewardEntity.Reward;

import java.util.List;

public interface RewardService {
    /**
     * 根据分页参数查询获奖登记集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findRewards(PageDTO pageDTO);

    /**
     * 查询全部获奖登记集合
     *
     */
    public List<Reward> findAllRewards();

    /**
     * 根据名称查询获奖登记集合(只提取ID 和 Name)
     *
     * @param rewardName 名称
     */
    public List<Reward> findRewardsWithIdNameByName(String rewardName);

    /**
     * 查询所有获奖登记集合(只提取ID 和 Name)
     *
     */
    public List<Reward> findAllRewardsWithIdName();

    /**
     * 根据ID查询指定的获奖登记(只提取ID 和 Name)
     *
     * @param rewardId Id
     */
    public Reward findRewardsWithIdNameById(Long rewardId);

    /**
     * 根据ID查询指定的获奖登记
     *
     * @param rewardId Id
     */
    public Reward findReward(Long rewardId);

    /**
     * 根据ID查询指定的获奖登记(包含外键)
     *
     * @param rewardId Id
     */
    public Reward findRewardWithForeignName(Long rewardId);

    /**
     * 新增获奖登记
     *
     * @param reward 实体对象
     */
    public Reward saveReward(Reward reward);

    /**
     * 更新获奖登记
     *
     * @param reward 实体对象
     */
    public Reward updateReward(Reward reward);

    /**
     * 根据ID删除获奖登记
     *
     * @param rewardId ID
     */
    public void deleteReward(Long rewardId);
}
