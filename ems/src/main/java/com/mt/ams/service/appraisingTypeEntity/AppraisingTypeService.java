package com.mt.ams.service.appraisingTypeEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ams.entity.appraisingTypeEntity.AppraisingType;

import java.util.List;

public interface AppraisingTypeService {
    /**
     * 根据分页参数查询学生处评优评先类别信息集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findAppraisingTypes(PageDTO pageDTO);

    /**
     * 查询全部学生处评优评先类别信息集合
     */
    public List<AppraisingType> findAllAppraisingTypes();

    /**
     * 根据名称查询学生处评优评先类别信息集合(只提取ID 和 Name)
     *
     * @param appraisingTypeName 名称
     */
    public List<AppraisingType> findAppraisingTypesWithIdNameByName(String appraisingTypeName);

    /**
     * 查询所有学生处评优评先类别信息集合(只提取ID 和 Name)
     */
    public List<AppraisingType> findAllAppraisingTypesWithIdName();

    /**
     * 根据ID查询指定的学生处评优评先类别信息(只提取ID 和 Name)
     *
     * @param appraisingTypeId Id
     */
    public AppraisingType findAppraisingTypesWithIdNameById(Long appraisingTypeId);

    /**
     * 根据ID查询指定的学生处评优评先类别信息
     *
     * @param appraisingTypeId Id
     */
    public AppraisingType findAppraisingType(Long appraisingTypeId);

    /**
     * 根据ID查询指定的学生处评优评先类别信息(包含外键)
     *
     * @param appraisingTypeId Id
     */
    public AppraisingType findAppraisingTypeWithForeignName(Long appraisingTypeId);

    /**
     * 新增学生处评优评先类别信息
     *
     * @param appraisingType 实体对象
     */
    public AppraisingType saveAppraisingType(AppraisingType appraisingType);

    /**
     * 更新学生处评优评先类别信息
     *
     * @param appraisingType 实体对象
     */
    public AppraisingType updateAppraisingType(AppraisingType appraisingType);

    /**
     * 根据ID删除学生处评优评先类别信息
     *
     * @param appraisingTypeId ID
     */
    public void deleteAppraisingType(Long appraisingTypeId);
}
