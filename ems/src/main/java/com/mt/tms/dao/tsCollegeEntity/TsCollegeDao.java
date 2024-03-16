package com.mt.tms.dao.tsCollegeEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsCollegeEntity.TsCollege;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "tsCollegeDao")
public interface TsCollegeDao {

    /**
    * 根据分页参数查询院系信息集合
    *
    * @param pageDTO 分页条件
    */
    public List<TsCollege> findTsColleges(PageDTO pageDTO);

    /**
    * 查询全部院系信息集合
    *
    */
    public List<TsCollege> findAllTsColleges();

    /**
    * 查询所有院系信息集合(只提取ID 和 Name)
    *
    */
    public List<TsCollege> findAllTsCollegesWithIdName();

    /**
    * 根据名称查询院系信息集合(只提取ID 和 Name)
    *
    * @param tsCollegeName 名称
    */
    public List<TsCollege> findTsCollegesWithIdNameByName(@Param("tsCollegeName") String tsCollegeName);

    /**
    * 根据ID查询指定的院系信息(只提取ID 和 Name)
    *
    * @param tsCollegeId Id
    */
    public TsCollege findTsCollegesWithIdNameById(@Param(" tsCollegeId") Long tsCollegeId);

    /**
    * 根据分页参数查询院系信息集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTsCollegeTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的院系信息
    *
    * @param tsCollegeId Id
    */
    public TsCollege findTsCollege(@Param("tsCollegeId") Long tsCollegeId);

    /**
    * 根据ID查询指定的院系信息(包含外键)
    *
    * @param tsCollegeId Id
    */
    public TsCollege findTsCollegeWithForeignName(@Param("tsCollegeId") Long tsCollegeId);

    /**
    * 新增院系信息
    *
    * @param tsCollege 实体对象
    */
    public Long saveTsCollege(TsCollege tsCollege);

    /**
    * 更新院系信息
    *
    * @param tsCollege 实体对象
    */
    public Long updateTsCollege(TsCollege tsCollege);

    /**
    * 根据ID删除院系信息
    *
    * @param tsCollegeId ID
    */
    public Long deleteTsCollege(@Param("tsCollegeId") Long tsCollegeId);
}
