package com.mt.ams.dao.appraisingEntity;

import com.mt.ams.entity.appraisingEntity.Appraising;
import com.mt.common.core.web.base.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "appraisingDao")
public interface AppraisingDao {

    /**
     * 根据分页参数查询评优评先登记集合
     *
     * @param pageDTO 分页条件
     */
    public List<Appraising> findAppraisings(PageDTO pageDTO);

    /**
     * 查询全部评优评先登记集合
     */
    public List<Appraising> findAllAppraisings();

    /**
     * 查询所有评优评先登记集合(只提取ID 和 Name)
     */
    public List<Appraising> findAllAppraisingsWithIdName();

    /**
     * 根据名称查询评优评先登记集合(只提取ID 和 Name)
     *
     * @param appraisingName 名称
     */
    public List<Appraising> findAppraisingsWithIdNameByName(@Param("appraisingName") String appraisingName);

    /**
     * 根据ID查询指定的评优评先登记(只提取ID 和 Name)
     *
     * @param appraisingId Id
     */
    public Appraising findAppraisingsWithIdNameById(@Param("appraisingId") Long appraisingId);

    /**
     * 根据分页参数查询评优评先登记集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findAppraisingTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的评优评先登记
     *
     * @param appraisingId Id
     */
    public Appraising findAppraising(@Param("appraisingId") Long appraisingId);

    /**
     * 根据ID查询指定的评优评先登记(包含外键)
     *
     * @param appraisingId Id
     */
    public Appraising findAppraisingWithForeignName(@Param("appraisingId") Long appraisingId);

    /**
     * 新增评优评先登记
     *
     * @param appraising 实体对象
     */
    public Long saveAppraising(Appraising appraising);

    /**
     * 更新评优评先登记
     *
     * @param appraising 实体对象
     */
    public Long updateAppraising(Appraising appraising);

    /**
     * 根据ID删除评优评先登记
     *
     * @param appraisingId ID
     */
    public Long deleteAppraising(@Param("appraisingId") Long appraisingId);

    public void auditForCon(Long Id, String remarkContent);

    public void myUpdate(Long Id);

    public void updateReviewer(Long Id,Long eid);
}
