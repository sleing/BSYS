package com.mt.ams.service.awardeeEntity;

import com.mt.ams.entity.awardeeEntity.Awardee;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface AwardeeService {
    /**
     * 根据分页参数查询获奖学生信息集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findAwardees(PageDTO pageDTO);

    /**
     * 查询全部获奖学生信息集合
     */
    public List<Awardee> findAllAwardees();

    /**
     * 根据名称查询获奖学生信息集合(只提取ID 和 Name)
     *
     * @param awardeeName 名称
     */
    public List<Awardee> findAwardeesWithIdNameByName(String awardeeName);

    /**
     * 查询所有获奖学生信息集合(只提取ID 和 Name)
     */
    public List<Awardee> findAllAwardeesWithIdName();

    /**
     * 根据ID查询指定的获奖学生信息(只提取ID 和 Name)
     *
     * @param awardeeId Id
     */
    public Awardee findAwardeesWithIdNameById(Long awardeeId);

    /**
     * 根据ID查询指定的获奖学生信息
     *
     * @param awardeeId Id
     */
    public Awardee findAwardee(Long awardeeId);

    /**
     * 根据ID查询指定的获奖学生信息(包含外键)
     *
     * @param awardeeId Id
     */
    public Awardee findAwardeeWithForeignName(Long awardeeId);

    /**
     * 新增获奖学生信息
     *
     * @param awardee 实体对象
     */
    public Awardee saveAwardee(Awardee awardee);

    /**
     * 更新获奖学生信息
     *
     * @param awardee 实体对象
     */
    public Awardee updateAwardee(Awardee awardee);

    /**
     * 根据ID删除获奖学生信息
     *
     * @param awardeeId ID
     */
    public void deleteAwardee(Long awardeeId);

    /**
     * 根据awardId删除获奖学生信息
     *
     * @param awardId ID
     */
    public void deleteAwardeeByAwardId(@RequestParam Long awardId);

    /**
     * 根据awardId获得获奖学生信息
     *
     * @param awardId ID
     */
    public List<Awardee> findAwardeeByAwardId(Long awardId);

    public void exportExcel(HttpServletResponse response, HttpServletRequest request) throws IOException;
}
