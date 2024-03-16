package com.mt.ams.dao.instructorEntity;

import com.mt.ams.entity.instructorEntity.Instructor;
import com.mt.common.core.web.base.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
@Component(value = "instructorDao")
public interface InstructorDao {

    /**
     * 根据分页参数查询指导老师信息集合
     *
     * @param pageDTO 分页条件
     */
    public List<Instructor> findInstructors(PageDTO pageDTO);

    /**
     * 查询全部指导老师信息集合
     */
    public List<Instructor> findAllInstructors();

    /**
     * 查询所有指导老师信息集合(只提取ID 和 Name)
     */
    public List<Instructor> findAllInstructorsWithIdName();

    /**
     * 根据名称查询指导老师信息集合(只提取ID 和 Name)
     *
     * @param instructorName 名称
     */
    public List<Instructor> findInstructorsWithIdNameByName(@Param("instructorName") String instructorName);

    /**
     * 根据ID查询指定的指导老师信息(只提取ID 和 Name)
     *
     * @param instructorId Id
     */
    public Instructor findInstructorsWithIdNameById(@Param(" instructorId") Long instructorId);

    /**
     * 根据分页参数查询指导老师信息集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findInstructorTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的指导老师信息
     *
     * @param instructorId Id
     */
    public Instructor findInstructor(@Param("instructorId") Long instructorId);

    /**
     * 根据ID查询指定的指导老师信息(包含外键)
     *
     * @param instructorId Id
     */
    public Instructor findInstructorWithForeignName(@Param("instructorId") Long instructorId);

    /**
     * 新增指导老师信息
     *
     * @param instructor 实体对象
     */
    public Long saveInstructor(Instructor instructor);

    /**
     * 更新指导老师信息
     *
     * @param instructor 实体对象
     */
    public Long updateInstructor(Instructor instructor);

    /**
     * 根据ID删除指导老师信息
     *
     * @param instructorId ID
     */
    public Long deleteInstructor(@Param("instructorId") Long instructorId);

    /**
     * 根据awardId删除指导老师信息
     *
     * @param awardId ID
     */
    public Long deleteInstructorByAwardId(@RequestParam Long awardId);

    /**
     * 根据awardId查询指导老师信息
     *
     * @param awardId ID
     */
    public List<Instructor> findInstructorByAwardId(Long awardId);
}
