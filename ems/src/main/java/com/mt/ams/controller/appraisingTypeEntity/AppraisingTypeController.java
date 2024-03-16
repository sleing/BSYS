

package com.mt.ams.controller.appraisingTypeEntity;

import com.mt.ams.dto.appraisingTypeEntity.AppraisingTypeEditDto;
import com.mt.ams.entity.appraisingTypeEntity.AppraisingType;
import com.mt.ams.service.appraisingTypeEntity.AppraisingTypeService;
import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "学生处评优评先类别信息")
@RestController
@RequestMapping("/api/ams/appraisingTypeEntity/AppraisingType")
@CrossOrigin(allowCredentials = "true")
public class AppraisingTypeController {
    private static Logger logger = LoggerFactory.getLogger(AppraisingTypeController.class);


    @Autowired
    private AppraisingTypeService appraisingTypeService;

    /**
     * 根据分页参数查询学生处评优评先类别信息集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:appraisingTypeEntity:AppraisingType:view')")
    @ApiOperation("分页查询学生处评优评先类别信息")
    @ApiPageParam
    @PostMapping("/findAppraisingTypes")
    public PageResultDTO findAppraisingTypes(@RequestBody PageDTO pageDTO) {
        return this.appraisingTypeService.findAppraisingTypes(pageDTO);
    }

    /**
     * 根据ID查询指定的学生处评优评先类别信息
     *
     * @param appraisingTypeId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisingTypeEntity:AppraisingType:view')")
    @ApiOperation("根据id查询学生处评优评先类别信息")
    @ApiPageParam
    @PostMapping("/findAppraisingType")
    public AppraisingType findAppraisingType(@RequestParam Long appraisingTypeId) {
        return this.appraisingTypeService.findAppraisingType(appraisingTypeId);
    }

    /**
     * 根据ID查询指定的学生处评优评先类别信息(包含外键名称)
     *
     * @param appraisingTypeId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisingTypeEntity:AppraisingType:view')")
    @ApiOperation("根据ID查询指定的学生处评优评先类别信息(包含外键名称)")
    @PostMapping("/findAppraisingTypeForView")
    public AppraisingType findAppraisingTypeForView(@RequestParam Long appraisingTypeId) {
        return this.appraisingTypeService.findAppraisingTypeWithForeignName(appraisingTypeId);
    }

    /**
     * 根据ID查询指定的学生处评优评先类别信息(包含学生处评优评先类别信息和外键名称)
     *
     * @param appraisingTypeId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisingTypeEntity:AppraisingType:view')")
    @ApiOperation("根据ID查询指定的学生处评优评先类别信息(包含学生处评优评先类别信息和外键名称)")
    @PostMapping("/findAppraisingTypeForEdit")
    public AppraisingTypeEditDto findAppraisingTypeForEdit(@RequestParam Long appraisingTypeId) {
        AppraisingTypeEditDto appraisingTypeEditDto = new AppraisingTypeEditDto();
        appraisingTypeEditDto.setAppraisingType(this.appraisingTypeService.findAppraisingTypeWithForeignName(appraisingTypeId));

        this.prepareAppraisingTypeEditDto(appraisingTypeEditDto);

        return appraisingTypeEditDto;
    }

    /**
     * 根据ID查询指定的学生处评优评先类别信息(只提取ID 和 Name)
     *
     * @param appraisingTypeId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisingTypeEntity:AppraisingType:view')")
    @ApiOperation("根据ID查询指定的学生处评优评先类别信息(只提取ID 和 Name)")
    @PostMapping("/findAppraisingTypesWithIdNameById")
    public AppraisingType findAppraisingTypesWithIdNameById(@RequestParam Long appraisingTypeId) {
        return this.appraisingTypeService.findAppraisingTypesWithIdNameById(appraisingTypeId);
    }

    /**
     * 根据名称查询学生处评优评先类别信息集合(只提取ID 和 Name)
     *
     * @param appraisingTypeName 名称
     */
    @PreAuthorize("hasAuthority('ams:appraisingTypeEntity:AppraisingType:view')")
    @ApiOperation("根据名称查询学生处评优评先类别信息集合(只提取ID 和 Name)")
    @PostMapping("/findAppraisingTypesWithIdNameByName")
    public List<AppraisingType> findAppraisingTypesWithIdNameByName(@RequestParam String appraisingTypeName) {
        return this.appraisingTypeService.findAppraisingTypesWithIdNameByName(appraisingTypeName);
    }


    /**
     * 创建新的学生处评优评先类别信息
     */
    @PreAuthorize("hasAuthority('ams:appraisingTypeEntity:AppraisingType:add')")
    @ApiOperation("创建新的学生处评优评先类别信息")
    @PostMapping("/createAppraisingType")
    public AppraisingTypeEditDto createAppraisingType() {
        AppraisingTypeEditDto appraisingTypeEditDto = new AppraisingTypeEditDto();
        appraisingTypeEditDto.setAppraisingType(new AppraisingType());

        this.prepareAppraisingTypeEditDto(appraisingTypeEditDto);
        return appraisingTypeEditDto;
    }

    private void prepareAppraisingTypeEditDto(AppraisingTypeEditDto appraisingTypeEditDto) {
    }

    /**
     * 新增保存学生处评优评先类别信息
     *
     * @param appraisingType 实体对象
     */
    @PreAuthorize("hasAuthority('ams:appraisingTypeEntity:AppraisingType:add')")
    @ApiOperation("新增保存学生处评优评先类别信息")
    @PostMapping("/saveAppraisingType")
    public AppraisingType saveAppraisingType(@RequestBody AppraisingType appraisingType) {
        return this.appraisingTypeService.saveAppraisingType(appraisingType);
    }

    /**
     * 修改保存学生处评优评先类别信息
     *
     * @param appraisingType 实体对象
     */
    @PreAuthorize("hasAuthority('ams:appraisingTypeEntity:AppraisingType:update')")
    @ApiOperation("修改保存学生处评优评先类别信息")
    @PostMapping("/updateAppraisingType")
    public AppraisingType updateAppraisingType(@RequestBody AppraisingType appraisingType) {
        return this.appraisingTypeService.updateAppraisingType(appraisingType);
    }

    /**
     * 根据ID删除学生处评优评先类别信息
     *
     * @param appraisingTypeId ID
     */
    @PreAuthorize("hasAuthority('ams:appraisingTypeEntity:AppraisingType:remove')")
    @ApiOperation("根据ID删除学生处评优评先类别信息")
    @PostMapping("/deleteAppraisingType")
    public void deleteAppraisingType(@RequestParam Long appraisingTypeId) {
        this.appraisingTypeService.deleteAppraisingType(appraisingTypeId);
    }


}

