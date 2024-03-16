package com.mt.tms.dao.tsmeetingEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsmeetingEntity.TsMeeting;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "tsMeetingDao")
public interface TsMeetingDao {

    /**
    * 根据分页参数查询会议管理集合
    *
    * @param pageDTO 分页条件
    */
    public List<TsMeeting> findTsMeetings(PageDTO pageDTO);

    /**
    * 查询全部会议管理集合
    *
    */
    public List<TsMeeting> findAllTsMeetings();

    /**
    * 查询所有会议管理集合(只提取ID 和 Name)
    *
    */
    public List<TsMeeting> findAllTsMeetingsWithIdName();

    /**
    * 根据名称查询会议管理集合(只提取ID 和 Name)
    *
    * @param tsMeetingName 名称
    */
    public List<TsMeeting> findTsMeetingsWithIdNameByName(@Param("tsMeetingName") String tsMeetingName);

    /**
    * 根据ID查询指定的会议管理(只提取ID 和 Name)
    *
    * @param tsMeetingId Id
    */
    public TsMeeting findTsMeetingsWithIdNameById(@Param(" tsMeetingId") Long tsMeetingId);

    /**
    * 根据分页参数查询会议管理集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTsMeetingTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的会议管理
    *
    * @param tsMeetingId Id
    */
    public TsMeeting findTsMeeting(@Param("tsMeetingId") Long tsMeetingId);

    /**
    * 根据ID查询指定的会议管理(包含外键)
    *
    * @param tsMeetingId Id
    */
    public TsMeeting findTsMeetingWithForeignName(@Param("tsMeetingId") Long tsMeetingId);

    /**
    * 新增会议管理
    *
    * @param tsMeeting 实体对象
    */
    public Long saveTsMeeting(TsMeeting tsMeeting);

    /**
    * 更新会议管理
    *
    * @param tsMeeting 实体对象
    */
    public Long updateTsMeeting(TsMeeting tsMeeting);

    /**
    * 根据ID删除会议管理
    *
    * @param tsMeetingId ID
    */
    public Long deleteTsMeeting(@Param("tsMeetingId") Long tsMeetingId);
}
