package com.mt.common.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mt.common.core.annotation.OperLog;
import com.mt.common.core.web.BaseController;
import com.mt.common.core.web.JsonResult;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.system.entity.Menu;
import com.mt.common.system.entity.RoleMenu;
import com.mt.common.system.entity.User;
import com.mt.common.system.service.MenuService;
import com.mt.common.system.service.RoleMenuService;
import com.mt.common.system.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangfan on 2018-12-24 16:10
 */
@Api(tags = "角色菜单管理")
@RestController
@RequestMapping("/api/sys/role/menu")
public class RoleMenuController extends BaseController {
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Resource
    private RedisTemplate<String, User> redisTemplate;

    /**
     * 查询角色菜单
     */
    @PreAuthorize("hasAuthority('sys:role:list')")
    @OperLog(value = "角色管理", desc = "查询角色菜单")
    @ResponseBody
    @GetMapping()
    public JsonResult list(Long roleId) {
        List<Menu> menus = menuService.list(new QueryWrapper<Menu>().orderByAsc("sort_number"));
        List<RoleMenu> roleMenus = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
        for (Menu menu : menus) {
            menu.setOpen(true);
            menu.setChecked(false);
            for (RoleMenu roleMenu : roleMenus) {
                if (menu.getMenuId().equals(roleMenu.getMenuId())) {
                    menu.setChecked(true);
                    break;
                }
            }
        }
        return JsonResult.ok().setData(menus);
    }

    /**
     * 添加角色菜单
     */
    @PreAuthorize("hasAuthority('sys:role:update')")
    @OperLog(value = "角色管理", desc = "添加角色菜单")
    @ResponseBody
    @PostMapping()
    public JsonResult addRoleAuth(Long roleId, Long menuId) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        roleMenu.setMenuId(menuId);
        if (roleMenuService.save(roleMenu)) {
            return JsonResult.ok();
        }
        return JsonResult.error();
    }

    /**
     * 移除角色菜单
     */
    @PreAuthorize("hasAuthority('sys:role:update')")
    @OperLog(value = "角色管理", desc = "移除角色菜单")
    @ResponseBody
    @DeleteMapping()
    public JsonResult removeRoleAuth(Long roleId, Long menuId) {
        if (roleMenuService.remove(new UpdateWrapper<RoleMenu>()
                .eq("role_id", roleId).eq("menuId", menuId))) {
            return JsonResult.ok();
        }
        return JsonResult.error();
    }

    /**
     * 批量修改角色菜单
     */
    @PreAuthorize("hasAuthority('sys:role:update')")
    @OperLog(value = "角色管理", desc = "修改角色菜单")
    @Transactional
    @ResponseBody
    @PutMapping("/{id}")
    public JsonResult setRoleAuth(@PathVariable("id") Long roleId, @RequestBody List<Long> menuIds) {
        roleMenuService.remove(new UpdateWrapper<RoleMenu>().eq("role_id", roleId));
        if (menuIds.size() > 0) {
            List<RoleMenu> roleMenuList = new ArrayList<>();
            for (Long menuId : menuIds) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenuList.add(roleMenu);
            }
            if (roleMenuService.saveBatch(roleMenuList)) {
                userService.decache();
                return JsonResult.ok("保存成功");
            } else {
                throw new BusinessException("操作失败");
            }
        }
        return JsonResult.ok("保存成功");
    }

}
