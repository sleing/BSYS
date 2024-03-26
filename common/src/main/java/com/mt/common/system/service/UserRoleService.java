package com.mt.common.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mt.common.system.entity.User;
import com.mt.common.system.entity.UserRole;

import java.util.List;

/**
 * 用户角色服务类
 * Created by wangfan on 2018-12-24 16:10
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 查询用户的角色id
     */
    Integer[] getRoleIds(String eid);

    public void insertBatchSingle(Long userId,Long roleId);

    /**
     * 批量添加用户角色
     * @param userRoles
     */
    void mutiImportUserRole(List<UserRole> userRoles);
}
