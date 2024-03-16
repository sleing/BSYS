package com.mt.tms.service.tsmeetingEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsmeetingEntity.TsMeeting;

import java.util.List;

public interface TsMeetingService {
    /**
     * 根据分页参数查询会议管理集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTsMeetings(PageDTO pageDTO);

    /**
     * 查询全部会议管理集合
     *
     */
    public List<TsMeeting> findAllTsMeetings();

    /**
     * 根据名称查询会议管理集合(只提取ID 和 Name)
     *
     * @param tsMeetingName 名称
     */
    public List<TsMeeting> findTsMeetingsWithIdNameByName(String tsMeetingName);

    /**
     * 查询所有会议管理集合(只提取ID 和 Name)
     *
     */
    public List<TsMeeting> findAllTsMeetingsWithIdName();

    /**
     * 根据ID查询指定的会议管理(只提取ID 和 Name)
     *
     * @param tsMeetingId Id
     */
    public TsMeeting findTsMeetingsWithIdNameById(Long tsMeetingId);

    /**
     * 根据ID查询指定的会议管理
     *
     * @param tsMeetingId Id
     */
    public TsMeeting findTsMeeting(Long tsMeetingId);

    /**
     * 根据ID查询指定的会议管理(包含外键)
     *
     * @param tsMeetingId Id
     */
    public TsMeeting findTsMeetingWithForeignName(Long tsMeetingId);

    /**
     * 新增会议管理
     *
     * @param tsMeeting 实体对象
     */
    public TsMeeting saveTsMeeting(TsMeeting tsMeeting);

    /**
     * 更新会议管理
     *
     * @param tsMeeting 实体对象
     */
    public TsMeeting updateTsMeeting(TsMeeting tsMeeting);

    /**
     * 根据ID删除会议管理
     *
     * @param tsMeetingId ID
     */
    public void deleteTsMeeting(Long tsMeetingId);
}
