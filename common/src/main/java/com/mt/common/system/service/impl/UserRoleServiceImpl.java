package com.mt.common.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.common.system.entity.User;
import com.mt.common.system.entity.UserRole;
import com.mt.common.system.mapper.UserRoleMapper;
import com.mt.common.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色服务实现类
 * Created by wangfan on 2018-12-24 16:10
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    public Integer[] getRoleIds(String userId) {
        List<UserRole> userRoles = baseMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", userId));
        Integer[] roleIds = new Integer[userRoles.size()];
        for (int i = 0; i < userRoles.size(); i++) {
            roleIds[i] = userRoles.get(i).getRoleId().intValue();
        }
        return roleIds;
    }


    @Override
    public void insertBatchSingle(Long userId,Long roleId) {
        userRoleMapper.insertBatchSingle(userId,roleId);
    }

    @Override
    public void mutiImportUserRole(List<UserRole> userRoles) {
        userRoleMapper.mutiImportUserRole(userRoles);
    }

}
