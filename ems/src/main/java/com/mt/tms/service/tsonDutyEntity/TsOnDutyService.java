package com.mt.tms.service.tsonDutyEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsonDutyEntity.TsOnDuty;

import java.util.List;

public interface TsOnDutyService {
    /**
     * 根据分页参数查询值班管理集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTsOnDutys(PageDTO pageDTO);

    /**
     * 查询全部值班管理集合
     *
     */
    public List<TsOnDuty> findAllTsOnDutys();

    /**
     * 根据名称查询值班管理集合(只提取ID 和 Name)
     *
     * @param tsOnDutyName 名称
     */
    public List<TsOnDuty> findTsOnDutysWithIdNameByName(String tsOnDutyName);

    /**
     * 查询所有值班管理集合(只提取ID 和 Name)
     *
     */
    public List<TsOnDuty> findAllTsOnDutysWithIdName();

    /**
     * 根据ID查询指定的值班管理(只提取ID 和 Name)
     *
     * @param tsOnDutyId Id
     */
    public TsOnDuty findTsOnDutysWithIdNameById(Long tsOnDutyId);

    /**
     * 根据ID查询指定的值班管理
     *
     * @param tsOnDutyId Id
     */
    public TsOnDuty findTsOnDuty(Long tsOnDutyId);

    /**
     * 根据ID查询指定的值班管理(包含外键)
     *
     * @param tsOnDutyId Id
     */
    public TsOnDuty findTsOnDutyWithForeignName(Long tsOnDutyId);

    /**
     * 新增值班管理
     *
     * @param tsOnDuty 实体对象
     */
    public TsOnDuty saveTsOnDuty(TsOnDuty tsOnDuty);

    /**
     * 更新值班管理
     *
     * @param tsOnDuty 实体对象
     */
    public TsOnDuty updateTsOnDuty(TsOnDuty tsOnDuty);

    /**
     * 根据ID删除值班管理
     *
     * @param tsOnDutyId ID
     */
    public void deleteTsOnDuty(Long tsOnDutyId);
}
