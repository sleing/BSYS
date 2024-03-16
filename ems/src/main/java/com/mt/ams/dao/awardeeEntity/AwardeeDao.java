package com.mt.ams.dao.awardeeEntity;

import com.mt.ams.entity.awardeeEntity.Awardee;
import com.mt.common.core.web.base.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
@Component(value = "awardeeDao")
public interface AwardeeDao {

    /**
     * 根据分页参数查询获奖学生信息集合
     *
     * @param pageDTO 分页条件
     */
    public List<Awardee> findAwardees(PageDTO pageDTO);

    /**
     * 查询全部获奖学生信息集合
     */
    public List<Awardee> findAllAwardees();

    /**
     * 查询所有获奖学生信息集合(只提取ID 和 Name)
     */
    public List<Awardee> findAllAwardeesWithIdName();

    /**
     * 根据名称查询获奖学生信息集合(只提取ID 和 Name)
     *
     * @param awardeeName 名称
     */
    public List<Awardee> findAwardeesWithIdNameByName(@Param("awardeeName") String awardeeName);

    /**
     * 根据ID查询指定的获奖学生信息(只提取ID 和 Name)
     *
     * @param awardeeId Id
     */
    public Awardee findAwardeesWithIdNameById(@Param(" awardeeId") Long awardeeId);

    /**
     * 根据分页参数查询获奖学生信息集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findAwardeeTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的获奖学生信息
     *
     * @param awardeeId Id
     */
    public Awardee findAwardee(@Param("awardeeId") Long awardeeId);

    /**
     * 根据ID查询指定的获奖学生信息(包含外键)
     *
     * @param awardeeId Id
     */
    public Awardee findAwardeeWithForeignName(@Param("awardeeId") Long awardeeId);

    /**
     * 新增获奖学生信息
     *
     * @param awardee 实体对象
     */
    public Long saveAwardee(Awardee awardee);

    /**
     * 更新获奖学生信息
     *
     * @param awardee 实体对象
     */
    public Long updateAwardee(Awardee awardee);

    /**
     * 根据ID删除获奖学生信息
     *
     * @param awardeeId ID
     */
    public Long deleteAwardee(@Param("awardeeId") Long awardeeId);

    /**
     * 根据awardId删除获奖学生信息
     *
     * @param awardId ID
     */
    public Long deleteAwardeeByAwardId(@RequestParam Long awardId);

    /**
     * 根据awardId获取获奖学生信息
     *
     * @param awardId ID
     */
    public List<Awardee> findAwardeeByAwardId(@RequestParam Long awardId);

    public List<Long> findStudentNameById(Long awardId);

}
