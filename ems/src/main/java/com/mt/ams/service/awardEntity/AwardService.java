package com.mt.ams.service.awardEntity;

import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ams.entity.awardEntity.Award;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AwardService {
    /**
     * 根据分页参数查询获奖登记集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findAwards(PageDTO pageDTO);

    /**
     * 查询全部获奖登记集合
     */
    public List<Award> findAllAwards();

    /**
     * 根据名称查询获奖登记集合(只提取ID 和 Name)
     *
     * @param awardName 名称
     */
    public List<Award> findAwardsWithIdNameByName(String awardName);

    /**
     * 查询所有获奖登记集合(只提取ID 和 Name)
     */
    public List<Award> findAllAwardsWithIdName();

    /**
     * 根据ID查询指定的获奖登记(只提取ID 和 Name)
     *
     * @param awardId Id
     */
    public Award findAwardsWithIdNameById(Long awardId);

    /**
     * 根据ID查询指定的获奖登记
     *
     * @param awardId Id
     */
    public Award findAward(Long awardId);

    /**
     * 根据ID查询指定的获奖登记(包含外键)
     *
     * @param awardId Id
     */
    public Award findAwardWithForeignName(Long awardId);

    /**
     * 新增获奖登记
     *
     * @param award 实体对象
     */
    public Award saveAward(Award award);

    /**
     * 更新获奖登记
     *
     * @param award 实体对象
     */
    public Award updateAward(Award award);

    /**
     * 根据ID删除获奖登记
     *
     * @param awardId ID
     */
    public void deleteAward(Long awardId);

    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("分页查询获奖登记")
    @ApiPageParam
    public PageResultDTO MyfindAwards(@RequestBody PageDTO pageDTO);

    public void myUpdate(Long awardId);

    public void auditForCon(Long awardId, String remarkContent);

    /**
     * 根据rowEid获取证明文件路径
     *
     * @param eid
     */
    public String fileUrl(Long eid);

    /**
     * 根据rowEid获取证明文件路径
     *
     * @param eid
     */
    public String resourcesUrl(Long eid);

    public void updateReviewer(Long Id,Long eid);

}
