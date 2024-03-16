

package com.mt.ams.controller.awardeeEntity;

import com.mt.ams.dao.awardEntity.AwardDao;
import com.mt.ams.dao.competitionEntity.CompetitionDao;
import com.mt.ams.dao.studentEntity.StudentInfoDao;
import com.mt.ams.dto.awardeeEntity.AwardeeEditDto;
import com.mt.ams.entity.awardeeEntity.Awardee;
import com.mt.ams.service.awardEntity.AwardService;
import com.mt.ams.service.awardeeEntity.AwardeeService;
import com.mt.ams.service.studentEntity.StudentInfoService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Api(tags = "获奖学生信息")
@RestController
@RequestMapping("/api/ams/awardeeEntity/Awardee")
@CrossOrigin(allowCredentials = "true")
public class AwardeeController {
    private static Logger logger = LoggerFactory.getLogger(AwardeeController.class);


    @Autowired
    private AwardeeService awardeeService;
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private AwardService awardService;
    @Autowired
    private StudentInfoDao studentInfoDao;
    @Autowired
    private AwardDao awardDao;
    @Autowired
    private CompetitionDao competitionDao;

    /**
     * 根据分页参数查询获奖学生信息集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:view')")
    @ApiOperation("分页查询获奖学生信息")
    @ApiPageParam
    @PostMapping("/findAwardees")
    public PageResultDTO findAwardees(@RequestBody PageDTO pageDTO) {
        return this.awardeeService.findAwardees(pageDTO);
    }

    /**
     * 根据ID查询指定的获奖学生信息
     *
     * @param awardeeId Id
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:view')")
    @ApiOperation("根据id查询获奖学生信息")
    @ApiPageParam
    @PostMapping("/findAwardee")
    public Awardee findAwardee(@RequestParam Long awardeeId) {
        return this.awardeeService.findAwardee(awardeeId);
    }

    /**
     * 根据ID查询指定的获奖学生信息(包含外键名称)
     *
     * @param awardeeId Id
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:view')")
    @ApiOperation("根据ID查询指定的获奖学生信息(包含外键名称)")
    @PostMapping("/findAwardeeForView")
    public Awardee findAwardeeForView(@RequestParam Long awardeeId) {
        return this.awardeeService.findAwardeeWithForeignName(awardeeId);
    }

    /**
     * 根据ID查询指定的获奖学生信息(包含获奖学生信息和外键名称)
     *
     * @param awardeeId Id
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:view')")
    @ApiOperation("根据ID查询指定的获奖学生信息(包含获奖学生信息和外键名称)")
    @PostMapping("/findAwardeeForEdit")
    public AwardeeEditDto findAwardeeForEdit(@RequestParam Long awardeeId) {
        AwardeeEditDto awardeeEditDto = new AwardeeEditDto();
        awardeeEditDto.setAwardee(this.awardeeService.findAwardeeWithForeignName(awardeeId));

        this.prepareAwardeeEditDto(awardeeEditDto);

        return awardeeEditDto;
    }

    /**
     * 根据ID查询指定的获奖学生信息(只提取ID 和 Name)
     *
     * @param awardeeId Id
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:view')")
    @ApiOperation("根据ID查询指定的获奖学生信息(只提取ID 和 Name)")
    @PostMapping("/findAwardeesWithIdNameById")
    public Awardee findAwardeesWithIdNameById(@RequestParam Long awardeeId) {
        return this.awardeeService.findAwardeesWithIdNameById(awardeeId);
    }

    /**
     * 根据名称查询获奖学生信息集合(只提取ID 和 Name)
     *
     * @param awardeeName 名称
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:view')")
    @ApiOperation("根据名称查询获奖学生信息集合(只提取ID 和 Name)")
    @PostMapping("/findAwardeesWithIdNameByName")
    public List<Awardee> findAwardeesWithIdNameByName(@RequestParam String awardeeName) {
        return this.awardeeService.findAwardeesWithIdNameByName(awardeeName);
    }


    /**
     * 创建新的获奖学生信息
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:add')")
    @ApiOperation("创建新的获奖学生信息")
    @PostMapping("/createAwardee")
    public AwardeeEditDto createAwardee() {
        AwardeeEditDto awardeeEditDto = new AwardeeEditDto();
        awardeeEditDto.setAwardee(new Awardee());

        this.prepareAwardeeEditDto(awardeeEditDto);
        return awardeeEditDto;
    }

    private void prepareAwardeeEditDto(AwardeeEditDto awardeeEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        awardeeEditDto.setStudentStudentInfos(this.studentInfoService.findAllStudentInfosWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        awardeeEditDto.setAwardAwards(this.awardService.findAllAwardsWithIdName());
    }

    /**
     * 新增保存获奖学生信息
     *
     * @param awardee 实体对象
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:add')")
    @ApiOperation("新增保存获奖学生信息")
    @PostMapping("/saveAwardee")
    public Awardee saveAwardee(@RequestBody Awardee awardee) {
        return this.awardeeService.saveAwardee(awardee);
    }

    /**
     * 修改保存获奖学生信息
     *
     * @param awardee 实体对象
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:update')")
    @ApiOperation("修改保存获奖学生信息")
    @PostMapping("/updateAwardee")
    public Awardee updateAwardee(@RequestBody Awardee awardee) {
        return this.awardeeService.updateAwardee(awardee);
    }

    /**
     * 根据ID删除获奖学生信息
     *
     * @param awardeeId ID
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:remove')")
    @ApiOperation("根据ID删除获奖学生信息")
    @PostMapping("/deleteAwardee")
    public void deleteAwardee(@RequestParam Long awardeeId) {
        this.awardeeService.deleteAwardee(awardeeId);
    }

    /**
     * 根据awardId删除获奖学生信息
     *
     * @param awardId ID
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:remove')")
    @ApiOperation("根据ID删除获奖学生信息")
    @PostMapping("/deleteAwardeeByAwardId")
    public void deleteAwardeeByAwardId(@RequestParam Long awardId) {
        this.awardeeService.deleteAwardeeByAwardId(awardId);
    }

    /**
     * 根据awardId获得学生信息
     *
     * @param awardId ID
     */
    @PreAuthorize("hasAuthority('ams:awardeeEntity:Awardee:update')")
    @ApiOperation("根据ID获取获奖学生信息(只提取studentId)")
    @PostMapping("/findAwardeeByAwardId")
    public List<Awardee> findAwardeeByAwardId(@RequestParam Long awardId) {
        return this.awardeeService.findAwardeeByAwardId(awardId);
    }

    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        this.awardeeService.exportExcel(response,request);
    }

}

