

package com.mt.ams.controller.collegeEntity;

import com.mt.ams.dto.collegeEntity.CollegeEditDto;
import com.mt.ams.entity.collegeEntity.College;
import com.mt.ams.service.collegeEntity.CollegeService;
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


@Api(tags = "学院信息")
@RestController
@RequestMapping("/api/ams/collegeEntity/College")
@CrossOrigin(allowCredentials = "true")
public class CollegeController {
    private static Logger logger = LoggerFactory.getLogger(CollegeController.class);


    @Autowired
    private CollegeService collegeService;

    /**
     * 根据分页参数查询学院信息集合
     *
     * @param pageDTO 分页条件
     */
    //@PreAuthorize("hasAuthority('ams:collegeEntity:College:view')")
    @ApiOperation("分页查询学院信息")
    @ApiPageParam
    @PostMapping("/findColleges")
    public PageResultDTO findColleges(@RequestBody PageDTO pageDTO) {
        return this.collegeService.findColleges(pageDTO);
    }

    /**
     * 根据ID查询指定的学院信息
     *
     * @param collegeId Id
     */
    @PreAuthorize("hasAuthority('ams:collegeEntity:College:view')")
    @ApiOperation("根据id查询学院信息")
    @ApiPageParam
    @PostMapping("/findCollege")
    public College findCollege(@RequestParam Long collegeId) {
        return this.collegeService.findCollege(collegeId);
    }

    /**
     * 根据ID查询指定的学院信息(包含外键名称)
     *
     * @param collegeId Id
     */
    @PreAuthorize("hasAuthority('ams:collegeEntity:College:view')")
    @ApiOperation("根据ID查询指定的学院信息(包含外键名称)")
    @PostMapping("/findCollegeForView")
    public College findCollegeForView(@RequestParam Long collegeId) {
        return this.collegeService.findCollegeWithForeignName(collegeId);
    }

    /**
     * 根据ID查询指定的学院信息(包含学院信息和外键名称)
     *
     * @param collegeId Id
     */
    @PreAuthorize("hasAuthority('ams:collegeEntity:College:view')")
    @ApiOperation("根据ID查询指定的学院信息(包含学院信息和外键名称)")
    @PostMapping("/findCollegeForEdit")
    public CollegeEditDto findCollegeForEdit(@RequestParam Long collegeId) {
        CollegeEditDto collegeEditDto = new CollegeEditDto();
        collegeEditDto.setCollege(this.collegeService.findCollegeWithForeignName(collegeId));

        this.prepareCollegeEditDto(collegeEditDto);

        return collegeEditDto;
    }

    /**
     * 根据ID查询指定的学院信息(只提取ID 和 Name)
     *
     * @param collegeId Id
     */
    @PreAuthorize("hasAuthority('ams:collegeEntity:College:view')")
    @ApiOperation("根据ID查询指定的学院信息(只提取ID 和 Name)")
    @PostMapping("/findCollegesWithIdNameById")
    public College findCollegesWithIdNameById(@RequestParam Long collegeId) {
        return this.collegeService.findCollegesWithIdNameById(collegeId);
    }

    /**
     * 根据名称查询学院信息集合(只提取ID 和 Name)
     *
     * @param collegeName 名称
     */
    @PreAuthorize("hasAuthority('ams:collegeEntity:College:view')")
    @ApiOperation("根据名称查询学院信息集合(只提取ID 和 Name)")
    @PostMapping("/findCollegesWithIdNameByName")
    public List<College> findCollegesWithIdNameByName(@RequestParam String collegeName) {
        return this.collegeService.findCollegesWithIdNameByName(collegeName);
    }


    /**
     * 创建新的学院信息
     */
    @PreAuthorize("hasAuthority('ams:collegeEntity:College:add')")
    @ApiOperation("创建新的学院信息")
    @PostMapping("/createCollege")
    public CollegeEditDto createCollege() {
        CollegeEditDto collegeEditDto = new CollegeEditDto();
        collegeEditDto.setCollege(new College());

        this.prepareCollegeEditDto(collegeEditDto);
        return collegeEditDto;
    }

    private void prepareCollegeEditDto(CollegeEditDto collegeEditDto) {
    }

    /**
     * 新增保存学院信息
     *
     * @param college 实体对象
     */
    @PreAuthorize("hasAuthority('ams:collegeEntity:College:add')")
    @ApiOperation("新增保存学院信息")
    @PostMapping("/saveCollege")
    public College saveCollege(@RequestBody College college) {
        return this.collegeService.saveCollege(college);
    }

    /**
     * 修改保存学院信息
     *
     * @param college 实体对象
     */
    @PreAuthorize("hasAuthority('ams:collegeEntity:College:update')")
    @ApiOperation("修改保存学院信息")
    @PostMapping("/updateCollege")
    public College updateCollege(@RequestBody College college) {
        return this.collegeService.updateCollege(college);
    }

    /**
     * 根据ID删除学院信息
     *
     * @param collegeId ID
     */
    @PreAuthorize("hasAuthority('ams:collegeEntity:College:remove')")
    @ApiOperation("根据ID删除学院信息")
    @PostMapping("/deleteCollege")
    public void deleteCollege(@RequestParam Long collegeId) {
        this.collegeService.deleteCollege(collegeId);
    }


}

