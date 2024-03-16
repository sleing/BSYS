package com.mt.tms.dao.tsdepartmentEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsdepartmentEntity.TsDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "tsDepartmentDao")
public interface TsDepartmentDao {

    /**
    * 根据分页参数查询部门信息管理集合
    *
    * @param pageDTO 分页条件
    */
    public List<TsDepartment> findTsDepartments(PageDTO pageDTO);

    /**
    * 查询全部部门信息管理集合
    *
    */
    public List<TsDepartment> findAllTsDepartments();

    /**
    * 查询所有部门信息管理集合(只提取ID 和 Name)
    *
    */
    public List<TsDepartment> findAllTsDepartmentsWithIdName();

    /**
    * 根据名称查询部门信息管理集合(只提取ID 和 Name)
    *
    * @param tsDepartmentName 名称
    */
    public List<TsDepartment> findTsDepartmentsWithIdNameByName(@Param("tsDepartmentName") String tsDepartmentName);

    /**
    * 根据ID查询指定的部门信息管理(只提取ID 和 Name)
    *
    * @param tsDepartmentId Id
    */
    public TsDepartment findTsDepartmentsWithIdNameById(@Param(" tsDepartmentId") Long tsDepartmentId);

    /**
    * 根据分页参数查询部门信息管理集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTsDepartmentTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的部门信息管理
    *
    * @param tsDepartmentId Id
    */
    public TsDepartment findTsDepartment(@Param("tsDepartmentId") Long tsDepartmentId);

    /**
    * 根据ID查询指定的部门信息管理(包含外键)
    *
    * @param tsDepartmentId Id
    */
    public TsDepartment findTsDepartmentWithForeignName(@Param("tsDepartmentId") Long tsDepartmentId);

    /**
    * 新增部门信息管理
    *
    * @param tsDepartment 实体对象
    */
    public Long saveTsDepartment(TsDepartment tsDepartment);

    /**
    * 更新部门信息管理
    *
    * @param tsDepartment 实体对象
    */
    public Long updateTsDepartment(TsDepartment tsDepartment);

    /**
    * 根据ID删除部门信息管理
    *
    * @param tsDepartmentId ID
    */
    public Long deleteTsDepartment(@Param("tsDepartmentId") Long tsDepartmentId);
}
