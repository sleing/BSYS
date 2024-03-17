

package com.mt.tms.controller.tsdepartmentEntity;

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


import com.mt.tms.dto.tsdepartmentEntity.TsDepartmentEditDto;
import com.mt.tms.entity.tsdepartmentEntity.TsDepartment;
import com.mt.tms.service.tsdepartmentEntity.TsDepartmentService;
import com.mt.tms.service.tsteacherEntity.TsTeacherInfoService;
import com.mt.tms.service.tsstudentEntity.TsStudentInfoService;
import com.mt.tms.service.tsstudentEntity.TsStudentInfoService;


@Api(tags = "部门信息管理")
@RestController
@RequestMapping("/api/tms/tsdepartmentEntity/TsDepartment")
@CrossOrigin(allowCredentials = "true")
public class TsDepartmentController {
    private static Logger logger = LoggerFactory.getLogger(TsDepartmentController.class);


    @Autowired
    private TsDepartmentService tsDepartmentService;
    @Autowired
    private TsTeacherInfoService tsTeacherInfoService;
    @Autowired
    private TsStudentInfoService tsStudentInfoService;

    /**
     * 根据分页参数查询部门信息管理集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('tms:tsdepartmentEntity:TsDepartment:view')")
    @ApiOperation("分页查询部门信息管理")
    @ApiPageParam
    @PostMapping("/findTsDepartments")
    public PageResultDTO findTsDepartments(@RequestBody PageDTO pageDTO) {
        return this.tsDepartmentService.findTsDepartments(pageDTO);
    }

    /**
     * 根据ID查询指定的部门信息管理
     *
     * @param tsDepartmentId Id
     */
    @PreAuthorize("hasAuthority('tms:tsdepartmentEntity:TsDepartment:view')")
    @ApiOperation("根据id查询部门信息管理")
    @ApiPageParam
    @PostMapping("/findTsDepartment")
    public TsDepartment findTsDepartment(@RequestParam Long tsDepartmentId) {
        return this.tsDepartmentService.findTsDepartment(tsDepartmentId);
    }

    /**
     * 根据ID查询指定的部门信息管理(包含外键名称)
     *
     * @param tsDepartmentId Id
     */
    @PreAuthorize("hasAuthority('tms:tsdepartmentEntity:TsDepartment:view')")
    @ApiOperation("根据ID查询指定的部门信息管理(包含外键名称)")
    @PostMapping("/findTsDepartmentForView")
    public TsDepartment findTsDepartmentForView(@RequestParam Long tsDepartmentId) {
        return this.tsDepartmentService.findTsDepartmentWithForeignName(tsDepartmentId);
    }

    /**
     * 根据ID查询指定的部门信息管理(包含部门信息管理和外键名称)
     *
     * @param tsDepartmentId Id
     */
    @PreAuthorize("hasAuthority('tms:tsdepartmentEntity:TsDepartment:view')")
    @ApiOperation("根据ID查询指定的部门信息管理(包含部门信息管理和外键名称)")
    @PostMapping("/findTsDepartmentForEdit")
    public TsDepartmentEditDto findTsDepartmentForEdit(@RequestParam Long tsDepartmentId) {
        TsDepartmentEditDto tsDepartmentEditDto = new TsDepartmentEditDto();
        tsDepartmentEditDto.setTsDepartment(this.tsDepartmentService.findTsDepartmentWithForeignName(tsDepartmentId));

        this.prepareTsDepartmentEditDto(tsDepartmentEditDto);

        return tsDepartmentEditDto;
    }

    /**
     * 根据ID查询指定的部门信息管理(只提取ID 和 Name)
     *
     * @param tsDepartmentId Id
     */
    @PreAuthorize("hasAuthority('tms:tsdepartmentEntity:TsDepartment:view')")
    @ApiOperation("根据ID查询指定的部门信息管理(只提取ID 和 Name)")
    @PostMapping("/findTsDepartmentsWithIdNameById")
    public TsDepartment findTsDepartmentsWithIdNameById(@RequestParam Long tsDepartmentId) {
        return this.tsDepartmentService.findTsDepartmentsWithIdNameById(tsDepartmentId);
    }

    /**
     * 根据名称查询部门信息管理集合(只提取ID 和 Name)
     *
     * @param tsDepartmentName 名称
     */
    @PreAuthorize("hasAuthority('tms:tsdepartmentEntity:TsDepartment:view')")
    @ApiOperation("根据名称查询部门信息管理集合(只提取ID 和 Name)")
    @PostMapping("/findTsDepartmentsWithIdNameByName")
    public List<TsDepartment> findTsDepartmentsWithIdNameByName(@RequestParam String tsDepartmentName) {
        return this.tsDepartmentService.findTsDepartmentsWithIdNameByName(tsDepartmentName);
    }


    /**
     * 创建新的部门信息管理
     */
    @PreAuthorize("hasAuthority('tms:tsdepartmentEntity:TsDepartment:add')")
    @ApiOperation("创建新的部门信息管理")
    @PostMapping("/createTsDepartment")
    public TsDepartmentEditDto createTsDepartment() {
        TsDepartmentEditDto tsDepartmentEditDto = new TsDepartmentEditDto();
        tsDepartmentEditDto.setTsDepartment(new TsDepartment());

        this.prepareTsDepartmentEditDto(tsDepartmentEditDto);
        return tsDepartmentEditDto;
    }

    private void prepareTsDepartmentEditDto(TsDepartmentEditDto tsDepartmentEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        tsDepartmentEditDto.setSupervisorTeaTsTeacherInfos(this.tsTeacherInfoService.findAllTsTeacherInfosWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        tsDepartmentEditDto.setSupervisorStuTsStudentInfos(this.tsStudentInfoService.findAllTsStudentInfosWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        tsDepartmentEditDto.setLeaderTsStudentInfos(this.tsStudentInfoService.findAllTsStudentInfosWithIdName());
    }

    /**
     * 新增保存部门信息管理
     *
     * @param tsDepartment 实体对象
     */
    @PreAuthorize("hasAuthority('tms:tsdepartmentEntity:TsDepartment:add')")
    @ApiOperation("新增保存部门信息管理")
    @PostMapping("/saveTsDepartment")
    public TsDepartment saveTsDepartment(@RequestBody TsDepartment tsDepartment) {
        return this.tsDepartmentService.saveTsDepartment(tsDepartment);
    }

    /**
     * 修改保存部门信息管理
     *
     * @param tsDepartment 实体对象
     */
    @PreAuthorize("hasAuthority('tms:tsdepartmentEntity:TsDepartment:update')")
    @ApiOperation("修改保存部门信息管理")
    @PostMapping("/updateTsDepartment")
    public TsDepartment updateTsDepartment(@RequestBody TsDepartment tsDepartment) {
        return this.tsDepartmentService.updateTsDepartment(tsDepartment);
    }

    /**
     * 根据ID删除部门信息管理
     *
     * @param tsDepartmentId ID
     */
    @PreAuthorize("hasAuthority('tms:tsdepartmentEntity:TsDepartment:remove')")
    @ApiOperation("根据ID删除部门信息管理")
    @PostMapping("/deleteTsDepartment")
    public void deleteTsDepartment(@RequestParam Long tsDepartmentId) {
        this.tsDepartmentService.deleteTsDepartment(tsDepartmentId);
    }


}

