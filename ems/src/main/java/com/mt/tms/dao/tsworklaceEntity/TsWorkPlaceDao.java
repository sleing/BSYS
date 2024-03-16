package com.mt.tms.dao.tsworklaceEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsworklaceEntity.TsWorkPlace;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "tsWorkPlaceDao")
public interface TsWorkPlaceDao {

    /**
    * 根据分页参数查询办公地点管理集合
    *
    * @param pageDTO 分页条件
    */
    public List<TsWorkPlace> findTsWorkPlaces(PageDTO pageDTO);

    /**
    * 查询全部办公地点管理集合
    *
    */
    public List<TsWorkPlace> findAllTsWorkPlaces();

    /**
    * 查询所有办公地点管理集合(只提取ID 和 Name)
    *
    */
    public List<TsWorkPlace> findAllTsWorkPlacesWithIdName();

    /**
    * 根据名称查询办公地点管理集合(只提取ID 和 Name)
    *
    * @param tsWorkPlaceName 名称
    */
    public List<TsWorkPlace> findTsWorkPlacesWithIdNameByName(@Param("tsWorkPlaceName") String tsWorkPlaceName);

    /**
    * 根据ID查询指定的办公地点管理(只提取ID 和 Name)
    *
    * @param tsWorkPlaceId Id
    */
    public TsWorkPlace findTsWorkPlacesWithIdNameById(@Param(" tsWorkPlaceId") Long tsWorkPlaceId);

    /**
    * 根据分页参数查询办公地点管理集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTsWorkPlaceTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的办公地点管理
    *
    * @param tsWorkPlaceId Id
    */
    public TsWorkPlace findTsWorkPlace(@Param("tsWorkPlaceId") Long tsWorkPlaceId);

    /**
    * 根据ID查询指定的办公地点管理(包含外键)
    *
    * @param tsWorkPlaceId Id
    */
    public TsWorkPlace findTsWorkPlaceWithForeignName(@Param("tsWorkPlaceId") Long tsWorkPlaceId);

    /**
    * 新增办公地点管理
    *
    * @param tsWorkPlace 实体对象
    */
    public Long saveTsWorkPlace(TsWorkPlace tsWorkPlace);

    /**
    * 更新办公地点管理
    *
    * @param tsWorkPlace 实体对象
    */
    public Long updateTsWorkPlace(TsWorkPlace tsWorkPlace);

    /**
    * 根据ID删除办公地点管理
    *
    * @param tsWorkPlaceId ID
    */
    public Long deleteTsWorkPlace(@Param("tsWorkPlaceId") Long tsWorkPlaceId);
}
