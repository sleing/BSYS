

package com.mt.tms.controller.tsmeetingEntity;

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



import com.mt.tms.dto.tsmeetingEntity.TsMeetingEditDto;
import com.mt.tms.entity.tsmeetingEntity.TsMeeting;
import com.mt.tms.service.tsmeetingEntity.TsMeetingService;
					import com.mt.tms.service.tsdepartmentEntity.TsDepartmentService;
			import com.mt.tms.service.tsdepartmentEntity.TsDepartmentService;
											

@Api(tags = "会议管理")
@RestController
@RequestMapping("/api/tms/tsmeetingEntity/TsMeeting")
@CrossOrigin(allowCredentials = "true")
public class TsMeetingController {
private static Logger logger = LoggerFactory.getLogger(TsMeetingController.class);



																		
											
																																			@Autowired private TsMeetingService tsMeetingService;
		@Autowired private TsDepartmentService tsDepartmentService;
	
	/**
	* 根据分页参数查询会议管理集合
	*
	* @param pageDTO 分页条件
	*/
	@PreAuthorize("hasAuthority('tms:tsmeetingEntity:TsMeeting:view')")
	@ApiOperation("分页查询会议管理")
	@ApiPageParam
	@PostMapping("/findTsMeetings")
	public PageResultDTO findTsMeetings(@RequestBody PageDTO pageDTO){
		return this.tsMeetingService.findTsMeetings(pageDTO);
	}

	/**
	* 根据ID查询指定的会议管理
	*
	* @param tsMeetingId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsmeetingEntity:TsMeeting:view')")
	@ApiOperation("根据id查询会议管理")
	@ApiPageParam
	@PostMapping("/findTsMeeting")
	public TsMeeting findTsMeeting(@RequestParam Long tsMeetingId){
		return this.tsMeetingService.findTsMeeting(tsMeetingId);
	}

	/**
	* 根据ID查询指定的会议管理(包含外键名称)
	*
	* @param tsMeetingId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsmeetingEntity:TsMeeting:view')")
	@ApiOperation("根据ID查询指定的会议管理(包含外键名称)")
	@PostMapping("/findTsMeetingForView")
	public TsMeeting findTsMeetingForView(@RequestParam Long tsMeetingId){
		return this.tsMeetingService.findTsMeetingWithForeignName(tsMeetingId);
	}

	/**
	* 根据ID查询指定的会议管理(包含会议管理和外键名称)
	*
	* @param tsMeetingId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsmeetingEntity:TsMeeting:view')")
	@ApiOperation("根据ID查询指定的会议管理(包含会议管理和外键名称)")
	@PostMapping("/findTsMeetingForEdit")
	public TsMeetingEditDto findTsMeetingForEdit(@RequestParam Long tsMeetingId){
				TsMeetingEditDto tsMeetingEditDto = new TsMeetingEditDto();
		tsMeetingEditDto.setTsMeeting(this.tsMeetingService.findTsMeetingWithForeignName(tsMeetingId));

		this.prepareTsMeetingEditDto(tsMeetingEditDto);

		return tsMeetingEditDto;
	}

	/**
	* 根据ID查询指定的会议管理(只提取ID 和 Name)
	*
	* @param tsMeetingId Id
	*/
	@PreAuthorize("hasAuthority('tms:tsmeetingEntity:TsMeeting:view')")
	@ApiOperation("根据ID查询指定的会议管理(只提取ID 和 Name)")
	@PostMapping("/findTsMeetingsWithIdNameById")
	public TsMeeting findTsMeetingsWithIdNameById(@RequestParam Long tsMeetingId){
	return this.tsMeetingService.findTsMeetingsWithIdNameById(tsMeetingId);
	}

	/**
	* 根据名称查询会议管理集合(只提取ID 和 Name)
	*
	* @param tsMeetingName 名称
	*/
	@PreAuthorize("hasAuthority('tms:tsmeetingEntity:TsMeeting:view')")
	@ApiOperation("根据名称查询会议管理集合(只提取ID 和 Name)")
	@PostMapping("/findTsMeetingsWithIdNameByName")
	public List<TsMeeting> findTsMeetingsWithIdNameByName(@RequestParam String tsMeetingName){
	return this.tsMeetingService.findTsMeetingsWithIdNameByName(tsMeetingName);
	}


	/**
	* 创建新的会议管理
	*
	*/
	@PreAuthorize("hasAuthority('tms:tsmeetingEntity:TsMeeting:add')")
	@ApiOperation("创建新的会议管理")
	@PostMapping("/createTsMeeting")
	public TsMeetingEditDto createTsMeeting(){
				TsMeetingEditDto tsMeetingEditDto = new TsMeetingEditDto();
		tsMeetingEditDto.setTsMeeting(new TsMeeting());

		this.prepareTsMeetingEditDto(tsMeetingEditDto);
		return tsMeetingEditDto;
	}

	private void prepareTsMeetingEditDto(TsMeetingEditDto tsMeetingEditDto){
															        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		tsMeetingEditDto.setOrganizationTsDepartments(this.tsDepartmentService.findAllTsDepartmentsWithIdName());
								        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		tsMeetingEditDto.setViceOrganizationTsDepartments(this.tsDepartmentService.findAllTsDepartmentsWithIdName());
																																		}

	/**
	* 新增保存会议管理
	*
	* @param tsMeeting 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsmeetingEntity:TsMeeting:add')")
	@ApiOperation("新增保存会议管理")
	@PostMapping("/saveTsMeeting")
	public TsMeeting saveTsMeeting(@RequestBody TsMeeting tsMeeting){
		return this.tsMeetingService.saveTsMeeting(tsMeeting);
	}

	/**
	* 修改保存会议管理
	*
	* @param tsMeeting 实体对象
	*/
	@PreAuthorize("hasAuthority('tms:tsmeetingEntity:TsMeeting:update')")
	@ApiOperation("修改保存会议管理")
	@PostMapping("/updateTsMeeting")
	public TsMeeting updateTsMeeting(@RequestBody TsMeeting tsMeeting){
		return this.tsMeetingService.updateTsMeeting(tsMeeting);
	}

	/**
	* 根据ID删除会议管理
	*
	* @param tsMeetingId ID
	*/
	@PreAuthorize("hasAuthority('tms:tsmeetingEntity:TsMeeting:remove')")
	@ApiOperation("根据ID删除会议管理")
	@PostMapping("/deleteTsMeeting")
	public void deleteTsMeeting(@RequestParam Long tsMeetingId){
		this.tsMeetingService.deleteTsMeeting(tsMeetingId);
	}



}

