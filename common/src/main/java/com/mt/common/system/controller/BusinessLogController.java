package com.mt.common.system.controller;

import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.system.dto.system.BusinessLogEditDto;
import com.mt.common.system.entity.BusinessLog;
import com.mt.common.system.service.BusinessLogService;
import com.mt.common.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "业务日志")
@RestController
@RequestMapping("/api/erp/system/BusinessLog")
@CrossOrigin(allowCredentials = "true")
public class BusinessLogController {
    private static Logger logger = LoggerFactory.getLogger(BusinessLogController.class);

    @Autowired
    private BusinessLogService businessLogService;
    @Autowired
    private UserService userService;

    /**
     * 根据分页参数查询业务日志集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('erp:system:BusinessLog:view')")
    @ApiOperation("分页查询业务日志")
    @ApiPageParam
    @PostMapping("/findBusinessLogs")
    public PageResultDTO findBusinessLogs(@RequestBody PageDTO pageDTO) {
        return this.businessLogService.findBusinessLogs(pageDTO);
    }

    /**
     * 根据ID查询指定的业务日志
     *
     * @param businessLogId Id
     */
    @PreAuthorize("hasAuthority('erp:system:BusinessLog:view')")
    @ApiOperation("根据id查询业务日志")
    @ApiPageParam
    @PostMapping("/findBusinessLog")
    public BusinessLog findBusinessLog(@RequestParam Long businessLogId) {
        return this.businessLogService.findBusinessLog(businessLogId);
    }

    /**
     * 根据ID查询指定的业务日志(包含外键名称)
     *
     * @param businessLogId Id
     */
    @PreAuthorize("hasAuthority('erp:system:BusinessLog:view')")
    @ApiOperation("根据ID查询指定的业务日志(包含外键名称)")
    @PostMapping("/findBusinessLogForView")
    public BusinessLog findBusinessLogForView(@RequestParam Long businessLogId) {
        return this.businessLogService.findBusinessLogWithForeignName(businessLogId);
    }

    /**
     * 根据ID查询指定的业务日志(包含业务日志和外键名称)
     *
     * @param businessLogId Id
     */
    @PreAuthorize("hasAuthority('erp:system:BusinessLog:view')")
    @ApiOperation("根据ID查询指定的业务日志(包含业务日志和外键名称)")
    @PostMapping("/findBusinessLogForEdit")
    public BusinessLogEditDto findBusinessLogForEdit(@RequestParam Long businessLogId) {
        BusinessLogEditDto businessLogEditDto = new BusinessLogEditDto();
        businessLogEditDto.setBusinessLog(this.businessLogService.findBusinessLogWithForeignName(businessLogId));

        this.prepareBusinessLogEditDto(businessLogEditDto);

        return businessLogEditDto;
    }

    /**
     * 根据ID查询指定的业务日志(只提取ID 和 Name)
     *
     * @param businessLogId Id
     */
    @PreAuthorize("hasAuthority('erp:system:BusinessLog:view')")
    @ApiOperation("根据ID查询指定的业务日志(只提取ID 和 Name)")
    @PostMapping("/findBusinessLogsWithIdNameById")
    public BusinessLog findBusinessLogsWithIdNameById(@RequestParam Long businessLogId) {
        return this.businessLogService.findBusinessLogsWithIdNameById(businessLogId);
    }

    /**
     * 根据名称查询业务日志集合(只提取ID 和 Name)
     *
     * @param businessLogName 名称
     */
    @PreAuthorize("hasAuthority('erp:system:BusinessLog:view')")
    @ApiOperation("根据名称查询业务日志集合(只提取ID 和 Name)")
    @PostMapping("/findBusinessLogsWithIdNameByName")
    public List<BusinessLog> findBusinessLogsWithIdNameByName(@RequestParam String businessLogName) {
        return this.businessLogService.findBusinessLogsWithIdNameByName(businessLogName);
    }

    /**
     * 创建新的业务日志
     */
    @PreAuthorize("hasAuthority('erp:system:BusinessLog:add')")
    @ApiOperation("创建新的业务日志")
    @PostMapping("/createBusinessLog")
    public BusinessLogEditDto createBusinessLog() {
        BusinessLogEditDto businessLogEditDto = new BusinessLogEditDto();
        businessLogEditDto.setBusinessLog(new BusinessLog());

        this.prepareBusinessLogEditDto(businessLogEditDto);
        return businessLogEditDto;
    }

    private void prepareBusinessLogEditDto(BusinessLogEditDto businessLogEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        businessLogEditDto.setUserUsers(this.userService.findAllUsersWithIdName());
    }

    /**
     * 新增保存业务日志
     *
     * @param businessLog 实体对象
     */
    @PreAuthorize("hasAuthority('erp:system:BusinessLog:add')")
    @ApiOperation("新增保存业务日志")
    @PostMapping("/saveBusinessLog")
    public BusinessLog saveBusinessLog(@RequestBody BusinessLog businessLog) {
        return this.businessLogService.saveBusinessLog(businessLog);
    }

    /**
     * 修改保存业务日志
     *
     * @param businessLog 实体对象
     */
    @PreAuthorize("hasAuthority('erp:system:BusinessLog:update')")
    @ApiOperation("修改保存业务日志")
    @PostMapping("/updateBusinessLog")
    public BusinessLog updateBusinessLog(@RequestBody BusinessLog businessLog) {
        return this.businessLogService.updateBusinessLog(businessLog);
    }

    /**
     * 根据ID删除业务日志
     *
     * @param businessLogId ID
     */
    @PreAuthorize("hasAuthority('erp:system:BusinessLog:remove')")
    @ApiOperation("根据ID删除业务日志")
    @PostMapping("/deleteBusinessLog")
    public void deleteBusinessLog(@RequestParam Long businessLogId) {
        this.businessLogService.deleteBusinessLog(businessLogId);
    }


}

