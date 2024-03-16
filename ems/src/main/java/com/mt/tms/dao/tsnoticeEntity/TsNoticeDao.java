package com.mt.tms.dao.tsnoticeEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsnoticeEntity.TsNotice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "tsNoticeDao")
public interface TsNoticeDao {

    /**
    * 根据分页参数查询通知管理集合
    *
    * @param pageDTO 分页条件
    */
    public List<TsNotice> findTsNotices(PageDTO pageDTO);

    /**
    * 查询全部通知管理集合
    *
    */
    public List<TsNotice> findAllTsNotices();

    /**
    * 查询所有通知管理集合(只提取ID 和 Name)
    *
    */
    public List<TsNotice> findAllTsNoticesWithIdName();

    /**
    * 根据名称查询通知管理集合(只提取ID 和 Name)
    *
    * @param tsNoticeName 名称
    */
    public List<TsNotice> findTsNoticesWithIdNameByName(@Param("tsNoticeName") String tsNoticeName);

    /**
    * 根据ID查询指定的通知管理(只提取ID 和 Name)
    *
    * @param tsNoticeId Id
    */
    public TsNotice findTsNoticesWithIdNameById(@Param(" tsNoticeId") Long tsNoticeId);

    /**
    * 根据分页参数查询通知管理集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTsNoticeTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的通知管理
    *
    * @param tsNoticeId Id
    */
    public TsNotice findTsNotice(@Param("tsNoticeId") Long tsNoticeId);

    /**
    * 根据ID查询指定的通知管理(包含外键)
    *
    * @param tsNoticeId Id
    */
    public TsNotice findTsNoticeWithForeignName(@Param("tsNoticeId") Long tsNoticeId);

    /**
    * 新增通知管理
    *
    * @param tsNotice 实体对象
    */
    public Long saveTsNotice(TsNotice tsNotice);

    /**
    * 更新通知管理
    *
    * @param tsNotice 实体对象
    */
    public Long updateTsNotice(TsNotice tsNotice);

    /**
    * 根据ID删除通知管理
    *
    * @param tsNoticeId ID
    */
    public Long deleteTsNotice(@Param("tsNoticeId") Long tsNoticeId);
}
