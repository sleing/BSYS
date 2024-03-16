

package com.mt.bms.controller.rewardEntity;

import com.mt.bms.dto.rewardEntity.RewardEditDto;
import com.mt.bms.entity.rewardEntity.Reward;
import com.mt.bms.service.rewardEntity.RewardService;
import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
																		

@Api(tags = "获奖登记")
@RestController
@RequestMapping("/api/bms/rewardEntity/Reward")
@CrossOrigin(allowCredentials = "true")
public class RewardController {
private static Logger logger = LoggerFactory.getLogger(RewardController.class);



																																																									@Autowired private RewardService rewardService;
	
	/**
	* 根据分页参数查询获奖登记集合
	*
	* @param pageDTO 分页条件
	*/
	@PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
	@ApiOperation("分页查询获奖登记")
	@ApiPageParam
	@PostMapping("/findRewards")
	public PageResultDTO findRewards(@RequestBody PageDTO pageDTO){
		return this.rewardService.findRewards(pageDTO);
	}

	/**
	* 根据ID查询指定的获奖登记
	*
	* @param rewardId Id
	*/
	@PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
	@ApiOperation("根据id查询获奖登记")
	@ApiPageParam
	@PostMapping("/findReward")
	public Reward findReward(@RequestParam Long rewardId){
		return this.rewardService.findReward(rewardId);
	}

	/**
	* 根据ID查询指定的获奖登记(包含外键名称)
	*
	* @param rewardId Id
	*/
	@PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
	@ApiOperation("根据ID查询指定的获奖登记(包含外键名称)")
	@PostMapping("/findRewardForView")
	public Reward findRewardForView(@RequestParam Long rewardId){
		return this.rewardService.findRewardWithForeignName(rewardId);
	}

	/**
	* 根据ID查询指定的获奖登记(包含获奖登记和外键名称)
	*
	* @param rewardId Id
	*/
	@PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
	@ApiOperation("根据ID查询指定的获奖登记(包含获奖登记和外键名称)")
	@PostMapping("/findRewardForEdit")
	public RewardEditDto findRewardForEdit(@RequestParam Long rewardId){
				RewardEditDto rewardEditDto = new RewardEditDto();
		rewardEditDto.setReward(this.rewardService.findRewardWithForeignName(rewardId));

		this.prepareRewardEditDto(rewardEditDto);

		return rewardEditDto;
	}

	/**
	* 根据ID查询指定的获奖登记(只提取ID 和 Name)
	*
	* @param rewardId Id
	*/
	@PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
	@ApiOperation("根据ID查询指定的获奖登记(只提取ID 和 Name)")
	@PostMapping("/findRewardsWithIdNameById")
	public Reward findRewardsWithIdNameById(@RequestParam Long rewardId){
	return this.rewardService.findRewardsWithIdNameById(rewardId);
	}

	/**
	* 根据名称查询获奖登记集合(只提取ID 和 Name)
	*
	* @param rewardName 名称
	*/
	@PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
	@ApiOperation("根据名称查询获奖登记集合(只提取ID 和 Name)")
	@PostMapping("/findRewardsWithIdNameByName")
	public List<Reward> findRewardsWithIdNameByName(@RequestParam String rewardName){
	return this.rewardService.findRewardsWithIdNameByName(rewardName);
	}


	/**
	* 创建新的获奖登记
	*
	*/
	@PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
	@ApiOperation("创建新的获奖登记")
	@PostMapping("/createReward")
	public RewardEditDto createReward(){
				RewardEditDto rewardEditDto = new RewardEditDto();
		rewardEditDto.setReward(new Reward());

		this.prepareRewardEditDto(rewardEditDto);
		return rewardEditDto;
	}

	private void prepareRewardEditDto(RewardEditDto rewardEditDto){
																																																								}

	/**
	* 新增保存获奖登记
	*
	* @param reward 实体对象
	*/
	@PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
	@ApiOperation("新增保存获奖登记")
	@PostMapping("/saveReward")
	public Reward saveReward(@RequestBody Reward reward){
		return this.rewardService.saveReward(reward);
	}

	/**
	* 修改保存获奖登记
	*
	* @param reward 实体对象
	*/
	@PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
	@ApiOperation("修改保存获奖登记")
	@PostMapping("/updateReward")
	public Reward updateReward(@RequestBody Reward reward){
		return this.rewardService.updateReward(reward);
	}

	/**
	* 根据ID删除获奖登记
	*
	* @param rewardId ID
	*/
	@PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
	@ApiOperation("根据ID删除获奖登记")
	@PostMapping("/deleteReward")
	public void deleteReward(@RequestParam Long rewardId){
		this.rewardService.deleteReward(rewardId);
	}



}

