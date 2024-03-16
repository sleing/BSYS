

package com.mt.tms.controller.tsonDutyEntity;

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



import com.mt.tms.dto.tsonDutyEntity.TsOnDutyEditDto;
import com.mt.tms.entity.tsonDutyEntity.TsOnDuty;
import com.mt.tms.service.tsonDutyEntity.TsOnDutyService;
					import com.mt.tms.service.tsworklaceEntity.TsWorkPlaceService;
				import com.mt.tms.service.tsstudentEntity.TsStudentInfoService;
					

@Api(tags = "值班管理")
@RestController
@RequestMapping("/api/tms/tsonDutyEntity/TsOnDuty")
@CrossOrigin(allowCredentials = "true")
public class TsOnDutyController {
private static Logger logger = LoggerFactory.getLogger(TsOnDutyController.class);



																		
														
																	@Autowired private TsOnDutyService tsOnDutyService;
		@Autowired private TsWorkPlaceService tsWorkPlaceService;
		@Autowired private TsStudentInfoService tsStudentInfoService;
	
	/**
	* 根据分页参数查询值班管理集合
	*
	* @param pageDTO 分页条件
	*/
	@PreAuthorize("hasAuthority('tms:tsonDutyEntity:TsOnDuty:view')")
	@ApiOperation("分页查询值班管理")
	@ApiPageParam
	@PostMapping("/findTsOnDutys")
	public PageResultDTO findTsOnDutys(@RequestBody PageDTO pageDTO){
		return this.tsOnDutyService.findTsOnDutys(pageDTO);
	}

	/**
	* 根据ID查询指定的值班管理
	*
	* @param tsOnDutyId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsonDutyEntity:TsOnDuty:view')")
	@ApiOperation("根据id查询值班管理")
	@ApiPageParam
	@PostMapping("/findTsOnDuty")
	public TsOnDuty findTsOnDuty(@RequestParam Long tsOnDutyId){
		return this.tsOnDutyService.findTsOnDuty(tsOnDutyId);
	}

	/**
	* 根据ID查询指定的值班管理(包含外键名称)
	*
	* @param tsOnDutyId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsonDutyEntity:TsOnDuty:view')")
	@ApiOperation("根据ID查询指定的值班管理(包含外键名称)")
	@PostMapping("/findTsOnDutyForView")
	public TsOnDuty findTsOnDutyForView(@RequestParam Long tsOnDutyId){
		return this.tsOnDutyService.findTsOnDutyWithForeignName(tsOnDutyId);
	}

	/**
	* 根据ID查询指定的值班管理(包含值班管理和外键名称)
	*
	* @param tsOnDutyId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsonDutyEntity:TsOnDuty:view')")
	@ApiOperation("根据ID查询指定的值班管理(包含值班管理和外键名称)")
	@PostMapping("/findTsOnDutyForEdit")
	public TsOnDutyEditDto findTsOnDutyForEdit(@RequestParam Long tsOnDutyId){
				TsOnDutyEditDto tsOnDutyEditDto = new TsOnDutyEditDto();
		tsOnDutyEditDto.setTsOnDuty(this.tsOnDutyService.findTsOnDutyWithForeignName(tsOnDutyId));

		this.prepareTsOnDutyEditDto(tsOnDutyEditDto);

		return tsOnDutyEditDto;
	}

	/**
	* 根据ID查询指定的值班管理(只提取ID 和 Name)
	*
	* @param tsOnDutyId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsonDutyEntity:TsOnDuty:view')")
	@ApiOperation("根据ID查询指定的值班管理(只提取ID 和 Name)")
	@PostMapping("/findTsOnDutysWithIdNameById")
	public TsOnDuty findTsOnDutysWithIdNameById(@RequestParam Long tsOnDutyId){
	return this.tsOnDutyService.findTsOnDutysWithIdNameById(tsOnDutyId);
	}

	/**
	* 根据名称查询值班管理集合(只提取ID 和 Name)
	*
	* @param tsOnDutyName 名称
	*/
	@PreAuthorize("hasAuthority('tms:tsonDutyEntity:TsOnDuty:view')")
	@ApiOperation("根据名称查询值班管理集合(只提取ID 和 Name)")
	@PostMapping("/findTsOnDutysWithIdNameByName")
	public List<TsOnDuty> findTsOnDutysWithIdNameByName(@RequestParam String tsOnDutyName){
	return this.tsOnDutyService.findTsOnDutysWithIdNameByName(tsOnDutyName);
	}


	/**
	* 创建新的值班管理
	*
	*/
	@PreAuthorize("hasAuthority('tms:tsonDutyEntity:TsOnDuty:add')")
	@ApiOperation("创建新的值班管理")
	@PostMapping("/createTsOnDuty")
	public TsOnDutyEditDto createTsOnDuty(){
				TsOnDutyEditDto tsOnDutyEditDto = new TsOnDutyEditDto();
		tsOnDutyEditDto.setTsOnDuty(new TsOnDuty());

		this.prepareTsOnDutyEditDto(tsOnDutyEditDto);
		return tsOnDutyEditDto;
	}

	private void prepareTsOnDutyEditDto(TsOnDutyEditDto tsOnDutyEditDto){
															        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		tsOnDutyEditDto.setOnDutyAddressTsWorkPlaces(this.tsWorkPlaceService.findAllTsWorkPlacesWithIdName());
											        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		tsOnDutyEditDto.setOnDutyStuTsStudentInfos(this.tsStudentInfoService.findAllTsStudentInfosWithIdName());
																}

	/**
	* 新增保存值班管理
	*
	* @param tsOnDuty 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsonDutyEntity:TsOnDuty:add')")
	@ApiOperation("新增保存值班管理")
	@PostMapping("/saveTsOnDuty")
	public TsOnDuty saveTsOnDuty(@RequestBody TsOnDuty tsOnDuty){
		return this.tsOnDutyService.saveTsOnDuty(tsOnDuty);
	}

	/**
	* 修改保存值班管理
	*
	* @param tsOnDuty 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsonDutyEntity:TsOnDuty:update')")
	@ApiOperation("修改保存值班管理")
	@PostMapping("/updateTsOnDuty")
	public TsOnDuty updateTsOnDuty(@RequestBody TsOnDuty tsOnDuty){
		return this.tsOnDutyService.updateTsOnDuty(tsOnDuty);
	}

	/**
	* 根据ID删除值班管理
	*
	* @param tsOnDutyId ID
	*/
	@PreAuthorize("hasAuthority('tms:tsonDutyEntity:TsOnDuty:remove')")
	@ApiOperation("根据ID删除值班管理")
	@PostMapping("/deleteTsOnDuty")
	public void deleteTsOnDuty(@RequestParam Long tsOnDutyId){
		this.tsOnDutyService.deleteTsOnDuty(tsOnDutyId);
	}



}

