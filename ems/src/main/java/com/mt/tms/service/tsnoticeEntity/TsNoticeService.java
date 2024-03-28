package com.mt.tms.service.tsnoticeEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsnoticeEntity.TsNotice;

import java.util.List;

public interface TsNoticeService {
    /**
     * 根据分页参数查询通知管理集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTsNotices(PageDTO pageDTO);

    /**
     * 查询全部通知管理集合
     *
     */
    public List<TsNotice> findAllTsNotices();

    /**
     * 根据名称查询通知管理集合(只提取ID 和 Name)
     *
     * @param tsNoticeName 名称
     */
    public List<TsNotice> findTsNoticesWithIdNameByName(String tsNoticeName);

    /**
     * 查询所有通知管理集合(只提取ID 和 Name)
     *
     */
    public List<TsNotice> findAllTsNoticesWithIdName();

    /**
     * 根据ID查询指定的通知管理(只提取ID 和 Name)
     *
     * @param tsNoticeId Id
     */
    public TsNotice findTsNoticesWithIdNameById(Long tsNoticeId);

    /**
     * 根据ID查询指定的通知管理
     *
     * @param tsNoticeId Id
     */
    public TsNotice findTsNotice(Long tsNoticeId);

    /**
     * 根据ID查询指定的通知管理(包含外键)
     *
     * @param tsNoticeId Id
     */
    public TsNotice findTsNoticeWithForeignName(Long tsNoticeId);

    /**
     * 新增通知管理
     *
     * @param tsNotice 实体对象
     */
    public TsNotice saveTsNotice(TsNotice tsNotice);

    /**
     * 更新通知管理
     *
     * @param tsNotice 实体对象
     */
    public TsNotice updateTsNotice(TsNotice tsNotice);

    /**
     * 根据ID删除通知管理
     *
     * @param tsNoticeId ID
     */
    public void deleteTsNotice(Long tsNoticeId);

    /**
     * 查询所有未审核通知
     * @param pageDTO
     */
    public PageResultDTO findTsNoticesWithoutAudition(PageDTO pageDTO);

    /**
     * 审核通过
     * @param eid
     * @return
     */
    void receiveEvent(Long eid,String remark);

    /**
     * 驳回
     * @param eid
     * @return
     */
    void rejectEvent(Long eid,String remark);

    /**
     * 邮箱通知
     * @param subject
     * @param body
     */
    void sendEmail(String subject, String body);
}
