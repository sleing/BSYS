package com.mt.tms.service.tsdepartmentEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsdepartmentEntity.TsDepartment;

import java.util.List;

public interface TsDepartmentService {
    /**
     * 根据分页参数查询部门信息管理集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTsDepartments(PageDTO pageDTO);

    /**
     * 查询全部部门信息管理集合
     *
     */
    public List<TsDepartment> findAllTsDepartments();

    /**
     * 根据名称查询部门信息管理集合(只提取ID 和 Name)
     *
     * @param tsDepartmentName 名称
     */
    public List<TsDepartment> findTsDepartmentsWithIdNameByName(String tsDepartmentName);

    /**
     * 查询所有部门信息管理集合(只提取ID 和 Name)
     *
     */
    public List<TsDepartment> findAllTsDepartmentsWithIdName();

    /**
     * 根据ID查询指定的部门信息管理(只提取ID 和 Name)
     *
     * @param tsDepartmentId Id
     */
    public TsDepartment findTsDepartmentsWithIdNameById(Long tsDepartmentId);

    /**
     * 根据ID查询指定的部门信息管理
     *
     * @param tsDepartmentId Id
     */
    public TsDepartment findTsDepartment(Long tsDepartmentId);

    /**
     * 根据ID查询指定的部门信息管理(包含外键)
     *
     * @param tsDepartmentId Id
     */
    public TsDepartment findTsDepartmentWithForeignName(Long tsDepartmentId);

    /**
     * 新增部门信息管理
     *
     * @param tsDepartment 实体对象
     */
    public TsDepartment saveTsDepartment(TsDepartment tsDepartment);

    /**
     * 更新部门信息管理
     *
     * @param tsDepartment 实体对象
     */
    public TsDepartment updateTsDepartment(TsDepartment tsDepartment);

    /**
     * 根据ID删除部门信息管理
     *
     * @param tsDepartmentId ID
     */
    public void deleteTsDepartment(Long tsDepartmentId);
}
