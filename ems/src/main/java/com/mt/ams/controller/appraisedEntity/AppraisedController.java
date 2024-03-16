

package com.mt.ams.controller.appraisedEntity;

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


import com.mt.ams.dto.appraisedEntity.AppraisedEditDto;
import com.mt.ams.entity.appraisedEntity.Appraised;
import com.mt.ams.service.appraisedEntity.AppraisedService;
import com.mt.ams.service.studentEntity.StudentInfoService;
import com.mt.ams.service.appraisingEntity.AppraisingService;


@Api(tags = "学生处评优评先关联")
@RestController
@RequestMapping("/api/ams/appraisedEntity/Appraised")
@CrossOrigin(allowCredentials = "true")
public class AppraisedController {
    private static Logger logger = LoggerFactory.getLogger(AppraisedController.class);


    @Autowired
    private AppraisedService appraisedService;
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private AppraisingService appraisingService;

    /**
     * 根据分页参数查询学生处评优评先关联集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:appraisedEntity:Appraised:view')")
    @ApiOperation("分页查询学生处评优评先关联")
    @ApiPageParam
    @PostMapping("/findAppraiseds")
    public PageResultDTO findAppraiseds(@RequestBody PageDTO pageDTO) {
        return this.appraisedService.findAppraiseds(pageDTO);
    }

    /**
     * 根据ID查询指定的学生处评优评先关联
     *
     * @param appraisedId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisedEntity:Appraised:view')")
    @ApiOperation("根据id查询学生处评优评先关联")
    @ApiPageParam
    @PostMapping("/findAppraised")
    public Appraised findAppraised(@RequestParam Long appraisedId) {
        return this.appraisedService.findAppraised(appraisedId);
    }

    /**
     * 根据ID查询指定的学生处评优评先关联(包含外键名称)
     *
     * @param appraisedId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisedEntity:Appraised:view')")
    @ApiOperation("根据ID查询指定的学生处评优评先关联(包含外键名称)")
    @PostMapping("/findAppraisedForView")
    public Appraised findAppraisedForView(@RequestParam Long appraisedId) {
        return this.appraisedService.findAppraisedWithForeignName(appraisedId);
    }

    /**
     * 根据ID查询指定的学生处评优评先关联(包含学生处评优评先关联和外键名称)
     *
     * @param appraisedId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisedEntity:Appraised:view')")
    @ApiOperation("根据ID查询指定的学生处评优评先关联(包含学生处评优评先关联和外键名称)")
    @PostMapping("/findAppraisedForEdit")
    public AppraisedEditDto findAppraisedForEdit(@RequestParam Long appraisedId) {
        AppraisedEditDto appraisedEditDto = new AppraisedEditDto();
        appraisedEditDto.setAppraised(this.appraisedService.findAppraisedWithForeignName(appraisedId));

        this.prepareAppraisedEditDto(appraisedEditDto);

        return appraisedEditDto;
    }

    /**
     * 根据ID查询指定的学生处评优评先关联(只提取ID 和 Name)
     *
     * @param appraisedId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisedEntity:Appraised:view')")
    @ApiOperation("根据ID查询指定的学生处评优评先关联(只提取ID 和 Name)")
    @PostMapping("/findAppraisedsWithIdNameById")
    public Appraised findAppraisedsWithIdNameById(@RequestParam Long appraisedId) {
        return this.appraisedService.findAppraisedsWithIdNameById(appraisedId);
    }

    /**
     * 根据名称查询学生处评优评先关联集合(只提取ID 和 Name)
     *
     * @param appraisedName 名称
     */
    @PreAuthorize("hasAuthority('ams:appraisedEntity:Appraised:view')")
    @ApiOperation("根据名称查询学生处评优评先关联集合(只提取ID 和 Name)")
    @PostMapping("/findAppraisedsWithIdNameByName")
    public List<Appraised> findAppraisedsWithIdNameByName(@RequestParam String appraisedName) {
        return this.appraisedService.findAppraisedsWithIdNameByName(appraisedName);
    }


    /**
     * 创建新的学生处评优评先关联
     */
    @PreAuthorize("hasAuthority('ams:appraisedEntity:Appraised:add')")
    @ApiOperation("创建新的学生处评优评先关联")
    @PostMapping("/createAppraised")
    public AppraisedEditDto createAppraised() {
        AppraisedEditDto appraisedEditDto = new AppraisedEditDto();
        appraisedEditDto.setAppraised(new Appraised());

        this.prepareAppraisedEditDto(appraisedEditDto);
        return appraisedEditDto;
    }

    private void prepareAppraisedEditDto(AppraisedEditDto appraisedEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        appraisedEditDto.setStudentStudentInfos(this.studentInfoService.findAllStudentInfosWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        appraisedEditDto.setAppraisingAppraisings(this.appraisingService.findAllAppraisingsWithIdName());
    }

    /**
     * 新增保存学生处评优评先关联
     *
     * @param appraised 实体对象
     */
    @PreAuthorize("hasAuthority('ams:appraisedEntity:Appraised:add')")
    @ApiOperation("新增保存学生处评优评先关联")
    @PostMapping("/saveAppraised")
    public Appraised saveAppraised(@RequestBody Appraised appraised) {
        return this.appraisedService.saveAppraised(appraised);
    }

    /**
     * 修改保存学生处评优评先关联
     *
     * @param appraised 实体对象
     */
    @PreAuthorize("hasAuthority('ams:appraisedEntity:Appraised:update')")
    @ApiOperation("修改保存学生处评优评先关联")
    @PostMapping("/updateAppraised")
    public Appraised updateAppraised(@RequestBody Appraised appraised) {
        return this.appraisedService.updateAppraised(appraised);
    }

    /**
     * 根据ID删除学生处评优评先关联
     *
     * @param appraisedId ID
     */
    @PreAuthorize("hasAuthority('ams:appraisedEntity:Appraised:remove')")
    @ApiOperation("根据ID删除学生处评优评先关联")
    @PostMapping("/deleteAppraised")
    public void deleteAppraised(@RequestParam Long appraisedId) {
        this.appraisedService.deleteAppraised(appraisedId);
    }


}

