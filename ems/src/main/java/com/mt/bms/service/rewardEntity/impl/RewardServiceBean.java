package com.mt.bms.service.rewardEntity.impl;

import com.mt.bms.dao.rewardEntity.RewardDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.bms.entity.rewardEntity.Reward;
import com.mt.bms.service.rewardEntity.RewardService;
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
public class RewardServiceBean extends BaseService implements RewardService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private RewardDao rewardDao;

	@Resource
	private RedisTemplate<String, List<Reward>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询获奖登记集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findRewards(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		//TODO:请在此校验参数的合法性
		this.validateFindRewards(pageDTO);
		List<Reward> rewardDTOS = this.rewardDao.findRewards(pageDTO);
		Long totalCount = this.rewardDao.findRewardTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(rewardDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部获奖登记集合
	 *
	 */
	@Override
	public List<Reward> findAllRewards(){
		return this.rewardDao.findAllRewards();
	}

	/**
	 * 查询所有获奖登记集合(只提取ID 和 Name)
	 *
	 */
	@Override
	public List<Reward> findAllRewardsWithIdName(){
		//TODO:请在此校验参数的合法性
		this.validateFindAllRewardsWithIdName();
		return this.rewardDao.findAllRewardsWithIdName();
	}

	/**
	 * 根据名称查询获奖登记集合(只提取ID 和 Name)
	 *
	 * @param rewardName 名称
	 */
	@Override
	public List<Reward> findRewardsWithIdNameByName(String rewardName){
		//TODO:请在此校验参数的合法性
		this.validateFindRewardsWithIdNameByName(rewardName);
		//TODO:缓存取对应参数
		Set<String> keys = stringRedisTemplate.keys("searchData:Reward_where_rewardName_" + rewardName);
		List<Reward> rewards = new ArrayList<>();
		if (keys.isEmpty()) {
		rewards = this.rewardDao.findRewardsWithIdNameByName(rewardName);
		redisTemplate.opsForValue().set("searchData:Reward_where_rewardName_" + rewardName, rewards, 30, TimeUnit.DAYS);
		} else {
		rewards = redisTemplate.opsForValue().get("searchData:Reward_where_rewardName_" + rewardName);
		}
		return rewards;
	}

	/**
	 * 根据ID查询指定的获奖登记(只提取ID 和 Name)
	 *
	 * @param rewardId Id
	 */
	@Override
	public Reward findRewardsWithIdNameById(Long rewardId){
		//TODO:请在此校验参数的合法性
		this.validateFindRewardsWithIdNameById(rewardId);
		return this.rewardDao.findRewardsWithIdNameById(rewardId);
	}

	/**
	 * 根据ID查询指定的获奖登记
	 *
	 * @param rewardId Id
	 */
	@Override
	public Reward findReward(Long rewardId){
		//TODO:请在此校验参数的合法性
		this.validateFindReward(rewardId);
		return this.rewardDao.findReward(rewardId);
	}

	/**
	 * 根据ID查询指定的获奖登记(包含外键)
	 *
	 * @param rewardId Id
	 */
	@Override
	public Reward findRewardWithForeignName(Long rewardId){
		//TODO:请在此校验参数的合法性
		this.validateFindRewardWithForeignName(rewardId);
		return this.rewardDao.findRewardWithForeignName(rewardId);
	}

	/**
	 * 新增获奖登记
	 *
	 * @param reward 实体对象
	 */
	@Override
	public Reward saveReward(Reward reward){
		//TODO:请在此校验参数的合法性
		this.validateSaveReward(reward);
		//TODO:填充公共参数
		this.setSavePulicColumns(reward);
		Long rows = this.rewardDao.saveReward(reward);
		if(rows != 1)
		{
			String error = "新增保存获奖登记出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return reward;
	}

	/**
	 * 更新获奖登记
	 *
	 * @param reward 实体对象
	 */
	@Override
	public Reward updateReward(Reward reward){
		//TODO:请在此校验参数的合法性
		this.validateUpdateReward(reward);
		Long rows = this.rewardDao.updateReward(reward);
		if(rows != 1)
		{
			String error = "修改保存获奖登记出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return reward;
	}

	/**
	 * 根据ID删除获奖登记
	 *
	 * @param rewardId ID
	 */
	@Override
	public void deleteReward(Long rewardId){
		//TODO:请在此校验参数的合法性
		this.validateDeleteReward(rewardId);

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(Reward.class, rewardId);
		if(entityUsageMap != null && entityUsageMap.size() >0){
			StringBuilder errors = new StringBuilder();
			errors.append("计划删除的数据正在被以下数引用\n");
			for(EntityUsage entityUsage : entityUsageMap.values()){
				errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
				for(Map.Entry<Long,String> entry : entityUsage.getUsageIdNames().entrySet() ){
					errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
				}
			}
			errors.append("，不能删除，请检查处理后再删除");
			throw  new BusinessException(errors.toString());
		}

		Long rows = this.rewardDao.deleteReward(rewardId);
		if(rows != 1){
			String error = "删除获奖登记出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	//TODO:---------------验证-------------------

	private void validateFindRewards(PageDTO pageDTO) {
	//TODO:请使用下面方法添加数据过滤条件
	//		pageDTO.addFilter("creatorId",this.getLoginUserId());
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateReward()写法
	}

	private void validateFindRewardsWithIdNameByName(String rewardName) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateReward()写法
	}


	private void validateFindAllRewardsWithIdName() {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateReward()写法
	}

	private void validateFindRewardsWithIdNameById(Long rewardId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateReward()写法
	}

	private void validateFindReward(Long rewardId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateReward()写法
	}

	private void validateFindRewardWithForeignName(Long rewardId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateReward()写法
	}

	private void validateSaveReward(Reward reward) {
	//不为空判断
	if (reward.getEid() != null || reward.getCreatorId() != null || reward.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateReward()写法
	}

	private void validateUpdateReward(Reward reward) {
	//不为空判断
	if (reward.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.rewardDao.findRewardTotalCount(PageDTO.create(Reward.FIELD_ID, reward.getEid())) == 0) {
	throw new BusinessException("修改的获奖登记 " + reward.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateReward()写法
	}

	private void validateDeleteReward(Long rewardId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateReward()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}
}
