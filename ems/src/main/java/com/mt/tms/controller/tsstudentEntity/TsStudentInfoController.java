

package com.mt.tms.controller.tsstudentEntity;

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



import com.mt.tms.dto.tsstudentEntity.TsStudentInfoEditDto;
import com.mt.tms.entity.tsstudentEntity.TsStudentInfo;
import com.mt.tms.service.tsstudentEntity.TsStudentInfoService;
					import com.mt.tms.service.tsCollegeEntity.TsCollegeService;
												

@Api(tags = "团学会学生信息管理")
@RestController
@RequestMapping("/api/tms/tsstudentEntity/TsStudentInfo")
@CrossOrigin(allowCredentials = "true")
public class TsStudentInfoController {
private static Logger logger = LoggerFactory.getLogger(TsStudentInfoController.class);



																		
																																						@Autowired private TsStudentInfoService tsStudentInfoService;
		@Autowired private TsCollegeService tsCollegeService;
	
	/**
	* 根据分页参数查询团学会学生信息管理集合
	*
	* @param pageDTO 分页条件
	*/
	@PreAuthorize("hasAuthority('tms:tsstudentEntity:TsStudentInfo:view')")
	@ApiOperation("分页查询团学会学生信息管理")
	@ApiPageParam
	@PostMapping("/findTsStudentInfos")
	public PageResultDTO findTsStudentInfos(@RequestBody PageDTO pageDTO){
		return this.tsStudentInfoService.findTsStudentInfos(pageDTO);
	}

	/**
	* 根据ID查询指定的团学会学生信息管理
	*
	* @param tsStudentInfoId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsstudentEntity:TsStudentInfo:view')")
	@ApiOperation("根据id查询团学会学生信息管理")
	@ApiPageParam
	@PostMapping("/findTsStudentInfo")
	public TsStudentInfo findTsStudentInfo(@RequestParam Long tsStudentInfoId){
		return this.tsStudentInfoService.findTsStudentInfo(tsStudentInfoId);
	}

	/**
	* 根据ID查询指定的团学会学生信息管理(包含外键名称)
	*
	* @param tsStudentInfoId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsstudentEntity:TsStudentInfo:view')")
	@ApiOperation("根据ID查询指定的团学会学生信息管理(包含外键名称)")
	@PostMapping("/findTsStudentInfoForView")
	public TsStudentInfo findTsStudentInfoForView(@RequestParam Long tsStudentInfoId){
		return this.tsStudentInfoService.findTsStudentInfoWithForeignName(tsStudentInfoId);
	}

	/**
	* 根据ID查询指定的团学会学生信息管理(包含团学会学生信息管理和外键名称)
	*
	* @param tsStudentInfoId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsstudentEntity:TsStudentInfo:view')")
	@ApiOperation("根据ID查询指定的团学会学生信息管理(包含团学会学生信息管理和外键名称)")
	@PostMapping("/findTsStudentInfoForEdit")
	public TsStudentInfoEditDto findTsStudentInfoForEdit(@RequestParam Long tsStudentInfoId){
				TsStudentInfoEditDto tsStudentInfoEditDto = new TsStudentInfoEditDto();
		tsStudentInfoEditDto.setTsStudentInfo(this.tsStudentInfoService.findTsStudentInfoWithForeignName(tsStudentInfoId));

		this.prepareTsStudentInfoEditDto(tsStudentInfoEditDto);

		return tsStudentInfoEditDto;
	}

	/**
	* 根据ID查询指定的团学会学生信息管理(只提取ID 和 Name)
	*
	* @param tsStudentInfoId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsstudentEntity:TsStudentInfo:view')")
	@ApiOperation("根据ID查询指定的团学会学生信息管理(只提取ID 和 Name)")
	@PostMapping("/findTsStudentInfosWithIdNameById")
	public TsStudentInfo findTsStudentInfosWithIdNameById(@RequestParam Long tsStudentInfoId){
	return this.tsStudentInfoService.findTsStudentInfosWithIdNameById(tsStudentInfoId);
	}

	/**
	* 根据名称查询团学会学生信息管理集合(只提取ID 和 Name)
	*
	* @param tsStudentInfoName 名称
	*/
	@PreAuthorize("hasAuthority('tms:tsstudentEntity:TsStudentInfo:view')")
	@ApiOperation("根据名称查询团学会学生信息管理集合(只提取ID 和 Name)")
	@PostMapping("/findTsStudentInfosWithIdNameByName")
	public List<TsStudentInfo> findTsStudentInfosWithIdNameByName(@RequestParam String tsStudentInfoName){
	return this.tsStudentInfoService.findTsStudentInfosWithIdNameByName(tsStudentInfoName);
	}


	/**
	* 创建新的团学会学生信息管理
	*
	*/
	@PreAuthorize("hasAuthority('tms:tsstudentEntity:TsStudentInfo:add')")
	@ApiOperation("创建新的团学会学生信息管理")
	@PostMapping("/createTsStudentInfo")
	public TsStudentInfoEditDto createTsStudentInfo(){
				TsStudentInfoEditDto tsStudentInfoEditDto = new TsStudentInfoEditDto();
		tsStudentInfoEditDto.setTsStudentInfo(new TsStudentInfo());

		this.prepareTsStudentInfoEditDto(tsStudentInfoEditDto);
		return tsStudentInfoEditDto;
	}

	private void prepareTsStudentInfoEditDto(TsStudentInfoEditDto tsStudentInfoEditDto){
															        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		tsStudentInfoEditDto.setCollegeTsColleges(this.tsCollegeService.findAllTsCollegesWithIdName());
																																					}

	/**
	* 新增保存团学会学生信息管理
	*
	* @param tsStudentInfo 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsstudentEntity:TsStudentInfo:add')")
	@ApiOperation("新增保存团学会学生信息管理")
	@PostMapping("/saveTsStudentInfo")
	public TsStudentInfo saveTsStudentInfo(@RequestBody TsStudentInfo tsStudentInfo){
		return this.tsStudentInfoService.saveTsStudentInfo(tsStudentInfo);
	}

	/**
	* 修改保存团学会学生信息管理
	*
	* @param tsStudentInfo 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsstudentEntity:TsStudentInfo:update')")
	@ApiOperation("修改保存团学会学生信息管理")
	@PostMapping("/updateTsStudentInfo")
	public TsStudentInfo updateTsStudentInfo(@RequestBody TsStudentInfo tsStudentInfo){
		return this.tsStudentInfoService.updateTsStudentInfo(tsStudentInfo);
	}

	/**
	* 根据ID删除团学会学生信息管理
	*
	* @param tsStudentInfoId ID
	*/
	@PreAuthorize("hasAuthority('tms:tsstudentEntity:TsStudentInfo:remove')")
	@ApiOperation("根据ID删除团学会学生信息管理")
	@PostMapping("/deleteTsStudentInfo")
	public void deleteTsStudentInfo(@RequestParam Long tsStudentInfoId){
		this.tsStudentInfoService.deleteTsStudentInfo(tsStudentInfoId);
	}



}

