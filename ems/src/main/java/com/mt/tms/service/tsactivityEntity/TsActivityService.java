package com.mt.tms.service.tsactivityEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsactivityEntity.TsActivity;

import java.util.List;

public interface TsActivityService {
    /**
     * 根据分页参数查询活动中心集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTsActivitys(PageDTO pageDTO);

    /**
     * 查询全部活动中心集合
     *
     */
    public List<TsActivity> findAllTsActivitys();

    /**
     * 根据名称查询活动中心集合(只提取ID 和 Name)
     *
     * @param tsActivityName 名称
     */
    public List<TsActivity> findTsActivitysWithIdNameByName(String tsActivityName);

    /**
     * 查询所有活动中心集合(只提取ID 和 Name)
     *
     */
    public List<TsActivity> findAllTsActivitysWithIdName();

    /**
     * 根据ID查询指定的活动中心(只提取ID 和 Name)
     *
     * @param tsActivityId Id
     */
    public TsActivity findTsActivitysWithIdNameById(Long tsActivityId);

    /**
     * 根据ID查询指定的活动中心
     *
     * @param tsActivityId Id
     */
    public TsActivity findTsActivity(Long tsActivityId);

    /**
     * 根据ID查询指定的活动中心(包含外键)
     *
     * @param tsActivityId Id
     */
    public TsActivity findTsActivityWithForeignName(Long tsActivityId);

    /**
     * 新增活动中心
     *
     * @param tsActivity 实体对象
     */
    public TsActivity saveTsActivity(TsActivity tsActivity);

    /**
     * 更新活动中心
     *
     * @param tsActivity 实体对象
     */
    public TsActivity updateTsActivity(TsActivity tsActivity);

    /**
     * 根据ID删除活动中心
     *
     * @param tsActivityId ID
     */
    public void deleteTsActivity(Long tsActivityId);
}
