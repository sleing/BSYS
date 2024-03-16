package com.mt.tms.service.tsCollegeEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.tms.entity.tsCollegeEntity.TsCollege;

import java.util.List;

public interface TsCollegeService {
    /**
     * 根据分页参数查询院系信息集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTsColleges(PageDTO pageDTO);

    /**
     * 查询全部院系信息集合
     *
     */
    public List<TsCollege> findAllTsColleges();

    /**
     * 根据名称查询院系信息集合(只提取ID 和 Name)
     *
     * @param tsCollegeName 名称
     */
    public List<TsCollege> findTsCollegesWithIdNameByName(String tsCollegeName);

    /**
     * 查询所有院系信息集合(只提取ID 和 Name)
     *
     */
    public List<TsCollege> findAllTsCollegesWithIdName();

    /**
     * 根据ID查询指定的院系信息(只提取ID 和 Name)
     *
     * @param tsCollegeId Id
     */
    public TsCollege findTsCollegesWithIdNameById(Long tsCollegeId);

    /**
     * 根据ID查询指定的院系信息
     *
     * @param tsCollegeId Id
     */
    public TsCollege findTsCollege(Long tsCollegeId);

    /**
     * 根据ID查询指定的院系信息(包含外键)
     *
     * @param tsCollegeId Id
     */
    public TsCollege findTsCollegeWithForeignName(Long tsCollegeId);

    /**
     * 新增院系信息
     *
     * @param tsCollege 实体对象
     */
    public TsCollege saveTsCollege(TsCollege tsCollege);

    /**
     * 更新院系信息
     *
     * @param tsCollege 实体对象
     */
    public TsCollege updateTsCollege(TsCollege tsCollege);

    /**
     * 根据ID删除院系信息
     *
     * @param tsCollegeId ID
     */
    public void deleteTsCollege(Long tsCollegeId);
}
