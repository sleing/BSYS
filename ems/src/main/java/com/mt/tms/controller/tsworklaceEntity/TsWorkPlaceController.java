

package com.mt.tms.controller.tsworklaceEntity;

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



import com.mt.tms.dto.tsworklaceEntity.TsWorkPlaceEditDto;
import com.mt.tms.entity.tsworklaceEntity.TsWorkPlace;
import com.mt.tms.service.tsworklaceEntity.TsWorkPlaceService;
								

@Api(tags = "办公地点管理")
@RestController
@RequestMapping("/api/tms/tsworklaceEntity/TsWorkPlace")
@CrossOrigin(allowCredentials = "true")
public class TsWorkPlaceController {
private static Logger logger = LoggerFactory.getLogger(TsWorkPlaceController.class);



																											@Autowired private TsWorkPlaceService tsWorkPlaceService;
	
	/**
	* 根据分页参数查询办公地点管理集合
	*
	* @param pageDTO 分页条件
	*/
	@PreAuthorize("hasAuthority('tms:tsworklaceEntity:TsWorkPlace:view')")
	@ApiOperation("分页查询办公地点管理")
	@ApiPageParam
	@PostMapping("/findTsWorkPlaces")
	public PageResultDTO findTsWorkPlaces(@RequestBody PageDTO pageDTO){
		return this.tsWorkPlaceService.findTsWorkPlaces(pageDTO);
	}

	/**
	* 根据ID查询指定的办公地点管理
	*
	* @param tsWorkPlaceId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsworklaceEntity:TsWorkPlace:view')")
	@ApiOperation("根据id查询办公地点管理")
	@ApiPageParam
	@PostMapping("/findTsWorkPlace")
	public TsWorkPlace findTsWorkPlace(@RequestParam Long tsWorkPlaceId){
		return this.tsWorkPlaceService.findTsWorkPlace(tsWorkPlaceId);
	}

	/**
	* 根据ID查询指定的办公地点管理(包含外键名称)
	*
	* @param tsWorkPlaceId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsworklaceEntity:TsWorkPlace:view')")
	@ApiOperation("根据ID查询指定的办公地点管理(包含外键名称)")
	@PostMapping("/findTsWorkPlaceForView")
	public TsWorkPlace findTsWorkPlaceForView(@RequestParam Long tsWorkPlaceId){
		return this.tsWorkPlaceService.findTsWorkPlaceWithForeignName(tsWorkPlaceId);
	}

	/**
	* 根据ID查询指定的办公地点管理(包含办公地点管理和外键名称)
	*
	* @param tsWorkPlaceId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsworklaceEntity:TsWorkPlace:view')")
	@ApiOperation("根据ID查询指定的办公地点管理(包含办公地点管理和外键名称)")
	@PostMapping("/findTsWorkPlaceForEdit")
	public TsWorkPlaceEditDto findTsWorkPlaceForEdit(@RequestParam Long tsWorkPlaceId){
				TsWorkPlaceEditDto tsWorkPlaceEditDto = new TsWorkPlaceEditDto();
		tsWorkPlaceEditDto.setTsWorkPlace(this.tsWorkPlaceService.findTsWorkPlaceWithForeignName(tsWorkPlaceId));

		this.prepareTsWorkPlaceEditDto(tsWorkPlaceEditDto);

		return tsWorkPlaceEditDto;
	}

	/**
	* 根据ID查询指定的办公地点管理(只提取ID 和 Name)
	*
	* @param tsWorkPlaceId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsworklaceEntity:TsWorkPlace:view')")
	@ApiOperation("根据ID查询指定的办公地点管理(只提取ID 和 Name)")
	@PostMapping("/findTsWorkPlacesWithIdNameById")
	public TsWorkPlace findTsWorkPlacesWithIdNameById(@RequestParam Long tsWorkPlaceId){
	return this.tsWorkPlaceService.findTsWorkPlacesWithIdNameById(tsWorkPlaceId);
	}

	/**
	* 根据名称查询办公地点管理集合(只提取ID 和 Name)
	*
	* @param tsWorkPlaceName 名称
	*/
	@PreAuthorize("hasAuthority('tms:tsworklaceEntity:TsWorkPlace:view')")
	@ApiOperation("根据名称查询办公地点管理集合(只提取ID 和 Name)")
	@PostMapping("/findTsWorkPlacesWithIdNameByName")
	public List<TsWorkPlace> findTsWorkPlacesWithIdNameByName(@RequestParam String tsWorkPlaceName){
	return this.tsWorkPlaceService.findTsWorkPlacesWithIdNameByName(tsWorkPlaceName);
	}


	/**
	* 创建新的办公地点管理
	*
	*/
	@PreAuthorize("hasAuthority('tms:tsworklaceEntity:TsWorkPlace:add')")
	@ApiOperation("创建新的办公地点管理")
	@PostMapping("/createTsWorkPlace")
	public TsWorkPlaceEditDto createTsWorkPlace(){
				TsWorkPlaceEditDto tsWorkPlaceEditDto = new TsWorkPlaceEditDto();
		tsWorkPlaceEditDto.setTsWorkPlace(new TsWorkPlace());

		this.prepareTsWorkPlaceEditDto(tsWorkPlaceEditDto);
		return tsWorkPlaceEditDto;
	}

	private void prepareTsWorkPlaceEditDto(TsWorkPlaceEditDto tsWorkPlaceEditDto){
																										}

	/**
	* 新增保存办公地点管理
	*
	* @param tsWorkPlace 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsworklaceEntity:TsWorkPlace:add')")
	@ApiOperation("新增保存办公地点管理")
	@PostMapping("/saveTsWorkPlace")
	public TsWorkPlace saveTsWorkPlace(@RequestBody TsWorkPlace tsWorkPlace){
		return this.tsWorkPlaceService.saveTsWorkPlace(tsWorkPlace);
	}

	/**
	* 修改保存办公地点管理
	*
	* @param tsWorkPlace 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsworklaceEntity:TsWorkPlace:update')")
	@ApiOperation("修改保存办公地点管理")
	@PostMapping("/updateTsWorkPlace")
	public TsWorkPlace updateTsWorkPlace(@RequestBody TsWorkPlace tsWorkPlace){
		return this.tsWorkPlaceService.updateTsWorkPlace(tsWorkPlace);
	}

	/**
	* 根据ID删除办公地点管理
	*
	* @param tsWorkPlaceId ID
	*/
	@PreAuthorize("hasAuthority('tms:tsworklaceEntity:TsWorkPlace:remove')")
	@ApiOperation("根据ID删除办公地点管理")
	@PostMapping("/deleteTsWorkPlace")
	public void deleteTsWorkPlace(@RequestParam Long tsWorkPlaceId){
		this.tsWorkPlaceService.deleteTsWorkPlace(tsWorkPlaceId);
	}



}

