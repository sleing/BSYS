package com.mt.ams.dao.appraisingTypeEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ams.entity.appraisingTypeEntity.AppraisingType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@Component(value = "appraisingTypeDao")
public interface AppraisingTypeDao {

    /**
     * 根据分页参数查询学生处评优评先类别信息集合
     *
     * @param pageDTO 分页条件
     */
    public List<AppraisingType> findAppraisingTypes(PageDTO pageDTO);

    /**
     * 查询全部学生处评优评先类别信息集合
     */
    public List<AppraisingType> findAllAppraisingTypes();

    /**
     * 查询所有学生处评优评先类别信息集合(只提取ID 和 Name)
     */
    public List<AppraisingType> findAllAppraisingTypesWithIdName();

    /**
     * 根据名称查询学生处评优评先类别信息集合(只提取ID 和 Name)
     *
     * @param appraisingTypeName 名称
     */
    public List<AppraisingType> findAppraisingTypesWithIdNameByName(@Param("appraisingTypeName") String appraisingTypeName);

    /**
     * 根据ID查询指定的学生处评优评先类别信息(只提取ID 和 Name)
     *
     * @param appraisingTypeId Id
     */
    public AppraisingType findAppraisingTypesWithIdNameById(@Param(" appraisingTypeId") Long appraisingTypeId);

    /**
     * 根据分页参数查询学生处评优评先类别信息集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findAppraisingTypeTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的学生处评优评先类别信息
     *
     * @param appraisingTypeId Id
     */
    public AppraisingType findAppraisingType(@Param("appraisingTypeId") Long appraisingTypeId);

    /**
     * 根据ID查询指定的学生处评优评先类别信息(包含外键)
     *
     * @param appraisingTypeId Id
     */
    public AppraisingType findAppraisingTypeWithForeignName(@Param("appraisingTypeId") Long appraisingTypeId);

    /**
     * 新增学生处评优评先类别信息
     *
     * @param appraisingType 实体对象
     */
    public Long saveAppraisingType(AppraisingType appraisingType);

    /**
     * 更新学生处评优评先类别信息
     *
     * @param appraisingType 实体对象
     */
    public Long updateAppraisingType(AppraisingType appraisingType);

    /**
     * 根据ID删除学生处评优评先类别信息
     *
     * @param appraisingTypeId ID
     */
    public Long deleteAppraisingType(@Param("appraisingTypeId") Long appraisingTypeId);
}
