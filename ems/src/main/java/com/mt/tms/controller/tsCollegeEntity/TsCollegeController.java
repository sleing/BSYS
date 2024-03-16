

package com.mt.tms.controller.tsCollegeEntity;

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



import com.mt.tms.dto.tsCollegeEntity.TsCollegeEditDto;
import com.mt.tms.entity.tsCollegeEntity.TsCollege;
import com.mt.tms.service.tsCollegeEntity.TsCollegeService;
								

@Api(tags = "院系信息")
@RestController
@RequestMapping("/api/tms/tsCollegeEntity/TsCollege")
@CrossOrigin(allowCredentials = "true")
public class TsCollegeController {
private static Logger logger = LoggerFactory.getLogger(TsCollegeController.class);



																											@Autowired private TsCollegeService tsCollegeService;
	
	/**
	* 根据分页参数查询院系信息集合
	*
	* @param pageDTO 分页条件
	*/
	@PreAuthorize("hasAuthority('tms:tsCollegeEntity:TsCollege:view')")
	@ApiOperation("分页查询院系信息")
	@ApiPageParam
	@PostMapping("/findTsColleges")
	public PageResultDTO findTsColleges(@RequestBody PageDTO pageDTO){
		return this.tsCollegeService.findTsColleges(pageDTO);
	}

	/**
	* 根据ID查询指定的院系信息
	*
	* @param tsCollegeId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsCollegeEntity:TsCollege:view')")
	@ApiOperation("根据id查询院系信息")
	@ApiPageParam
	@PostMapping("/findTsCollege")
	public TsCollege findTsCollege(@RequestParam Long tsCollegeId){
		return this.tsCollegeService.findTsCollege(tsCollegeId);
	}

	/**
	* 根据ID查询指定的院系信息(包含外键名称)
	*
	* @param tsCollegeId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsCollegeEntity:TsCollege:view')")
	@ApiOperation("根据ID查询指定的院系信息(包含外键名称)")
	@PostMapping("/findTsCollegeForView")
	public TsCollege findTsCollegeForView(@RequestParam Long tsCollegeId){
		return this.tsCollegeService.findTsCollegeWithForeignName(tsCollegeId);
	}

	/**
	* 根据ID查询指定的院系信息(包含院系信息和外键名称)
	*
	* @param tsCollegeId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsCollegeEntity:TsCollege:view')")
	@ApiOperation("根据ID查询指定的院系信息(包含院系信息和外键名称)")
	@PostMapping("/findTsCollegeForEdit")
	public TsCollegeEditDto findTsCollegeForEdit(@RequestParam Long tsCollegeId){
				TsCollegeEditDto tsCollegeEditDto = new TsCollegeEditDto();
		tsCollegeEditDto.setTsCollege(this.tsCollegeService.findTsCollegeWithForeignName(tsCollegeId));

		this.prepareTsCollegeEditDto(tsCollegeEditDto);

		return tsCollegeEditDto;
	}

	/**
	* 根据ID查询指定的院系信息(只提取ID 和 Name)
	*
	* @param tsCollegeId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsCollegeEntity:TsCollege:view')")
	@ApiOperation("根据ID查询指定的院系信息(只提取ID 和 Name)")
	@PostMapping("/findTsCollegesWithIdNameById")
	public TsCollege findTsCollegesWithIdNameById(@RequestParam Long tsCollegeId){
	return this.tsCollegeService.findTsCollegesWithIdNameById(tsCollegeId);
	}

	/**
	* 根据名称查询院系信息集合(只提取ID 和 Name)
	*
	* @param tsCollegeName 名称
	*/
	@PreAuthorize("hasAuthority('tms:tsCollegeEntity:TsCollege:view')")
	@ApiOperation("根据名称查询院系信息集合(只提取ID 和 Name)")
	@PostMapping("/findTsCollegesWithIdNameByName")
	public List<TsCollege> findTsCollegesWithIdNameByName(@RequestParam String tsCollegeName){
	return this.tsCollegeService.findTsCollegesWithIdNameByName(tsCollegeName);
	}


	/**
	* 创建新的院系信息
	*
	*/
	@PreAuthorize("hasAuthority('tms:tsCollegeEntity:TsCollege:add')")
	@ApiOperation("创建新的院系信息")
	@PostMapping("/createTsCollege")
	public TsCollegeEditDto createTsCollege(){
				TsCollegeEditDto tsCollegeEditDto = new TsCollegeEditDto();
		tsCollegeEditDto.setTsCollege(new TsCollege());

		this.prepareTsCollegeEditDto(tsCollegeEditDto);
		return tsCollegeEditDto;
	}

	private void prepareTsCollegeEditDto(TsCollegeEditDto tsCollegeEditDto){
																										}

	/**
	* 新增保存院系信息
	*
	* @param tsCollege 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsCollegeEntity:TsCollege:add')")
	@ApiOperation("新增保存院系信息")
	@PostMapping("/saveTsCollege")
	public TsCollege saveTsCollege(@RequestBody TsCollege tsCollege){
		return this.tsCollegeService.saveTsCollege(tsCollege);
	}

	/**
	* 修改保存院系信息
	*
	* @param tsCollege 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsCollegeEntity:TsCollege:update')")
	@ApiOperation("修改保存院系信息")
	@PostMapping("/updateTsCollege")
	public TsCollege updateTsCollege(@RequestBody TsCollege tsCollege){
		return this.tsCollegeService.updateTsCollege(tsCollege);
	}

	/**
	* 根据ID删除院系信息
	*
	* @param tsCollegeId ID
	*/
	@PreAuthorize("hasAuthority('tms:tsCollegeEntity:TsCollege:remove')")
	@ApiOperation("根据ID删除院系信息")
	@PostMapping("/deleteTsCollege")
	public void deleteTsCollege(@RequestParam Long tsCollegeId){
		this.tsCollegeService.deleteTsCollege(tsCollegeId);
	}



}

