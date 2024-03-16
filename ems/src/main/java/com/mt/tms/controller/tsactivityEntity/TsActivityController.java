

package com.mt.tms.controller.tsactivityEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import com.mt.common.core.annotation.ApiPageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.mt.tms.dto.tsactivityEntity.TsActivityEditDto;
import com.mt.tms.entity.tsactivityEntity.TsActivity;
import com.mt.tms.service.tsactivityEntity.TsActivityService;
								import com.mt.tms.service.tsstudentEntity.TsStudentInfoService;
											

@Api(tags = "活动中心")
@RestController
@RequestMapping("/api/tms/tsactivityEntity/TsActivity")
@CrossOrigin(allowCredentials = "true")
public class TsActivityController {
private static Logger logger = LoggerFactory.getLogger(TsActivityController.class);



																											
																																			@Autowired private TsActivityService tsActivityService;
		@Autowired private TsStudentInfoService tsStudentInfoService;
	
	/**
	* 根据分页参数查询活动中心集合
	*
	* @param pageDTO 分页条件
	*/
	@PreAuthorize("hasAuthority('tms:tsactivityEntity:TsActivity:view')")
	@ApiOperation("分页查询活动中心")
	@ApiPageParam
	@PostMapping("/findTsActivitys")
	public PageResultDTO findTsActivitys(@RequestBody PageDTO pageDTO){
		return this.tsActivityService.findTsActivitys(pageDTO);
	}

	/**
	* 根据ID查询指定的活动中心
	*
	* @param tsActivityId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsactivityEntity:TsActivity:view')")
	@ApiOperation("根据id查询活动中心")
	@ApiPageParam
	@PostMapping("/findTsActivity")
	public TsActivity findTsActivity(@RequestParam Long tsActivityId){
		return this.tsActivityService.findTsActivity(tsActivityId);
	}

	/**
	* 根据ID查询指定的活动中心(包含外键名称)
	*
	* @param tsActivityId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsactivityEntity:TsActivity:view')")
	@ApiOperation("根据ID查询指定的活动中心(包含外键名称)")
	@PostMapping("/findTsActivityForView")
	public TsActivity findTsActivityForView(@RequestParam Long tsActivityId){
		return this.tsActivityService.findTsActivityWithForeignName(tsActivityId);
	}

	/**
	* 根据ID查询指定的活动中心(包含活动中心和外键名称)
	*
	* @param tsActivityId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsactivityEntity:TsActivity:view')")
	@ApiOperation("根据ID查询指定的活动中心(包含活动中心和外键名称)")
	@PostMapping("/findTsActivityForEdit")
	public TsActivityEditDto findTsActivityForEdit(@RequestParam Long tsActivityId){
				TsActivityEditDto tsActivityEditDto = new TsActivityEditDto();
		tsActivityEditDto.setTsActivity(this.tsActivityService.findTsActivityWithForeignName(tsActivityId));

		this.prepareTsActivityEditDto(tsActivityEditDto);

		return tsActivityEditDto;
	}

	/**
	* 根据ID查询指定的活动中心(只提取ID 和 Name)
	*
	* @param tsActivityId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsactivityEntity:TsActivity:view')")
	@ApiOperation("根据ID查询指定的活动中心(只提取ID 和 Name)")
	@PostMapping("/findTsActivitysWithIdNameById")
	public TsActivity findTsActivitysWithIdNameById(@RequestParam Long tsActivityId){
	return this.tsActivityService.findTsActivitysWithIdNameById(tsActivityId);
	}

	/**
	* 根据名称查询活动中心集合(只提取ID 和 Name)
	*
	* @param tsActivityName 名称
	*/
	@PreAuthorize("hasAuthority('tms:tsactivityEntity:TsActivity:view')")
	@ApiOperation("根据名称查询活动中心集合(只提取ID 和 Name)")
	@PostMapping("/findTsActivitysWithIdNameByName")
	public List<TsActivity> findTsActivitysWithIdNameByName(@RequestParam String tsActivityName){
	return this.tsActivityService.findTsActivitysWithIdNameByName(tsActivityName);
	}


	/**
	* 创建新的活动中心
	*
	*/
	@PreAuthorize("hasAuthority('tms:tsactivityEntity:TsActivity:add')")
	@ApiOperation("创建新的活动中心")
	@PostMapping("/createTsActivity")
	public TsActivityEditDto createTsActivity(){
				TsActivityEditDto tsActivityEditDto = new TsActivityEditDto();
		tsActivityEditDto.setTsActivity(new TsActivity());

		this.prepareTsActivityEditDto(tsActivityEditDto);
		return tsActivityEditDto;
	}

	private void prepareTsActivityEditDto(TsActivityEditDto tsActivityEditDto){
																								        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		tsActivityEditDto.setActivityHostTsStudentInfos(this.tsStudentInfoService.findAllTsStudentInfosWithIdName());
																																		}

	/**
	* 新增保存活动中心
	*
	* @param tsActivity 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsactivityEntity:TsActivity:add')")
	@ApiOperation("新增保存活动中心")
	@PostMapping("/saveTsActivity")
	public TsActivity saveTsActivity(@RequestBody TsActivity tsActivity){
		return this.tsActivityService.saveTsActivity(tsActivity);
	}

	/**
	* 修改保存活动中心
	*
	* @param tsActivity 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsactivityEntity:TsActivity:update')")
	@ApiOperation("修改保存活动中心")
	@PostMapping("/updateTsActivity")
	public TsActivity updateTsActivity(@RequestBody TsActivity tsActivity){
		return this.tsActivityService.updateTsActivity(tsActivity);
	}

	/**
	* 根据ID删除活动中心
	*
	* @param tsActivityId ID
	*/
	@PreAuthorize("hasAuthority('tms:tsactivityEntity:TsActivity:remove')")
	@ApiOperation("根据ID删除活动中心")
	@PostMapping("/deleteTsActivity")
	public void deleteTsActivity(@RequestParam Long tsActivityId){
		this.tsActivityService.deleteTsActivity(tsActivityId);
	}



}

