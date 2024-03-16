package com.mt.common.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mt.common.system.entity.Role;
import com.mt.common.system.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色Mapper接口
 * Created by wangfan on 2018-12-24 16:10
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 批量添加用户角色
     */
    Long insertBatch(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    /**
     * 批量查询用户角色
     */
    List<Role> listByUserIds(@Param("userIds") List<Long> userIds);

    /**
     * 查询某个用户的角色
     */
    List<Role> listByUserId(@Param("userId") Long userId);

    /**
     * 查询用户的角色的编码
     */
    List<String> listCodesByUserId(@Param("userId") Long userId);

    //添加到user_role表中
    void insertBatchSingle(@Param("userId")Long userId,@Param("roleId")Long roleId);

}
