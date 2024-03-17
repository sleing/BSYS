

package com.mt.tms.controller.tsteacherEntity;

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


import com.mt.tms.dto.tsteacherEntity.TsTeacherInfoEditDto;
import com.mt.tms.entity.tsteacherEntity.TsTeacherInfo;
import com.mt.tms.service.tsteacherEntity.TsTeacherInfoService;
import com.mt.tms.service.tsCollegeEntity.TsCollegeService;
import com.mt.tms.service.tspositionEntity.TsPositionService;


@Api(tags = "老师信息管理")
@RestController
@RequestMapping("/api/tms/tsteacherEntity/TsTeacherInfo")
@CrossOrigin(allowCredentials = "true")
public class TsTeacherInfoController {
    private static Logger logger = LoggerFactory.getLogger(TsTeacherInfoController.class);


    @Autowired
    private TsTeacherInfoService tsTeacherInfoService;
    @Autowired
    private TsCollegeService tsCollegeService;
    @Autowired
    private TsPositionService tsPositionService;

    /**
     * 根据分页参数查询老师信息管理集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('tms:tsteacherEntity:TsTeacherInfo:view')")
    @ApiOperation("分页查询老师信息管理")
    @ApiPageParam
    @PostMapping("/findTsTeacherInfos")
    public PageResultDTO findTsTeacherInfos(@RequestBody PageDTO pageDTO) {
        return this.tsTeacherInfoService.findTsTeacherInfos(pageDTO);
    }

    /**
     * 根据ID查询指定的老师信息管理
     *
     * @param tsTeacherInfoId Id
     */
    @PreAuthorize("hasAuthority('tms:tsteacherEntity:TsTeacherInfo:view')")
    @ApiOperation("根据id查询老师信息管理")
    @ApiPageParam
    @PostMapping("/findTsTeacherInfo")
    public TsTeacherInfo findTsTeacherInfo(@RequestParam Long tsTeacherInfoId) {
        return this.tsTeacherInfoService.findTsTeacherInfo(tsTeacherInfoId);
    }

    /**
     * 根据ID查询指定的老师信息管理(包含外键名称)
     *
     * @param tsTeacherInfoId Id
     */
    @PreAuthorize("hasAuthority('tms:tsteacherEntity:TsTeacherInfo:view')")
    @ApiOperation("根据ID查询指定的老师信息管理(包含外键名称)")
    @PostMapping("/findTsTeacherInfoForView")
    public TsTeacherInfo findTsTeacherInfoForView(@RequestParam Long tsTeacherInfoId) {
        return this.tsTeacherInfoService.findTsTeacherInfoWithForeignName(tsTeacherInfoId);
    }

    /**
     * 根据ID查询指定的老师信息管理(包含老师信息管理和外键名称)
     *
     * @param tsTeacherInfoId Id
     */
    @PreAuthorize("hasAuthority('tms:tsteacherEntity:TsTeacherInfo:view')")
    @ApiOperation("根据ID查询指定的老师信息管理(包含老师信息管理和外键名称)")
    @PostMapping("/findTsTeacherInfoForEdit")
    public TsTeacherInfoEditDto findTsTeacherInfoForEdit(@RequestParam Long tsTeacherInfoId) {
        TsTeacherInfoEditDto tsTeacherInfoEditDto = new TsTeacherInfoEditDto();
        tsTeacherInfoEditDto.setTsTeacherInfo(this.tsTeacherInfoService.findTsTeacherInfoWithForeignName(tsTeacherInfoId));

        this.prepareTsTeacherInfoEditDto(tsTeacherInfoEditDto);

        return tsTeacherInfoEditDto;
    }

    /**
     * 根据ID查询指定的老师信息管理(只提取ID 和 Name)
     *
     * @param tsTeacherInfoId Id
     */
    @PreAuthorize("hasAuthority('tms:tsteacherEntity:TsTeacherInfo:view')")
    @ApiOperation("根据ID查询指定的老师信息管理(只提取ID 和 Name)")
    @PostMapping("/findTsTeacherInfosWithIdNameById")
    public TsTeacherInfo findTsTeacherInfosWithIdNameById(@RequestParam Long tsTeacherInfoId) {
        return this.tsTeacherInfoService.findTsTeacherInfosWithIdNameById(tsTeacherInfoId);
    }

    /**
     * 根据名称查询老师信息管理集合(只提取ID 和 Name)
     *
     * @param tsTeacherInfoName 名称
     */
    @PreAuthorize("hasAuthority('tms:tsteacherEntity:TsTeacherInfo:view')")
    @ApiOperation("根据名称查询老师信息管理集合(只提取ID 和 Name)")
    @PostMapping("/findTsTeacherInfosWithIdNameByName")
    public List<TsTeacherInfo> findTsTeacherInfosWithIdNameByName(@RequestParam String tsTeacherInfoName) {
        return this.tsTeacherInfoService.findTsTeacherInfosWithIdNameByName(tsTeacherInfoName);
    }


    /**
     * 创建新的老师信息管理
     */
    @PreAuthorize("hasAuthority('tms:tsteacherEntity:TsTeacherInfo:add')")
    @ApiOperation("创建新的老师信息管理")
    @PostMapping("/createTsTeacherInfo")
    public TsTeacherInfoEditDto createTsTeacherInfo() {
        TsTeacherInfoEditDto tsTeacherInfoEditDto = new TsTeacherInfoEditDto();
        tsTeacherInfoEditDto.setTsTeacherInfo(new TsTeacherInfo());

        this.prepareTsTeacherInfoEditDto(tsTeacherInfoEditDto);
        return tsTeacherInfoEditDto;
    }

    private void prepareTsTeacherInfoEditDto(TsTeacherInfoEditDto tsTeacherInfoEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        tsTeacherInfoEditDto.setCollegeTsColleges(this.tsCollegeService.findAllTsCollegesWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        tsTeacherInfoEditDto.setPositionTsPositions(this.tsPositionService.findAllTsPositionsWithIdName());
    }

    /**
     * 新增保存老师信息管理
     *
     * @param tsTeacherInfo 实体对象
     */
    @PreAuthorize("hasAuthority('tms:tsteacherEntity:TsTeacherInfo:add')")
    @ApiOperation("新增保存老师信息管理")
    @PostMapping("/saveTsTeacherInfo")
    public TsTeacherInfo saveTsTeacherInfo(@RequestBody TsTeacherInfo tsTeacherInfo) {
        return this.tsTeacherInfoService.saveTsTeacherInfo(tsTeacherInfo);
    }

    /**
     * 修改保存老师信息管理
     *
     * @param tsTeacherInfo 实体对象
     */
    @PreAuthorize("hasAuthority('tms:tsteacherEntity:TsTeacherInfo:update')")
    @ApiOperation("修改保存老师信息管理")
    @PostMapping("/updateTsTeacherInfo")
    public TsTeacherInfo updateTsTeacherInfo(@RequestBody TsTeacherInfo tsTeacherInfo) {
        return this.tsTeacherInfoService.updateTsTeacherInfo(tsTeacherInfo);
    }

    /**
     * 根据ID删除老师信息管理
     *
     * @param tsTeacherInfoId ID
     */
    @PreAuthorize("hasAuthority('tms:tsteacherEntity:TsTeacherInfo:remove')")
    @ApiOperation("根据ID删除老师信息管理")
    @PostMapping("/deleteTsTeacherInfo")
    public void deleteTsTeacherInfo(@RequestParam Long tsTeacherInfoId) {
        this.tsTeacherInfoService.deleteTsTeacherInfo(tsTeacherInfoId);
    }


}

