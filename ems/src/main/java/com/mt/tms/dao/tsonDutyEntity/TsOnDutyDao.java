package com.mt.tms.dao.tsonDutyEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsonDutyEntity.TsOnDuty;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "tsOnDutyDao")
public interface TsOnDutyDao {

    /**
    * 根据分页参数查询值班管理集合
    *
    * @param pageDTO 分页条件
    */
    public List<TsOnDuty> findTsOnDutys(PageDTO pageDTO);

    /**
    * 查询全部值班管理集合
    *
    */
    public List<TsOnDuty> findAllTsOnDutys();

    /**
    * 查询所有值班管理集合(只提取ID 和 Name)
    *
    */
    public List<TsOnDuty> findAllTsOnDutysWithIdName();

    /**
    * 根据名称查询值班管理集合(只提取ID 和 Name)
    *
    * @param tsOnDutyName 名称
    */
    public List<TsOnDuty> findTsOnDutysWithIdNameByName(@Param("tsOnDutyName") String tsOnDutyName);

    /**
    * 根据ID查询指定的值班管理(只提取ID 和 Name)
    *
    * @param tsOnDutyId Id
    */
    public TsOnDuty findTsOnDutysWithIdNameById(@Param(" tsOnDutyId") Long tsOnDutyId);

    /**
    * 根据分页参数查询值班管理集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTsOnDutyTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的值班管理
    *
    * @param tsOnDutyId Id
    */
    public TsOnDuty findTsOnDuty(@Param("tsOnDutyId") Long tsOnDutyId);

    /**
    * 根据ID查询指定的值班管理(包含外键)
    *
    * @param tsOnDutyId Id
    */
    public TsOnDuty findTsOnDutyWithForeignName(@Param("tsOnDutyId") Long tsOnDutyId);

    /**
    * 新增值班管理
    *
    * @param tsOnDuty 实体对象
    */
    public Long saveTsOnDuty(TsOnDuty tsOnDuty);

    /**
    * 更新值班管理
    *
    * @param tsOnDuty 实体对象
    */
    public Long updateTsOnDuty(TsOnDuty tsOnDuty);

    /**
    * 根据ID删除值班管理
    *
    * @param tsOnDutyId ID
    */
    public Long deleteTsOnDuty(@Param("tsOnDutyId") Long tsOnDutyId);
}
