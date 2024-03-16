package com.mt.ams.service.collegeEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ams.entity.collegeEntity.College;

import java.util.List;

public interface CollegeService {
    /**
     * 根据分页参数查询学院信息集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findColleges(PageDTO pageDTO);

    /**
     * 查询全部学院信息集合
     */
    public List<College> findAllColleges();

    /**
     * 根据名称查询学院信息集合(只提取ID 和 Name)
     *
     * @param collegeName 名称
     */
    public List<College> findCollegesWithIdNameByName(String collegeName);

    /**
     * 查询所有学院信息集合(只提取ID 和 Name)
     */
    public List<College> findAllCollegesWithIdName();

    /**
     * 根据ID查询指定的学院信息(只提取ID 和 Name)
     *
     * @param collegeId Id
     */
    public College findCollegesWithIdNameById(Long collegeId);

    /**
     * 根据ID查询指定的学院信息
     *
     * @param collegeId Id
     */
    public College findCollege(Long collegeId);

    /**
     * 根据ID查询指定的学院信息(包含外键)
     *
     * @param collegeId Id
     */
    public College findCollegeWithForeignName(Long collegeId);

    /**
     * 新增学院信息
     *
     * @param college 实体对象
     */
    public College saveCollege(College college);

    /**
     * 更新学院信息
     *
     * @param college 实体对象
     */
    public College updateCollege(College college);

    /**
     * 根据ID删除学院信息
     *
     * @param collegeId ID
     */
    public void deleteCollege(Long collegeId);
}
