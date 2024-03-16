package com.mt.common.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mt.common.core.web.PageParam;
import com.mt.common.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单Mapper接口
 * Created by wangfan on 2018-12-24 16:10
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 分页查询
     */
    List<Menu> listPage(@Param("page") PageParam<Menu> page);

    /**
     * 根据用户id查询
     */
    List<Menu> listByUserId(@Param("eid") Long userId, @Param("menuType") Long menuType);

    /**
     * 查询所有的授权
     */
    List<Menu> listAuthoritiesByUserId(@Param("eid") Long userId);

    /**
     * 查询地址带#{appUrl}的菜单，这些菜单是直接访问服务的菜单，需要自定义鉴权
     */
    List<Menu> listAppUrlMenus();


}
