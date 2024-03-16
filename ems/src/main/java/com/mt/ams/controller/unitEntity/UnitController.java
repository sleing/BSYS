

package com.mt.ams.controller.unitEntity;

import com.mt.ams.dto.unitEntity.UnitEditDto;
import com.mt.ams.entity.unitEntity.Unit;
import com.mt.ams.service.unitEntity.UnitService;
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


@Api(tags = "举办单位信息")
@RestController
@RequestMapping("/api/ams/unitEntity/Unit")
@CrossOrigin(allowCredentials = "true")
public class UnitController {
    private static Logger logger = LoggerFactory.getLogger(UnitController.class);


    @Autowired
    private UnitService unitService;

    /**
     * 根据分页参数查询举办单位信息集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:unitEntity:Unit:view')")
    @ApiOperation("分页查询举办单位信息")
    @ApiPageParam
    @PostMapping("/findUnits")
    public PageResultDTO findUnits(@RequestBody PageDTO pageDTO) {
        return this.unitService.findUnits(pageDTO);
    }

    /**
     * 根据ID查询指定的举办单位信息
     *
     * @param unitId Id
     */
    @PreAuthorize("hasAuthority('ams:unitEntity:Unit:view')")
    @ApiOperation("根据id查询举办单位信息")
    @ApiPageParam
    @PostMapping("/findUnit")
    public Unit findUnit(@RequestParam Long unitId) {
        return this.unitService.findUnit(unitId);
    }

    /**
     * 根据ID查询指定的举办单位信息(包含外键名称)
     *
     * @param unitId Id
     */
    @PreAuthorize("hasAuthority('ams:unitEntity:Unit:view')")
    @ApiOperation("根据ID查询指定的举办单位信息(包含外键名称)")
    @PostMapping("/findUnitForView")
    public Unit findUnitForView(@RequestParam Long unitId) {
        return this.unitService.findUnitWithForeignName(unitId);
    }

    /**
     * 根据ID查询指定的举办单位信息(包含举办单位信息和外键名称)
     *
     * @param unitId Id
     */
    @PreAuthorize("hasAuthority('ams:unitEntity:Unit:view')")
    @ApiOperation("根据ID查询指定的举办单位信息(包含举办单位信息和外键名称)")
    @PostMapping("/findUnitForEdit")
    public UnitEditDto findUnitForEdit(@RequestParam Long unitId) {
        UnitEditDto unitEditDto = new UnitEditDto();
        unitEditDto.setUnit(this.unitService.findUnitWithForeignName(unitId));

        this.prepareUnitEditDto(unitEditDto);

        return unitEditDto;
    }

    /**
     * 根据ID查询指定的举办单位信息(只提取ID 和 Name)
     *
     * @param unitId Id
     */
    @PreAuthorize("hasAuthority('ams:unitEntity:Unit:view')")
    @ApiOperation("根据ID查询指定的举办单位信息(只提取ID 和 Name)")
    @PostMapping("/findUnitsWithIdNameById")
    public Unit findUnitsWithIdNameById(@RequestParam Long unitId) {
        return this.unitService.findUnitsWithIdNameById(unitId);
    }

    /**
     * 根据名称查询举办单位信息集合(只提取ID 和 Name)
     *
     * @param unitName 名称
     */
    @PreAuthorize("hasAuthority('ams:unitEntity:Unit:view')")
    @ApiOperation("根据名称查询举办单位信息集合(只提取ID 和 Name)")
    @PostMapping("/findUnitsWithIdNameByName")
    public List<Unit> findUnitsWithIdNameByName(@RequestParam String unitName) {
        return this.unitService.findUnitsWithIdNameByName(unitName);
    }


    /**
     * 创建新的举办单位信息
     */
    @PreAuthorize("hasAuthority('ams:unitEntity:Unit:add')")
    @ApiOperation("创建新的举办单位信息")
    @PostMapping("/createUnit")
    public UnitEditDto createUnit() {
        UnitEditDto unitEditDto = new UnitEditDto();
        unitEditDto.setUnit(new Unit());

        this.prepareUnitEditDto(unitEditDto);
        return unitEditDto;
    }

    private void prepareUnitEditDto(UnitEditDto unitEditDto) {
    }

    /**
     * 新增保存举办单位信息
     *
     * @param unit 实体对象
     */
    @PreAuthorize("hasAuthority('ams:unitEntity:Unit:add')")
    @ApiOperation("新增保存举办单位信息")
    @PostMapping("/saveUnit")
    public Unit saveUnit(@RequestBody Unit unit) {
        return this.unitService.saveUnit(unit);
    }

    /**
     * 修改保存举办单位信息
     *
     * @param unit 实体对象
     */
    @PreAuthorize("hasAuthority('ams:unitEntity:Unit:update')")
    @ApiOperation("修改保存举办单位信息")
    @PostMapping("/updateUnit")
    public Unit updateUnit(@RequestBody Unit unit) {
        return this.unitService.updateUnit(unit);
    }

    /**
     * 根据ID删除举办单位信息
     *
     * @param unitId ID
     */
    @PreAuthorize("hasAuthority('ams:unitEntity:Unit:remove')")
    @ApiOperation("根据ID删除举办单位信息")
    @PostMapping("/deleteUnit")
    public void deleteUnit(@RequestParam Long unitId) {
        this.unitService.deleteUnit(unitId);
    }


    @PreAuthorize("hasAuthority('ams:unitEntity:Unit:update')")
    @ApiOperation("根据ID删除举办单位信息")
    @PostMapping("/getUnitName")
    public Boolean getUnitName(@RequestParam String name) {
        return this.unitService.getUnitName(name);
    }

}

