package com.mt.ams.dao.appraisedEntity;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ams.entity.appraisedEntity.Appraised;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@Component(value = "appraisedDao")
public interface AppraisedDao {

    /**
     * 根据分页参数查询学生处评优评先关联集合
     *
     * @param pageDTO 分页条件
     */
    public List<Appraised> findAppraiseds(PageDTO pageDTO);

    /**
     * 查询全部学生处评优评先关联集合
     */
    public List<Appraised> findAllAppraiseds();

    /**
     * 查询所有学生处评优评先关联集合(只提取ID 和 Name)
     */
    public List<Appraised> findAllAppraisedsWithIdName();

    /**
     * 根据名称查询学生处评优评先关联集合(只提取ID 和 Name)
     *
     * @param appraisedName 名称
     */
    public List<Appraised> findAppraisedsWithIdNameByName(@Param("appraisedName") String appraisedName);

    /**
     * 根据ID查询指定的学生处评优评先关联(只提取ID 和 Name)
     *
     * @param appraisedId Id
     */
    public Appraised findAppraisedsWithIdNameById(@Param(" appraisedId") Long appraisedId);

    /**
     * 根据分页参数查询学生处评优评先关联集合的数量
     *
     * @param pageDTO 分页条件
     */
    public Long findAppraisedTotalCount(PageDTO pageDTO);

    /**
     * 根据ID查询指定的学生处评优评先关联
     *
     * @param appraisedId Id
     */
    public Appraised findAppraised(@Param("appraisedId") Long appraisedId);

    /**
     * 根据ID查询指定的学生处评优评先关联(包含外键)
     *
     * @param appraisedId Id
     */
    public Appraised findAppraisedWithForeignName(@Param("appraisedId") Long appraisedId);

    /**
     * 新增学生处评优评先关联
     *
     * @param appraised 实体对象
     */
    public Long saveAppraised(Appraised appraised);

    /**
     * 更新学生处评优评先关联
     *
     * @param appraised 实体对象
     */
    public Long updateAppraised(Appraised appraised);

    /**
     * 根据ID删除学生处评优评先关联
     *
     * @param appraisedId ID
     */
    public Long deleteAppraised(@Param("appraisedId") Long appraisedId);
}
