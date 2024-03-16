package com.mt.ams.service.appraisingEntity;

import com.mt.ams.entity.appraisingEntity.Appraising;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;

import java.util.List;

public interface AppraisingService {
    /**
     * 根据分页参数查询评优评先登记集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findAppraisings(PageDTO pageDTO);

    /**
     * 查询全部评优评先登记集合
     */
    public List<Appraising> findAllAppraisings();

    /**
     * 根据名称查询评优评先登记集合(只提取ID 和 Name)
     *
     * @param appraisingName 名称
     */
    public List<Appraising> findAppraisingsWithIdNameByName(String appraisingName);

    /**
     * 查询所有评优评先登记集合(只提取ID 和 Name)
     */
    public List<Appraising> findAllAppraisingsWithIdName();

    /**
     * 根据ID查询指定的评优评先登记(只提取ID 和 Name)
     *
     * @param appraisingId Id
     */
    public Appraising findAppraisingsWithIdNameById(Long appraisingId);

    /**
     * 根据ID查询指定的评优评先登记
     *
     * @param appraisingId Id
     */
    public Appraising findAppraising(Long appraisingId);

    /**
     * 根据ID查询指定的评优评先登记(包含外键)
     *
     * @param appraisingId Id
     */
    public Appraising findAppraisingWithForeignName(Long appraisingId);

    /**
     * 新增评优评先登记
     *
     * @param appraising 实体对象
     */
    public Appraising saveAppraising(Appraising appraising);

    /**
     * 更新评优评先登记
     *
     * @param appraising 实体对象
     */
    public Appraising updateAppraising(Appraising appraising);

    /**
     * 根据ID删除评优评先登记
     *
     * @param appraisingId ID
     */
    public void deleteAppraising(Long appraisingId);


    /**
     * 根据分页参数查询评优评先登记集合
     *
     * @param pageDTO 分页条件
     */

    public PageResultDTO myfindAppraisings(PageDTO pageDTO);

    //获取审核人返回的意见信息
    public void auditForCon(Long Id, String remarkContent);

    public void myUpdate(Long Id);

    public void updateReviewer(Long Id,Long eid);
}
