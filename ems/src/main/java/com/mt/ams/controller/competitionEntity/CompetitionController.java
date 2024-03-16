

package com.mt.ams.controller.competitionEntity;

import com.mt.ams.dto.competitionEntity.CompetitionEditDto;
import com.mt.ams.entity.competitionEntity.Competition;
import com.mt.ams.service.competitionEntity.CompetitionService;
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


@Api(tags = "竞赛信息")
@RestController
@RequestMapping("/api/ams/competitionEntity/Competition")
@CrossOrigin(allowCredentials = "true")
public class CompetitionController {
    private static Logger logger = LoggerFactory.getLogger(CompetitionController.class);


    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private UnitService unitService;

    /**
     * 根据分页参数查询竞赛信息集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:competitionEntity:Competition:view')")
    @ApiOperation("分页查询竞赛信息")
    @ApiPageParam
    @PostMapping("/findCompetitions")
    public PageResultDTO findCompetitions(@RequestBody PageDTO pageDTO) {
        return this.competitionService.findCompetitions(pageDTO);
    }

    /**
     * 根据ID查询指定的竞赛信息
     *
     * @param competitionId Id
     */
    @PreAuthorize("hasAuthority('ams:competitionEntity:Competition:view')")
    @ApiOperation("根据id查询竞赛信息")
    @ApiPageParam
    @PostMapping("/findCompetition")
    public Competition findCompetition(@RequestParam Long competitionId) {
        return this.competitionService.findCompetition(competitionId);
    }

    /**
     * 根据ID查询指定的竞赛信息(包含外键名称)
     *
     * @param competitionId Id
     */
    @PreAuthorize("hasAuthority('ams:competitionEntity:Competition:view')")
    @ApiOperation("根据ID查询指定的竞赛信息(包含外键名称)")
    @PostMapping("/findCompetitionForView")
    public Competition findCompetitionForView(@RequestParam Long competitionId) {
        return this.competitionService.findCompetitionWithForeignName(competitionId);
    }

    /**
     * 根据ID查询指定的竞赛信息(包含竞赛信息和外键名称)
     *
     * @param competitionId Id
     */
    @PreAuthorize("hasAuthority('ams:competitionEntity:Competition:view')")
    @ApiOperation("根据ID查询指定的竞赛信息(包含竞赛信息和外键名称)")
    @PostMapping("/findCompetitionForEdit")
    public CompetitionEditDto findCompetitionForEdit(@RequestParam Long competitionId) {
        CompetitionEditDto competitionEditDto = new CompetitionEditDto();
        competitionEditDto.setCompetition(this.competitionService.findCompetitionWithForeignName(competitionId));

        this.prepareCompetitionEditDto(competitionEditDto);

        return competitionEditDto;
    }

    /**
     * 根据ID查询指定的竞赛信息(只提取ID 和 Name)
     *
     * @param competitionId Id
     */
    @PreAuthorize("hasAuthority('ams:competitionEntity:Competition:view')")
    @ApiOperation("根据ID查询指定的竞赛信息(只提取ID 和 Name)")
    @PostMapping("/findCompetitionsWithIdNameById")
    public Competition findCompetitionsWithIdNameById(@RequestParam Long competitionId) {
        return this.competitionService.findCompetitionsWithIdNameById(competitionId);
    }

    /**
     * 根据名称查询竞赛信息集合(只提取ID 和 Name)
     *
     * @param competitionName 名称
     */
    @PreAuthorize("hasAuthority('ams:competitionEntity:Competition:view')")
    @ApiOperation("根据名称查询竞赛信息集合(只提取ID 和 Name)")
    @PostMapping("/findCompetitionsWithIdNameByName")
    public List<Competition> findCompetitionsWithIdNameByName(@RequestParam String competitionName) {
        return this.competitionService.findCompetitionsWithIdNameByName(competitionName);
    }


    /**
     * 创建新的竞赛信息
     */
    @PreAuthorize("hasAuthority('ams:competitionEntity:Competition:add')")
    @ApiOperation("创建新的竞赛信息")
    @PostMapping("/createCompetition")
    public CompetitionEditDto createCompetition() {
        CompetitionEditDto competitionEditDto = new CompetitionEditDto();
        competitionEditDto.setCompetition(new Competition());

        this.prepareCompetitionEditDto(competitionEditDto);
        return competitionEditDto;
    }

    private void prepareCompetitionEditDto(CompetitionEditDto competitionEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        competitionEditDto.setUnitsUnits(this.unitService.findAllUnitsWithIdName());
    }

    /**
     * 新增保存竞赛信息
     *
     * @param competition 实体对象
     */
    @PreAuthorize("hasAuthority('ams:competitionEntity:Competition:add')")
    @ApiOperation("新增保存竞赛信息")
    @PostMapping("/saveCompetition")
    public Competition saveCompetition(@RequestBody Competition competition) {
        return this.competitionService.saveCompetition(competition);
    }

    /**
     * 修改保存竞赛信息
     *
     * @param competition 实体对象
     */
    @PreAuthorize("hasAuthority('ams:competitionEntity:Competition:update')")
    @ApiOperation("修改保存竞赛信息")
    @PostMapping("/updateCompetition")
    public Competition updateCompetition(@RequestBody Competition competition) {
        return this.competitionService.updateCompetition(competition);
    }

    /**
     * 根据ID删除竞赛信息
     *
     * @param competitionId ID
     */
    @PreAuthorize("hasAuthority('ams:competitionEntity:Competition:remove')")
    @ApiOperation("根据ID删除竞赛信息")
    @PostMapping("/deleteCompetition")
    public void deleteCompetition(@RequestParam Long competitionId) {
        this.competitionService.deleteCompetition(competitionId);
    }

    @PreAuthorize("hasAuthority('ams:competitionEntity:Competition:view')")
    @ApiOperation("根据名称查询竞赛信息集合(只提取ID 和 Name)")
    @PostMapping("/getCompetitionEidByName")
    public Boolean getCompetitionEidByName(@RequestParam String competitionName) {
        return this.competitionService.getCompetitionEidByName(competitionName);
    }


}

