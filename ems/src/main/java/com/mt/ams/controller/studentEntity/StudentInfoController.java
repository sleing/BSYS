
package com.mt.ams.controller.studentEntity;

import com.mt.ams.dto.studentEntity.StudentInfoEditDto;
import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.ams.service.collegeEntity.CollegeService;
import com.mt.ams.service.studentEntity.StudentInfoService;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@Api(tags = "学生信息")
@RestController
@RequestMapping("/api/ams/studentEntity/StudentInfo")
@CrossOrigin(allowCredentials = "true")
public class StudentInfoController {
    private static Logger logger = LoggerFactory.getLogger(StudentInfoController.class);


    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private CollegeService collegeService;

    private HashMap<String, String> cache = new HashMap<String, String>();

    /**
     * 根据分页参数查询学生信息集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:studentEntity:StudentInfo:view')")
    @ApiOperation("分页查询学生信息")
    @ApiPageParam
    @PostMapping("/findStudentInfos")
    public PageResultDTO findStudentInfos(@RequestBody PageDTO pageDTO) {
        return this.studentInfoService.findStudentInfos(pageDTO);
    }

    /**
     * 根据ID查询指定的学生信息
     *
     * @param studentInfoId Id
     */
    @PreAuthorize("hasAuthority('ams:studentEntity:StudentInfo:view')")
    @ApiOperation("根据id查询学生信息")
    @ApiPageParam
    @PostMapping("/findStudentInfo")
    public StudentInfo findStudentInfo(@RequestParam Long studentInfoId) {
        return this.studentInfoService.findStudentInfo(studentInfoId);
    }

    /**
     * 根据ID查询指定的学生信息(包含外键名称)
     *
     * @param studentInfoId Id
     */
    @PreAuthorize("hasAuthority('ams:studentEntity:StudentInfo:view')")
    @ApiOperation("根据ID查询指定的学生信息(包含外键名称)")
    @PostMapping("/findStudentInfoForView")
    public StudentInfo findStudentInfoForView(@RequestParam Long studentInfoId) {
        return this.studentInfoService.findStudentInfoWithForeignName(studentInfoId);
    }

    /**
     * 根据ID查询指定的学生信息(包含学生信息和外键名称)
     *
     * @param studentInfoId Id
     */
    @PreAuthorize("hasAuthority('ams:studentEntity:StudentInfo:view')")
    @ApiOperation("根据ID查询指定的学生信息(包含学生信息和外键名称)")
    @PostMapping("/findStudentInfoForEdit")
    public StudentInfoEditDto findStudentInfoForEdit(@RequestParam Long studentInfoId) {
        StudentInfoEditDto studentInfoEditDto = new StudentInfoEditDto();
        studentInfoEditDto.setStudentInfo(this.studentInfoService.findStudentInfoWithForeignName(studentInfoId));

        this.prepareStudentInfoEditDto(studentInfoEditDto);

        return studentInfoEditDto;
    }

    /**
     * 根据ID查询指定的学生信息(只提取ID 和 Name)
     *
     * @param studentInfoId Id
     */
    @PreAuthorize("hasAuthority('ams:studentEntity:StudentInfo:view')")
    @ApiOperation("根据ID查询指定的学生信息(只提取ID 和 Name)")
    @PostMapping("/findStudentInfosWithIdNameById")
    public StudentInfo findStudentInfosWithIdNameById(@RequestParam Long studentInfoId) {
        return this.studentInfoService.findStudentInfosWithIdNameById(studentInfoId);
    }

    /**
     * 根据名称查询学生信息集合(只提取ID 和 Name)
     *
     * @param studentInfoName 名称
     */
    @PreAuthorize("hasAuthority('ams:studentEntity:StudentInfo:view')")
    @ApiOperation("根据名称查询学生信息集合(只提取ID 和 Name)")
    @PostMapping("/findStudentInfosWithIdNameByName")
    public List<StudentInfo> findStudentInfosWithIdNameByName(@RequestParam String studentInfoName) {
        return this.studentInfoService.findStudentInfosWithIdNameByName(studentInfoName);
    }


    /**
     * 创建新的学生信息
     */
    @PreAuthorize("hasAuthority('ams:studentEntity:StudentInfo:add')")
    @ApiOperation("创建新的学生信息")
    @PostMapping("/createStudentInfo")
    public StudentInfoEditDto createStudentInfo() {
        StudentInfoEditDto studentInfoEditDto = new StudentInfoEditDto();
        studentInfoEditDto.setStudentInfo(new StudentInfo());

        this.prepareStudentInfoEditDto(studentInfoEditDto);
        return studentInfoEditDto;
    }

    private void prepareStudentInfoEditDto(StudentInfoEditDto studentInfoEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        studentInfoEditDto.setCollegeColleges(this.collegeService.findAllCollegesWithIdName());
    }

    /**
     * 新增保存学生信息
     *
     * @param studentInfo 实体对象
     */
//    @PreAuthorize("hasAuthority('ams:studentEntity:StudentInfo:add')")
    @ApiOperation("新增保存学生信息")
    @PostMapping("/saveStudentInfo")
    public StudentInfo saveStudentInfo(@RequestBody StudentInfo studentInfo) {
        System.out.println("啦啦啦"+studentInfo.getRemark());
        return this.studentInfoService.saveStudentInfo(studentInfo);
    }

    @ApiOperation("新增保存学生信息")
    @PostMapping("/saveStudentInfoMy")
    public void saveStudentInfoMy(@RequestParam String studentId, @RequestParam String name, @RequestParam String major,
                                         @RequestParam String collegeId, @RequestParam String grade, @RequestParam String classGrade,
                                         @RequestParam String contactTel, @RequestParam String email, @RequestParam String remark) {
        System.out.println("学生类型controller："+remark);
        this.studentInfoService.saveStudentInfoMy(studentId, name, major, collegeId, grade, classGrade, contactTel, email,remark);
    }


    /**
     * 修改保存学生信息
     *
     * @param studentInfo 实体对象
     */
    @PreAuthorize("hasAuthority('ams:studentEntity:StudentInfo:update')")
    @ApiOperation("修改保存学生信息")
    @PostMapping("/updateStudentInfo")
    public StudentInfo updateStudentInfo(@RequestBody StudentInfo studentInfo) {
        return this.studentInfoService.updateStudentInfo(studentInfo);
    }

    /**
     * 根据ID删除学生信息
     *
     * @param studentInfoId ID
     */
    @PreAuthorize("hasAuthority('ams:studentEntity:StudentInfo:remove')")
    @ApiOperation("根据ID删除学生信息")
    @PostMapping("/deleteStudentInfo")
    public void deleteStudentInfo(@RequestParam Long studentInfoId) {
        this.studentInfoService.deleteStudentInfo(studentInfoId);
    }

    /**
     * 根据学号查询学生信息
     *
     * @param studentInfoId Id
     */
    @PreAuthorize("hasAuthority('ams:studentEntity:StudentInfo:view')")
    @ApiOperation("根据学号查询学生信息")
    @PostMapping("/findStudentInfoNumById")
    public List<StudentInfo> findStudentInfoNumById(@RequestParam String studentInfoId) {
        return this.studentInfoService.findStudentInfoNumById(studentInfoId);
    }

    /**
     * 注册时候发送验证码
     * 先把注册写在这里，这样写不规范，需要优化
     */
    @ApiOperation("发送验证码")
    @PostMapping("/sendCheckCode")
    public String sendCheckCode(String email) {
        return this.studentInfoService.sendCheckCode(email);
    }

    /**
     * 验证验证码
     */

    @ApiOperation("验证验证码")
    @PostMapping("/checkCode")
    public boolean checkCode(String code, String email) throws IOException {
        return this.studentInfoService.checkCode(code, email);
    }

    //（通过获取学生学号返回eid）
    @ApiOperation("获取学生eid")
    @PostMapping("/getStudentEid")
    public Long getStudentEid(@RequestParam String studentId) {
        return this.studentInfoService.getStudentEid(studentId);
    }

    //(通过获取学生邮箱返回eid)
    @ApiOperation("通过邮箱获取学生eid")
    @PostMapping("/getStudentEidByEmail")
    public Long getStudentEidByEmail(@RequestParam String email) {
        return this.studentInfoService.getStudentEidByEmail(email);
    }

    // (修改学生信息)
    @ApiOperation("修改学生信息")
    @PostMapping("/updateStudentInfoMy")
    public void updateStudentInfoMy(@RequestParam String studentId, @RequestParam String name, @RequestParam String major,
                                    @RequestParam String collegeId, @RequestParam String grade, @RequestParam String classGrade,
                                    @RequestParam String contactTel, @RequestParam String email, @RequestParam String remark) {
        System.out.println("更新学生表之remark1："+remark);
        this.studentInfoService.updateStudentInfoMy(studentId, name, major, collegeId, grade, classGrade, contactTel, email,remark);
    }

    //导入学生信息
    /**
     * 根据excel导入学生信息
     *
     * @param uploadStudentInfos
     */
    @PreAuthorize("hasAuthority('sys:file:upload')")
    @OperLog(value = "文件管理", desc = "上传文件", param = false, result = true)
    @ApiOperation("上传文件")
    @PostMapping("/uploadStudentInfos")
    public int uploadTeacherInfos(@RequestParam("file") MultipartFile uploadStudentInfos) {
        return this.studentInfoService.uploadStudentInfos(uploadStudentInfos);
    }


}

