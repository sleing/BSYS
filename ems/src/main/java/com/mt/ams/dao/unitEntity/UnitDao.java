package com.mt.ams.dao.unitEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ams.entity.unitEntity.Unit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@Component(value = "unitDao")
public interface UnitDao {

    /**
     * 根据分页参数查询举办单位信息集合
     *
     * @param pageDTO 分页条件
     */
    public List<Unit> findUnits(PageDTO pageDTO);

    /**
     * 查询全部举办单位信息集合
     */
    public List<Unit> findAllUnits();

    /**
     * 查询所有举办单位信息集合(只提取ID 和 Name)
     */
    public List<Unit> findAllUnitsWithIdName();

    /**
     * 根据名称查询举办单位信息集合(只提取ID 和 Name)
     *
     * @param unitName 名称
     */
    public List<Unit> findUnitsWithIdNameByName(@Param("unitName") String unitName);

    /**
     * 根据ID查询指定的举办单位信息(只提取ID 和 Name)
     *
     * @param unitId Id
     */
    public Unit findUnitsWithIdNameById(@Param(" unitId") Long unitId);

    /**
     * 根据分页参数查询举办单位信息集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findUnitTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的举办单位信息
     *
     * @param unitId Id
     */
    public Unit findUnit(@Param("unitId") Long unitId);

    /**
     * 根据ID查询指定的举办单位信息(包含外键)
     *
     * @param unitId Id
     */
    public Unit findUnitWithForeignName(@Param("unitId") Long unitId);

    /**
     * 新增举办单位信息
     *
     * @param unit 实体对象
     */
    public Long saveUnit(Unit unit);

    /**
     * 更新举办单位信息
     *
     * @param unit 实体对象
     */
    public Long updateUnit(Unit unit);

    /**
     * 根据ID删除举办单位信息
     *
     * @param unitId ID
     */
    public Long deleteUnit(@Param("unitId") Long unitId);

    Integer getUnitName(String name);
}
