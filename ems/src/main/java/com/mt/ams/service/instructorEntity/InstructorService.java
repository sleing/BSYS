package com.mt.ams.service.instructorEntity;

import com.mt.ams.entity.instructorEntity.Instructor;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface InstructorService {
    /**
     * 根据分页参数查询指导老师信息集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findInstructors(PageDTO pageDTO);

    /**
     * 查询全部指导老师信息集合
     */
    public List<Instructor> findAllInstructors();

    /**
     * 根据名称查询指导老师信息集合(只提取ID 和 Name)
     *
     * @param instructorName 名称
     */
    public List<Instructor> findInstructorsWithIdNameByName(String instructorName);

    /**
     * 查询所有指导老师信息集合(只提取ID 和 Name)
     */
    public List<Instructor> findAllInstructorsWithIdName();

    /**
     * 根据ID查询指定的指导老师信息(只提取ID 和 Name)
     *
     * @param instructorId Id
     */
    public Instructor findInstructorsWithIdNameById(Long instructorId);

    /**
     * 根据ID查询指定的指导老师信息
     *
     * @param instructorId Id
     */
    public Instructor findInstructor(Long instructorId);

    /**
     * 根据ID查询指定的指导老师信息(包含外键)
     *
     * @param instructorId Id
     */
    public Instructor findInstructorWithForeignName(Long instructorId);

    /**
     * 新增指导老师信息
     *
     * @param instructor 实体对象
     */
    public Instructor saveInstructor(Instructor instructor);

    /**
     * 更新指导老师信息
     *
     * @param instructor 实体对象
     */
    public Instructor updateInstructor(Instructor instructor);

    /**
     * 根据ID删除指导老师信息
     *
     * @param instructorId ID
     */
    public void deleteInstructor(Long instructorId);

    /**
     * 根据awardId删除指导老师信息
     *
     * @param awardId ID
     */
    public void deleteInstructorByAwardId(@RequestParam Long awardId);

    /**
     * 根据awardId查询指导老师信息
     *
     * @param awardId ID
     */
    public List<Instructor> findInstructorByAwardId(@RequestParam Long awardId);
}
