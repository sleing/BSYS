package com.mt.ams.service.unitEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ams.entity.unitEntity.Unit;

import java.util.List;

public interface UnitService {
    /**
     * 根据分页参数查询举办单位信息集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findUnits(PageDTO pageDTO);

    /**
     * 查询全部举办单位信息集合
     */
    public List<Unit> findAllUnits();

    /**
     * 根据名称查询举办单位信息集合(只提取ID 和 Name)
     *
     * @param unitName 名称
     */
    public List<Unit> findUnitsWithIdNameByName(String unitName);

    /**
     * 查询所有举办单位信息集合(只提取ID 和 Name)
     */
    public List<Unit> findAllUnitsWithIdName();

    /**
     * 根据ID查询指定的举办单位信息(只提取ID 和 Name)
     *
     * @param unitId Id
     */
    public Unit findUnitsWithIdNameById(Long unitId);

    /**
     * 根据ID查询指定的举办单位信息
     *
     * @param unitId Id
     */
    public Unit findUnit(Long unitId);

    /**
     * 根据ID查询指定的举办单位信息(包含外键)
     *
     * @param unitId Id
     */
    public Unit findUnitWithForeignName(Long unitId);

    /**
     * 新增举办单位信息
     *
     * @param unit 实体对象
     */
    public Unit saveUnit(Unit unit);

    /**
     * 更新举办单位信息
     *
     * @param unit 实体对象
     */
    public Unit updateUnit(Unit unit);

    /**
     * 根据ID删除举办单位信息
     *
     * @param unitId ID
     */
    public void deleteUnit(Long unitId);

    Boolean getUnitName(String name);
}
