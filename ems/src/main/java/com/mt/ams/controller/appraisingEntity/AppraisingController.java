

package com.mt.ams.controller.appraisingEntity;

import com.mt.ams.dto.appraisingEntity.AppraisingEditDto;
import com.mt.ams.entity.appraisingEntity.Appraising;
import com.mt.ams.service.appraisingEntity.AppraisingService;
import com.mt.ams.service.appraisingTypeEntity.AppraisingTypeService;
import com.mt.ams.service.collegeEntity.CollegeService;
import com.mt.ams.service.studentEntity.StudentInfoService;
import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "评优评先登记")
@RestController
@RequestMapping("/api/ams/appraisingEntity/Appraising")
@CrossOrigin(allowCredentials = "true")
public class AppraisingController {
    private static Logger logger = LoggerFactory.getLogger(AppraisingController.class);


    @Autowired
    private AppraisingService appraisingService;
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private AppraisingTypeService appraisingTypeService;
    @Autowired
    private UserService userService;

    /**
     * 根据分页参数查询评优评先登记集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:view')")
    @ApiOperation("分页查询评优评先登记")
    @ApiPageParam
    @PostMapping("/findAppraisings")
    public PageResultDTO findAppraisings(@RequestBody PageDTO pageDTO) {
        return this.appraisingService.findAppraisings(pageDTO);
    }

    /**
     * 根据ID查询指定的评优评先登记
     *
     * @param appraisingId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:view')")
    @ApiOperation("根据id查询评优评先登记")
    @ApiPageParam
    @PostMapping("/findAppraising")
    public Appraising findAppraising(@RequestParam Long appraisingId) {
        return this.appraisingService.findAppraising(appraisingId);
    }

    /**
     * 根据ID查询指定的评优评先登记(包含外键名称)
     *
     * @param appraisingId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:view')")
    @ApiOperation("根据ID查询指定的评优评先登记(包含外键名称)")
    @PostMapping("/findAppraisingForView")
    public Appraising findAppraisingForView(@RequestParam Long appraisingId) {
        return this.appraisingService.findAppraisingWithForeignName(appraisingId);
    }

    /**
     * 根据ID查询指定的评优评先登记(包含评优评先登记和外键名称)
     *
     * @param appraisingId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:view')")
    @ApiOperation("根据ID查询指定的评优评先登记(包含评优评先登记和外键名称)")
    @PostMapping("/findAppraisingForEdit")
    public AppraisingEditDto findAppraisingForEdit(@RequestParam Long appraisingId) {
        AppraisingEditDto appraisingEditDto = new AppraisingEditDto();
        appraisingEditDto.setAppraising(this.appraisingService.findAppraisingWithForeignName(appraisingId));

        this.prepareAppraisingEditDto(appraisingEditDto);

        return appraisingEditDto;
    }

    /**
     * 根据ID查询指定的评优评先登记(只提取ID 和 Name)
     *
     * @param appraisingId Id
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:view')")
    @ApiOperation("根据ID查询指定的评优评先登记(只提取ID 和 Name)")
    @PostMapping("/findAppraisingsWithIdNameById")
    public Appraising findAppraisingsWithIdNameById(@RequestParam Long appraisingId) {
        return this.appraisingService.findAppraisingsWithIdNameById(appraisingId);
    }

    /**
     * 根据名称查询评优评先登记集合(只提取ID 和 Name)
     *
     * @param appraisingName 名称
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:view')")
    @ApiOperation("根据名称查询评优评先登记集合(只提取ID 和 Name)")
    @PostMapping("/findAppraisingsWithIdNameByName")
    public List<Appraising> findAppraisingsWithIdNameByName(@RequestParam String appraisingName) {
        return this.appraisingService.findAppraisingsWithIdNameByName(appraisingName);
    }


    /**
     * 创建新的评优评先登记
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:add')")
    @ApiOperation("创建新的评优评先登记")
    @PostMapping("/createAppraising")
    public AppraisingEditDto createAppraising() {
        AppraisingEditDto appraisingEditDto = new AppraisingEditDto();
        appraisingEditDto.setAppraising(new Appraising());

        this.prepareAppraisingEditDto(appraisingEditDto);
        return appraisingEditDto;
    }

    private void prepareAppraisingEditDto(AppraisingEditDto appraisingEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        appraisingEditDto.setStudentStudentInfos(this.studentInfoService.findAllStudentInfosWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        appraisingEditDto.setCollegeColleges(this.collegeService.findAllCollegesWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        appraisingEditDto.setTargetNameAppraisingTypes(this.appraisingTypeService.findAllAppraisingTypesWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        appraisingEditDto.setReviewerUsers(this.userService.findAllUsersWithIdName());
    }

    /**
     * 新增保存评优评先登记
     *
     * @param appraising 实体对象
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:add')")
    @ApiOperation("新增保存评优评先登记")
    @PostMapping("/saveAppraising")
    public Appraising saveAppraising(@RequestBody Appraising appraising) {
        return this.appraisingService.saveAppraising(appraising);
    }

    /**
     * 修改保存评优评先登记
     *
     * @param appraising 实体对象
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:update')")
    @ApiOperation("修改保存评优评先登记")
    @PostMapping("/updateAppraising")
    public Appraising updateAppraising(@RequestBody Appraising appraising) {
        return this.appraisingService.updateAppraising(appraising);
    }

    /**
     * 根据ID删除评优评先登记
     *
     * @param appraisingId ID
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:remove')")
    @ApiOperation("根据ID删除评优评先登记")
    @PostMapping("/deleteAppraising")
    public void deleteAppraising(@RequestParam Long appraisingId) {
        this.appraisingService.deleteAppraising(appraisingId);
    }

    /**
     * 根据分页参数查询评优评先登记集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:view')")
    @ApiOperation("分页查询评优评先登记")
    @ApiPageParam
    @PostMapping("/myfindAppraisings")
    public PageResultDTO myfindAppraisings(@RequestBody PageDTO pageDTO) {
        return this.appraisingService.myfindAppraisings(pageDTO);
    }

    /**
     * 保存审核人的意见信息
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:view')")
    @ApiOperation("分页查询评优评先登记")
    @ApiPageParam
    @PostMapping("/auditForCon")
    public void auditForCon(Long Id, String remarkContent) {
        this.appraisingService.auditForCon(Id, remarkContent);
    }

    /**
     * 返回审核人的意见信息
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:view')")
    @ApiOperation("返回审核人意见")
    @ApiPageParam
    @PostMapping("/myUpdate")
    public void myUpdate(@RequestParam String Id) {
        Long eid = Long.valueOf(Id);
        this.appraisingService.myUpdate(eid);
    }

    /**
     * 更新审核人信息
     */
    @PreAuthorize("hasAuthority('ams:appraisingEntity:Appraising:view')")
    @ApiOperation("更新审核人信息")
    @ApiPageParam
    @PostMapping("/updateReviewer")
    public void updateReviewer(@RequestParam("userId") String Id,@RequestParam("rowId") String eid) {
        Long userId = Long.valueOf(Id);
        Long Aeid = Long.valueOf(eid);

        this.appraisingService.updateReviewer(userId,Aeid);

    }
}
