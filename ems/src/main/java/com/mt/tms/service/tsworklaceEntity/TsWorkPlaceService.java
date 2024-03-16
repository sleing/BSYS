package com.mt.tms.service.tsworklaceEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsworklaceEntity.TsWorkPlace;

import java.util.List;

public interface TsWorkPlaceService {
    /**
     * 根据分页参数查询办公地点管理集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTsWorkPlaces(PageDTO pageDTO);

    /**
     * 查询全部办公地点管理集合
     *
     */
    public List<TsWorkPlace> findAllTsWorkPlaces();

    /**
     * 根据名称查询办公地点管理集合(只提取ID 和 Name)
     *
     * @param tsWorkPlaceName 名称
     */
    public List<TsWorkPlace> findTsWorkPlacesWithIdNameByName(String tsWorkPlaceName);

    /**
     * 查询所有办公地点管理集合(只提取ID 和 Name)
     *
     */
    public List<TsWorkPlace> findAllTsWorkPlacesWithIdName();

    /**
     * 根据ID查询指定的办公地点管理(只提取ID 和 Name)
     *
     * @param tsWorkPlaceId Id
     */
    public TsWorkPlace findTsWorkPlacesWithIdNameById(Long tsWorkPlaceId);

    /**
     * 根据ID查询指定的办公地点管理
     *
     * @param tsWorkPlaceId Id
     */
    public TsWorkPlace findTsWorkPlace(Long tsWorkPlaceId);

    /**
     * 根据ID查询指定的办公地点管理(包含外键)
     *
     * @param tsWorkPlaceId Id
     */
    public TsWorkPlace findTsWorkPlaceWithForeignName(Long tsWorkPlaceId);

    /**
     * 新增办公地点管理
     *
     * @param tsWorkPlace 实体对象
     */
    public TsWorkPlace saveTsWorkPlace(TsWorkPlace tsWorkPlace);

    /**
     * 更新办公地点管理
     *
     * @param tsWorkPlace 实体对象
     */
    public TsWorkPlace updateTsWorkPlace(TsWorkPlace tsWorkPlace);

    /**
     * 根据ID删除办公地点管理
     *
     * @param tsWorkPlaceId ID
     */
    public void deleteTsWorkPlace(Long tsWorkPlaceId);
}
