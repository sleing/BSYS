

package com.mt.tms.controller.tspositionEntity;

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



import com.mt.tms.dto.tspositionEntity.TsPositionEditDto;
import com.mt.tms.entity.tspositionEntity.TsPosition;
import com.mt.tms.service.tspositionEntity.TsPositionService;
								

@Api(tags = "职务管理")
@RestController
@RequestMapping("/api/tms/tspositionEntity/TsPosition")
@CrossOrigin(allowCredentials = "true")
public class TsPositionController {
private static Logger logger = LoggerFactory.getLogger(TsPositionController.class);



																											@Autowired private TsPositionService tsPositionService;
	
	/**
	* 根据分页参数查询职务管理集合
	*
	* @param pageDTO 分页条件
	*/
	@PreAuthorize("hasAuthority('tms:tspositionEntity:TsPosition:view')")
	@ApiOperation("分页查询职务管理")
	@ApiPageParam
	@PostMapping("/findTsPositions")
	public PageResultDTO findTsPositions(@RequestBody PageDTO pageDTO){
		return this.tsPositionService.findTsPositions(pageDTO);
	}

	/**
	* 根据ID查询指定的职务管理
	*
	* @param tsPositionId Id
	*/
	@PreAuthorize("hasAuthority('tms:tspositionEntity:TsPosition:view')")
	@ApiOperation("根据id查询职务管理")
	@ApiPageParam
	@PostMapping("/findTsPosition")
	public TsPosition findTsPosition(@RequestParam Long tsPositionId){
		return this.tsPositionService.findTsPosition(tsPositionId);
	}

	/**
	* 根据ID查询指定的职务管理(包含外键名称)
	*
	* @param tsPositionId Id
	*/
	@PreAuthorize("hasAuthority('tms:tspositionEntity:TsPosition:view')")
	@ApiOperation("根据ID查询指定的职务管理(包含外键名称)")
	@PostMapping("/findTsPositionForView")
	public TsPosition findTsPositionForView(@RequestParam Long tsPositionId){
		return this.tsPositionService.findTsPositionWithForeignName(tsPositionId);
	}

	/**
	* 根据ID查询指定的职务管理(包含职务管理和外键名称)
	*
	* @param tsPositionId Id
	*/
	@PreAuthorize("hasAuthority('tms:tspositionEntity:TsPosition:view')")
	@ApiOperation("根据ID查询指定的职务管理(包含职务管理和外键名称)")
	@PostMapping("/findTsPositionForEdit")
	public TsPositionEditDto findTsPositionForEdit(@RequestParam Long tsPositionId){
				TsPositionEditDto tsPositionEditDto = new TsPositionEditDto();
		tsPositionEditDto.setTsPosition(this.tsPositionService.findTsPositionWithForeignName(tsPositionId));

		this.prepareTsPositionEditDto(tsPositionEditDto);

		return tsPositionEditDto;
	}

	/**
	* 根据ID查询指定的职务管理(只提取ID 和 Name)
	*
	* @param tsPositionId Id
	*/
	@PreAuthorize("hasAuthority('tms:tspositionEntity:TsPosition:view')")
	@ApiOperation("根据ID查询指定的职务管理(只提取ID 和 Name)")
	@PostMapping("/findTsPositionsWithIdNameById")
	public TsPosition findTsPositionsWithIdNameById(@RequestParam Long tsPositionId){
	return this.tsPositionService.findTsPositionsWithIdNameById(tsPositionId);
	}

	/**
	* 根据名称查询职务管理集合(只提取ID 和 Name)
	*
	* @param tsPositionName 名称
	*/
	@PreAuthorize("hasAuthority('tms:tspositionEntity:TsPosition:view')")
	@ApiOperation("根据名称查询职务管理集合(只提取ID 和 Name)")
	@PostMapping("/findTsPositionsWithIdNameByName")
	public List<TsPosition> findTsPositionsWithIdNameByName(@RequestParam String tsPositionName){
	return this.tsPositionService.findTsPositionsWithIdNameByName(tsPositionName);
	}


	/**
	* 创建新的职务管理
	*
	*/
	@PreAuthorize("hasAuthority('tms:tspositionEntity:TsPosition:add')")
	@ApiOperation("创建新的职务管理")
	@PostMapping("/createTsPosition")
	public TsPositionEditDto createTsPosition(){
				TsPositionEditDto tsPositionEditDto = new TsPositionEditDto();
		tsPositionEditDto.setTsPosition(new TsPosition());

		this.prepareTsPositionEditDto(tsPositionEditDto);
		return tsPositionEditDto;
	}

	private void prepareTsPositionEditDto(TsPositionEditDto tsPositionEditDto){
																										}

	/**
	* 新增保存职务管理
	*
	* @param tsPosition 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tspositionEntity:TsPosition:add')")
	@ApiOperation("新增保存职务管理")
	@PostMapping("/saveTsPosition")
	public TsPosition saveTsPosition(@RequestBody TsPosition tsPosition){
		return this.tsPositionService.saveTsPosition(tsPosition);
	}

	/**
	* 修改保存职务管理
	*
	* @param tsPosition 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tspositionEntity:TsPosition:update')")
	@ApiOperation("修改保存职务管理")
	@PostMapping("/updateTsPosition")
	public TsPosition updateTsPosition(@RequestBody TsPosition tsPosition){
		return this.tsPositionService.updateTsPosition(tsPosition);
	}

	/**
	* 根据ID删除职务管理
	*
	* @param tsPositionId ID
	*/
	@PreAuthorize("hasAuthority('tms:tspositionEntity:TsPosition:remove')")
	@ApiOperation("根据ID删除职务管理")
	@PostMapping("/deleteTsPosition")
	public void deleteTsPosition(@RequestParam Long tsPositionId){
		this.tsPositionService.deleteTsPosition(tsPositionId);
	}



}

