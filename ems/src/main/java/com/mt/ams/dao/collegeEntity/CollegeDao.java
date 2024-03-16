package com.mt.ams.dao.collegeEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ams.entity.collegeEntity.College;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@Component(value = "collegeDao")
public interface CollegeDao {

    /**
     * 根据分页参数查询学院信息集合
     *
     * @param pageDTO 分页条件
     */
    public List<College> findColleges(PageDTO pageDTO);

    /**
     * 查询全部学院信息集合
     */
    public List<College> findAllColleges();

    /**
     * 查询所有学院信息集合(只提取ID 和 Name)
     */
    public List<College> findAllCollegesWithIdName();

    /**
     * 根据名称查询学院信息集合(只提取ID 和 Name)
     *
     * @param collegeName 名称
     */
    public List<College> findCollegesWithIdNameByName(@Param("collegeName") String collegeName);

    /**
     * 根据ID查询指定的学院信息(只提取ID 和 Name)
     *
     * @param collegeId Id
     */
    public College findCollegesWithIdNameById(@Param(" collegeId") Long collegeId);

    /**
     * 根据分页参数查询学院信息集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findCollegeTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的学院信息
     *
     * @param collegeId Id
     */
    public College findCollege(@Param("collegeId") Long collegeId);

    /**
     * 根据ID查询指定的学院信息(包含外键)
     *
     * @param collegeId Id
     */
    public College findCollegeWithForeignName(@Param("collegeId") Long collegeId);

    /**
     * 新增学院信息
     *
     * @param college 实体对象
     */
    public Long saveCollege(College college);

    /**
     * 更新学院信息
     *
     * @param college 实体对象
     */
    public Long updateCollege(College college);

    /**
     * 根据ID删除学院信息
     *
     * @param collegeId ID
     */
    public Long deleteCollege(@Param("collegeId") Long collegeId);
}
