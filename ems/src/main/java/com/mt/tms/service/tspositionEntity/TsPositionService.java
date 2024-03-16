package com.mt.tms.service.tspositionEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tspositionEntity.TsPosition;

import java.util.List;

public interface TsPositionService {
    /**
     * 根据分页参数查询职务管理集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTsPositions(PageDTO pageDTO);

    /**
     * 查询全部职务管理集合
     *
     */
    public List<TsPosition> findAllTsPositions();

    /**
     * 根据名称查询职务管理集合(只提取ID 和 Name)
     *
     * @param tsPositionName 名称
     */
    public List<TsPosition> findTsPositionsWithIdNameByName(String tsPositionName);

    /**
     * 查询所有职务管理集合(只提取ID 和 Name)
     *
     */
    public List<TsPosition> findAllTsPositionsWithIdName();

    /**
     * 根据ID查询指定的职务管理(只提取ID 和 Name)
     *
     * @param tsPositionId Id
     */
    public TsPosition findTsPositionsWithIdNameById(Long tsPositionId);

    /**
     * 根据ID查询指定的职务管理
     *
     * @param tsPositionId Id
     */
    public TsPosition findTsPosition(Long tsPositionId);

    /**
     * 根据ID查询指定的职务管理(包含外键)
     *
     * @param tsPositionId Id
     */
    public TsPosition findTsPositionWithForeignName(Long tsPositionId);

    /**
     * 新增职务管理
     *
     * @param tsPosition 实体对象
     */
    public TsPosition saveTsPosition(TsPosition tsPosition);

    /**
     * 更新职务管理
     *
     * @param tsPosition 实体对象
     */
    public TsPosition updateTsPosition(TsPosition tsPosition);

    /**
     * 根据ID删除职务管理
     *
     * @param tsPositionId ID
     */
    public void deleteTsPosition(Long tsPositionId);
}
