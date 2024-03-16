

package com.mt.ams.controller.instructorEntity;

import com.mt.ams.dto.instructorEntity.InstructorEditDto;
import com.mt.ams.entity.instructorEntity.Instructor;
import com.mt.ams.service.awardEntity.AwardService;
import com.mt.ams.service.instructorEntity.InstructorService;
import com.mt.ams.service.teacherEntity.TeacherInfoService;
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


@Api(tags = "指导老师信息")
@RestController
@RequestMapping("/api/ams/instructorEntity/Instructor")
@CrossOrigin(allowCredentials = "true")
public class InstructorController {
    private static Logger logger = LoggerFactory.getLogger(InstructorController.class);


    @Autowired
    private InstructorService instructorService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private AwardService awardService;

    /**
     * 根据分页参数查询指导老师信息集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
    @ApiOperation("分页查询指导老师信息")
    @ApiPageParam
    @PostMapping("/findInstructors")
    public PageResultDTO findInstructors(@RequestBody PageDTO pageDTO) {
        return this.instructorService.findInstructors(pageDTO);
    }

    /**
     * 根据ID查询指定的指导老师信息
     *
     * @param instructorId Id
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
    @ApiOperation("根据id查询指导老师信息")
    @ApiPageParam
    @PostMapping("/findInstructor")
    public Instructor findInstructor(@RequestParam Long instructorId) {
        return this.instructorService.findInstructor(instructorId);
    }

    /**
     * 根据ID查询指定的指导老师信息(包含外键名称)
     *
     * @param instructorId Id
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
    @ApiOperation("根据ID查询指定的指导老师信息(包含外键名称)")
    @PostMapping("/findInstructorForView")
    public Instructor findInstructorForView(@RequestParam Long instructorId) {
        return this.instructorService.findInstructorWithForeignName(instructorId);
    }

    /**
     * 根据ID查询指定的指导老师信息(包含指导老师信息和外键名称)
     *
     * @param instructorId Id
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
    @ApiOperation("根据ID查询指定的指导老师信息(包含指导老师信息和外键名称)")
    @PostMapping("/findInstructorForEdit")
    public InstructorEditDto findInstructorForEdit(@RequestParam Long instructorId) {
        InstructorEditDto instructorEditDto = new InstructorEditDto();
        instructorEditDto.setInstructor(this.instructorService.findInstructorWithForeignName(instructorId));

        this.prepareInstructorEditDto(instructorEditDto);

        return instructorEditDto;
    }

    /**
     * 根据ID查询指定的指导老师信息(只提取ID 和 Name)
     *
     * @param instructorId Id
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
    @ApiOperation("根据ID查询指定的指导老师信息(只提取ID 和 Name)")
    @PostMapping("/findInstructorsWithIdNameById")
    public Instructor findInstructorsWithIdNameById(@RequestParam Long instructorId) {
        return this.instructorService.findInstructorsWithIdNameById(instructorId);
    }

    /**
     * 根据名称查询指导老师信息集合(只提取ID 和 Name)
     *
     * @param instructorName 名称
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:view')")
    @ApiOperation("根据名称查询指导老师信息集合(只提取ID 和 Name)")
    @PostMapping("/findInstructorsWithIdNameByName")
    public List<Instructor> findInstructorsWithIdNameByName(@RequestParam String instructorName) {
        return this.instructorService.findInstructorsWithIdNameByName(instructorName);
    }


    /**
     * 创建新的指导老师信息
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:add')")
    @ApiOperation("创建新的指导老师信息")
    @PostMapping("/createInstructor")
    public InstructorEditDto createInstructor() {
        InstructorEditDto instructorEditDto = new InstructorEditDto();
        instructorEditDto.setInstructor(new Instructor());

        this.prepareInstructorEditDto(instructorEditDto);
        return instructorEditDto;
    }

    private void prepareInstructorEditDto(InstructorEditDto instructorEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        instructorEditDto.setTeacherTeacherInfos(this.teacherInfoService.findAllTeacherInfosWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        instructorEditDto.setAwardAwards(this.awardService.findAllAwardsWithIdName());
    }

    /**
     * 新增保存指导老师信息
     *
     * @param instructor 实体对象
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:add')")
    @ApiOperation("新增保存指导老师信息")
    @PostMapping("/saveInstructor")
    public Instructor saveInstructor(@RequestBody Instructor instructor) {
        return this.instructorService.saveInstructor(instructor);
    }

    /**
     * 修改保存指导老师信息
     *
     * @param instructor 实体对象
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:update')")
    @ApiOperation("修改保存指导老师信息")
    @PostMapping("/updateInstructor")
    public Instructor updateInstructor(@RequestBody Instructor instructor) {
        return this.instructorService.updateInstructor(instructor);
    }

    /**
     * 根据ID删除指导老师信息
     *
     * @param instructorId ID
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:remove')")
    @ApiOperation("根据ID删除指导老师信息")
    @PostMapping("/deleteInstructor")
    public void deleteInstructor(@RequestParam Long instructorId) {
        this.instructorService.deleteInstructor(instructorId);
    }

    /**
     * 根据ID删除指导老师信息
     *
     * @param awardId ID
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:remove')")
    @ApiOperation("根据ID删除指导老师信息")
    @PostMapping("/deleteInstructorByAwardId")
    public void deleteInstructorByAwardId(@RequestParam Long awardId) {
        this.instructorService.deleteInstructorByAwardId(awardId);
    }

    /**
     * 根据awardId查询指导老师信息
     *
     * @param awardId ID
     */
    @PreAuthorize("hasAuthority('ams:instructorEntity:Instructor:update')")
    @ApiOperation("根据awardID查询指导老师信息")
    @PostMapping("/findInstructorByAwardId")
    public List<Instructor> findInstructorByAwardId(@RequestParam Long awardId) {
        return this.instructorService.findInstructorByAwardId(awardId);
    }

}

