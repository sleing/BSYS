package com.mt.tms.dao.tsactivityEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsactivityEntity.TsActivity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "tsActivityDao")
public interface TsActivityDao {

    /**
    * 根据分页参数查询活动中心集合
    *
    * @param pageDTO 分页条件
    */
    public List<TsActivity> findTsActivitys(PageDTO pageDTO);

    /**
    * 查询全部活动中心集合
    *
    */
    public List<TsActivity> findAllTsActivitys();

    /**
    * 查询所有活动中心集合(只提取ID 和 Name)
    *
    */
    public List<TsActivity> findAllTsActivitysWithIdName();

    /**
    * 根据名称查询活动中心集合(只提取ID 和 Name)
    *
    * @param tsActivityName 名称
    */
    public List<TsActivity> findTsActivitysWithIdNameByName(@Param("tsActivityName") String tsActivityName);

    /**
    * 根据ID查询指定的活动中心(只提取ID 和 Name)
    *
    * @param tsActivityId Id
    */
    public TsActivity findTsActivitysWithIdNameById(@Param(" tsActivityId") Long tsActivityId);

    /**
    * 根据分页参数查询活动中心集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTsActivityTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的活动中心
    *
    * @param tsActivityId Id
    */
    public TsActivity findTsActivity(@Param("tsActivityId") Long tsActivityId);

    /**
    * 根据ID查询指定的活动中心(包含外键)
    *
    * @param tsActivityId Id
    */
    public TsActivity findTsActivityWithForeignName(@Param("tsActivityId") Long tsActivityId);

    /**
    * 新增活动中心
    *
    * @param tsActivity 实体对象
    */
    public Long saveTsActivity(TsActivity tsActivity);

    /**
    * 更新活动中心
    *
    * @param tsActivity 实体对象
    */
    public Long updateTsActivity(TsActivity tsActivity);

    /**
    * 根据ID删除活动中心
    *
    * @param tsActivityId ID
    */
    public Long deleteTsActivity(@Param("tsActivityId") Long tsActivityId);
}
