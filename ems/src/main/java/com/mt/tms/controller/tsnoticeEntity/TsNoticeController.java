

package com.mt.tms.controller.tsnoticeEntity;

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


import com.mt.tms.dto.tsnoticeEntity.TsNoticeEditDto;
import com.mt.tms.entity.tsnoticeEntity.TsNotice;
import com.mt.tms.service.tsnoticeEntity.TsNoticeService;
import com.mt.tms.service.tsdepartmentEntity.TsDepartmentService;


@Api(tags = "通知管理")
@RestController
@RequestMapping("/api/tms/tsnoticeEntity/TsNotice")
@CrossOrigin(allowCredentials = "true")
public class TsNoticeController {
    private static Logger logger = LoggerFactory.getLogger(TsNoticeController.class);


    @Autowired
    private TsNoticeService tsNoticeService;
    @Autowired
    private TsDepartmentService tsDepartmentService;

    /**
     * 根据分页参数查询通知管理集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('tms:tsnoticeEntity:TsNotice:view')")
    @ApiOperation("分页查询通知管理")
    @ApiPageParam
    @PostMapping("/findTsNotices")
    public PageResultDTO findTsNotices(@RequestBody PageDTO pageDTO) {
        return this.tsNoticeService.findTsNotices(pageDTO);
    }

    /**
     * 根据ID查询指定的通知管理
     *
     * @param tsNoticeId Id
     */
    @PreAuthorize("hasAuthority('tms:tsnoticeEntity:TsNotice:view')")
    @ApiOperation("根据id查询通知管理")
    @ApiPageParam
    @PostMapping("/findTsNotice")
    public TsNotice findTsNotice(@RequestParam Long tsNoticeId) {
        return this.tsNoticeService.findTsNotice(tsNoticeId);
    }

    /**
     * 根据ID查询指定的通知管理(包含外键名称)
     *
     * @param tsNoticeId Id
     */
    @PreAuthorize("hasAuthority('tms:tsnoticeEntity:TsNotice:view')")
    @ApiOperation("根据ID查询指定的通知管理(包含外键名称)")
    @PostMapping("/findTsNoticeForView")
    public TsNotice findTsNoticeForView(@RequestParam Long tsNoticeId) {
        return this.tsNoticeService.findTsNoticeWithForeignName(tsNoticeId);
    }

    /**
     * 根据ID查询指定的通知管理(包含通知管理和外键名称)
     *
     * @param tsNoticeId Id
     */
    @PreAuthorize("hasAuthority('tms:tsnoticeEntity:TsNotice:view')")
    @ApiOperation("根据ID查询指定的通知管理(包含通知管理和外键名称)")
    @PostMapping("/findTsNoticeForEdit")
    public TsNoticeEditDto findTsNoticeForEdit(@RequestParam Long tsNoticeId) {
        TsNoticeEditDto tsNoticeEditDto = new TsNoticeEditDto();
        tsNoticeEditDto.setTsNotice(this.tsNoticeService.findTsNoticeWithForeignName(tsNoticeId));

        this.prepareTsNoticeEditDto(tsNoticeEditDto);

        return tsNoticeEditDto;
    }

    /**
     * 根据ID查询指定的通知管理(只提取ID 和 Name)
     *
     * @param tsNoticeId Id
     */
    @PreAuthorize("hasAuthority('tms:tsnoticeEntity:TsNotice:view')")
    @ApiOperation("根据ID查询指定的通知管理(只提取ID 和 Name)")
    @PostMapping("/findTsNoticesWithIdNameById")
    public TsNotice findTsNoticesWithIdNameById(@RequestParam Long tsNoticeId) {
        return this.tsNoticeService.findTsNoticesWithIdNameById(tsNoticeId);
    }

    /**
     * 根据名称查询通知管理集合(只提取ID 和 Name)
     *
     * @param tsNoticeName 名称
     */
    @PreAuthorize("hasAuthority('tms:tsnoticeEntity:TsNotice:view')")
    @ApiOperation("根据名称查询通知管理集合(只提取ID 和 Name)")
    @PostMapping("/findTsNoticesWithIdNameByName")
    public List<TsNotice> findTsNoticesWithIdNameByName(@RequestParam String tsNoticeName) {
        return this.tsNoticeService.findTsNoticesWithIdNameByName(tsNoticeName);
    }


    /**
     * 创建新的通知管理
     */
    @PreAuthorize("hasAuthority('tms:tsnoticeEntity:TsNotice:add')")
    @ApiOperation("创建新的通知管理")
    @PostMapping("/createTsNotice")
    public TsNoticeEditDto createTsNotice() {
        TsNoticeEditDto tsNoticeEditDto = new TsNoticeEditDto();
        tsNoticeEditDto.setTsNotice(new TsNotice());

        this.prepareTsNoticeEditDto(tsNoticeEditDto);
        return tsNoticeEditDto;
    }

    private void prepareTsNoticeEditDto(TsNoticeEditDto tsNoticeEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        tsNoticeEditDto.setNoticeDepartmentTsDepartments(this.tsDepartmentService.findAllTsDepartmentsWithIdName());
    }

    /**
     * 新增保存通知管理
     *
     * @param tsNotice 实体对象
     */
    @PreAuthorize("hasAuthority('tms:tsnoticeEntity:TsNotice:add')")
    @ApiOperation("新增保存通知管理")
    @PostMapping("/saveTsNotice")
    public TsNotice saveTsNotice(@RequestBody TsNotice tsNotice) {
        return this.tsNoticeService.saveTsNotice(tsNotice);
    }

    /**
     * 修改保存通知管理
     *
     * @param tsNotice 实体对象
     */
    @PreAuthorize("hasAuthority('tms:tsnoticeEntity:TsNotice:update')")
    @ApiOperation("修改保存通知管理")
    @PostMapping("/updateTsNotice")
    public TsNotice updateTsNotice(@RequestBody TsNotice tsNotice) {
        return this.tsNoticeService.updateTsNotice(tsNotice);
    }

    /**
     * 根据ID删除通知管理
     *
     * @param tsNoticeId ID
     */
    @PreAuthorize("hasAuthority('tms:tsnoticeEntity:TsNotice:remove')")
    @ApiOperation("根据ID删除通知管理")
    @PostMapping("/deleteTsNotice")
    public void deleteTsNotice(@RequestParam Long tsNoticeId) {
        this.tsNoticeService.deleteTsNotice(tsNoticeId);
    }


}

