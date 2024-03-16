package com.mt.tms.dao.tspositionEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tspositionEntity.TsPosition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "tsPositionDao")
public interface TsPositionDao {

    /**
    * 根据分页参数查询职务管理集合
    *
    * @param pageDTO 分页条件
    */
    public List<TsPosition> findTsPositions(PageDTO pageDTO);

    /**
    * 查询全部职务管理集合
    *
    */
    public List<TsPosition> findAllTsPositions();

    /**
    * 查询所有职务管理集合(只提取ID 和 Name)
    *
    */
    public List<TsPosition> findAllTsPositionsWithIdName();

    /**
    * 根据名称查询职务管理集合(只提取ID 和 Name)
    *
    * @param tsPositionName 名称
    */
    public List<TsPosition> findTsPositionsWithIdNameByName(@Param("tsPositionName") String tsPositionName);

    /**
    * 根据ID查询指定的职务管理(只提取ID 和 Name)
    *
    * @param tsPositionId Id
    */
    public TsPosition findTsPositionsWithIdNameById(@Param(" tsPositionId") Long tsPositionId);

    /**
    * 根据分页参数查询职务管理集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTsPositionTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的职务管理
    *
    * @param tsPositionId Id
    */
    public TsPosition findTsPosition(@Param("tsPositionId") Long tsPositionId);

    /**
    * 根据ID查询指定的职务管理(包含外键)
    *
    * @param tsPositionId Id
    */
    public TsPosition findTsPositionWithForeignName(@Param("tsPositionId") Long tsPositionId);

    /**
    * 新增职务管理
    *
    * @param tsPosition 实体对象
    */
    public Long saveTsPosition(TsPosition tsPosition);

    /**
    * 更新职务管理
    *
    * @param tsPosition 实体对象
    */
    public Long updateTsPosition(TsPosition tsPosition);

    /**
    * 根据ID删除职务管理
    *
    * @param tsPositionId ID
    */
    public Long deleteTsPosition(@Param("tsPositionId") Long tsPositionId);
}
