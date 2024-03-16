

package com.mt.ams.controller.teacherEntity;

import com.mt.ams.dto.teacherEntity.TeacherInfoEditDto;
import com.mt.ams.entity.teacherEntity.TeacherInfo;
import com.mt.ams.service.collegeEntity.CollegeService;
import com.mt.ams.service.teacherEntity.TeacherInfoService;
import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.annotation.OperLog;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Api(tags = "老师信息")
@RestController
@RequestMapping("/api/ams/teacherEntity/TeacherInfo")
@CrossOrigin(allowCredentials = "true")
public class TeacherInfoController {
    private static Logger logger = LoggerFactory.getLogger(TeacherInfoController.class);


    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private CollegeService collegeService;

    /**
     * 根据分页参数查询老师信息集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:view')")
    @ApiOperation("分页查询老师信息")
    @ApiPageParam
    @PostMapping("/findTeacherInfos")
    public PageResultDTO findTeacherInfos(@RequestBody PageDTO pageDTO) {
        return this.teacherInfoService.findTeacherInfos(pageDTO);
    }

    /**
     * 根据ID查询指定的老师信息
     *
     * @param teacherInfoId Id
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:view')")
    @ApiOperation("根据id查询老师信息")
    @ApiPageParam
    @PostMapping("/findTeacherInfo")
    public TeacherInfo findTeacherInfo(@RequestParam Long teacherInfoId) {
        return this.teacherInfoService.findTeacherInfo(teacherInfoId);
    }

    /**
     * 根据ID查询指定的老师信息(包含外键名称)
     *
     * @param teacherInfoId Id
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:view')")
    @ApiOperation("根据ID查询指定的老师信息(包含外键名称)")
    @PostMapping("/findTeacherInfoForView")
    public TeacherInfo findTeacherInfoForView(@RequestParam Long teacherInfoId) {
        return this.teacherInfoService.findTeacherInfoWithForeignName(teacherInfoId);
    }

    /**
     * 根据ID查询指定的老师信息(包含老师信息和外键名称)
     *
     * @param teacherInfoId Id
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:view')")
    @ApiOperation("根据ID查询指定的老师信息(包含老师信息和外键名称)")
    @PostMapping("/findTeacherInfoForEdit")
    public TeacherInfoEditDto findTeacherInfoForEdit(@RequestParam Long teacherInfoId) {
        TeacherInfoEditDto teacherInfoEditDto = new TeacherInfoEditDto();
        teacherInfoEditDto.setTeacherInfo(this.teacherInfoService.findTeacherInfoWithForeignName(teacherInfoId));

        this.prepareTeacherInfoEditDto(teacherInfoEditDto);

        return teacherInfoEditDto;
    }

    /**
     * 根据ID查询指定的老师信息(只提取ID 和 Name)
     *
     * @param teacherInfoId Id
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:view')")
    @ApiOperation("根据ID查询指定的老师信息(只提取ID 和 Name)")
    @PostMapping("/findTeacherInfosWithIdNameById")
    public TeacherInfo findTeacherInfosWithIdNameById(@RequestParam Long teacherInfoId) {
        return this.teacherInfoService.findTeacherInfosWithIdNameById(teacherInfoId);
    }

    /**
     * 根据名称查询老师信息集合(只提取ID 和 Name)
     *
     * @param teacherInfoName 名称
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:view')")
    @ApiOperation("根据名称查询老师信息集合(只提取ID 和 Name)")
    @PostMapping("/findTeacherInfosWithIdNameByName")
    public List<TeacherInfo> findTeacherInfosWithIdNameByName(@RequestParam String teacherInfoName) {
        return this.teacherInfoService.findTeacherInfosWithIdNameByName(teacherInfoName);
    }


    /**
     * 创建新的老师信息
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:add')")
    @ApiOperation("创建新的老师信息")
    @PostMapping("/createTeacherInfo")
    public TeacherInfoEditDto createTeacherInfo() {
        TeacherInfoEditDto teacherInfoEditDto = new TeacherInfoEditDto();
        teacherInfoEditDto.setTeacherInfo(new TeacherInfo());

        this.prepareTeacherInfoEditDto(teacherInfoEditDto);
        return teacherInfoEditDto;
    }

    private void prepareTeacherInfoEditDto(TeacherInfoEditDto teacherInfoEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        teacherInfoEditDto.setCollegeColleges(this.collegeService.findAllCollegesWithIdName());
    }

    /**
     * 新增保存老师信息
     *
     * @param teacherInfo 实体对象
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:add')")
    @ApiOperation("新增保存老师信息")
    @PostMapping("/saveTeacherInfo")
    public TeacherInfo saveTeacherInfo(@RequestBody TeacherInfo teacherInfo) {
        return this.teacherInfoService.saveTeacherInfo(teacherInfo);
    }

    /**
     * 修改保存老师信息
     *
     * @param teacherInfo 实体对象
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:update')")
    @ApiOperation("修改保存老师信息")
    @PostMapping("/updateTeacherInfo")
    public TeacherInfo updateTeacherInfo(@RequestBody TeacherInfo teacherInfo) {
        return this.teacherInfoService.updateTeacherInfo(teacherInfo);
    }

    /**
     * 根据ID删除老师信息
     *
     * @param teacherInfoId ID
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:remove')")
    @ApiOperation("根据ID删除老师信息")
    @PostMapping("/deleteTeacherInfo")
    public void deleteTeacherInfo(@RequestParam Long teacherInfoId) {
        this.teacherInfoService.deleteTeacherInfo(teacherInfoId);
    }

    /**
     * 根据工号查询老师信息
     *
     * @param teacherInfoId Id
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:view')")
    @ApiOperation("根据学号查询学生信息")
    @PostMapping("/findTeacherInfosById")
    public List<TeacherInfo> findTeacherInfosById(@RequestParam String teacherInfoId) {
        return this.teacherInfoService.findTeacherInfosById(teacherInfoId);
    }

    /**
     * 根据excel导入老师信息
     *
     * @param teacherInfos
     */
    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:view')")
    @ApiOperation("导入老师信息")
    @PostMapping("/improtTeacherInfos")
    public int  findTeacherInfosById(@RequestParam("teacherInfos") List<TeacherInfo> teacherInfos) {
     //to do
        return teacherInfos.size();
    }


    /**
     * 根据excel导入老师信息
     *
     * @param uploadTeacherInfos
     */
    @PreAuthorize("hasAuthority('sys:file:upload')")
    @OperLog(value = "文件管理", desc = "上传文件", param = false, result = true)
    @ApiOperation("上传文件")
    @PostMapping("/uploadTeacherInfos")
    public int uploadTeacherInfos(@RequestParam("file") MultipartFile uploadTeacherInfos) {
        return this.teacherInfoService.uploadTeacherInfos(uploadTeacherInfos);
    }

    @PreAuthorize("hasAuthority('ams:teacherEntity:TeacherInfo:view')")
    @ApiOperation("根据id查询老师信息")
    @PostMapping("/getTeacherEid")
    public Boolean getTeacherEid(@RequestParam String teacherId) {
        return this.teacherInfoService.getTeacherEid(teacherId);
    }


}

