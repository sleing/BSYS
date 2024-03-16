package com.mt.common.system.controller;

import com.mt.common.core.annotation.OperLog;
import com.mt.common.core.security.MySecurityMetadataSource;
import com.mt.common.core.web.*;
import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.system.entity.Menu;
import com.mt.common.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wangfan on 2018-12-24 16:10
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/api/sys/menu")
public class MenuController extends BaseController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;

    @PreAuthorize("hasAuthority('sys:menu:list')")
    @OperLog(value = "菜单管理", desc = "分页查询")
    @ApiOperation("分页查询菜单")
    @ApiPageParam
    @GetMapping("/page")
    public PageResult<Menu> page(HttpServletRequest request) {
        PageParam<Menu> pageParam = new PageParam<>(request);
        pageParam.setDefaultOrder(new String[]{"sort_number"}, null);
        return new PageResult<>(menuService.listPage(pageParam), pageParam.getTotal());
    }

    @PreAuthorize("hasAuthority('sys:menu:list')")
    @OperLog(value = "菜单管理", desc = "查询全部")
    @ApiOperation("查询全部菜单")
    @GetMapping()
    public JsonResult list(HttpServletRequest request) {
        PageParam<Menu> pageParam = new PageParam<>(request);
        pageParam.setDefaultOrder(new String[]{"sort_number"}, null);
        return JsonResult.ok().setData(menuService.list(pageParam.getOrderWrapper()));
    }

    @PreAuthorize("hasAuthority('sys:menu:list')")
    @OperLog(value = "菜单管理", desc = "根据id查询")
    @ApiOperation("根据id查询菜单")
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id") Long id) {
        return JsonResult.ok().setData(menuService.getById(id));
    }

    @OperLog(value = "菜单管理", desc = "添加", param = false, result = true)
    @ApiOperation("添加菜单")
    @PostMapping()
    public JsonResult add(@RequestBody Menu menu) {
        if (menuService.save(menu)) {
            //重新加载菜单
            this.mySecurityMetadataSource.loadResource();
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    @PreAuthorize("hasAuthority('sys:menu:update')")
    @OperLog(value = "菜单管理", desc = "修改", param = false, result = true)
    @ApiOperation("修改菜单")
    @PutMapping()
    public JsonResult update(@RequestBody Menu menu) {
        if (menuService.updateById(menu)) {
            //重新加载菜单
            this.mySecurityMetadataSource.loadResource();
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    @PreAuthorize("hasAuthority('sys:menu:remove')")
    @OperLog(value = "菜单管理", desc = "删除", result = true)
    @ApiOperation("删除菜单")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id") Long id) {
        if (menuService.removeById(id)) {
            //重新加载菜单
            this.mySecurityMetadataSource.loadResource();
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    @PreAuthorize("hasAuthority('sys:menu:save')")
    @OperLog(value = "菜单管理", desc = "批量添加", param = false, result = true)
    @ApiOperation("批量添加菜单")
    @PostMapping("/batch")
    public JsonResult saveBatch(@RequestBody List<Menu> menuList) {
        if (menuService.saveBatch(menuList)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    @PreAuthorize("hasAuthority('sys:menu:update')")
    @OperLog(value = "菜单管理", desc = "批量修改", result = true)
    @ApiOperation("批量修改菜单")
    @PutMapping("/batch")
    public JsonResult updateBatch(@RequestBody BatchParam<Menu> batchParam) {
        if (batchParam.update(menuService, "menu_id")) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    @PreAuthorize("hasAuthority('sys:menu:remove')")
    @OperLog(value = "菜单管理", desc = "批量删除", result = true)
    @ApiOperation("批量删除菜单")
    @DeleteMapping("/batch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (menuService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}
