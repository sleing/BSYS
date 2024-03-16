package com.mt.common.system.controller;

import cn.hutool.core.util.StrUtil;
import com.mt.common.core.annotation.OperLog;
import com.mt.common.core.utils.ConfigManager;
import com.mt.common.core.utils.DictManager;
import com.mt.common.core.web.BaseController;
import com.mt.common.core.web.JsonResult;
import com.mt.common.system.entity.Menu;
import com.mt.common.system.entity.User;
import com.mt.common.system.service.MenuService;
import com.mt.common.system.service.UserService;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangfan on 2018-12-24 16:10
 */
@Api(tags = "登录认证")
@RestController
@RequestMapping("/api")
public class MainController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @Autowired
    private DictManager dictManager;

    @Autowired
    private ConfigManager configManager;

    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/login")
    public void login(String username, String password) {
        // 登录操作由JwtLoginFilter完成
    }
    @PostMapping("/logout")
    public void logout() {
        System.err.println("dddddddddddddddddddd");
        // 登录操作由JwtLoginFilter完成
    }

    @ApiOperation("获取登录用户菜单")
    @GetMapping("/main/menu")
    public JsonResult userMenu() {
        List<Menu> userMenu = menuService.getUserMenu(this.getLoginUserId(), Menu.TYPE_MENU);
        User user = userService.getFullById(getLoginUserId());

        JsonResult jsonResult = JsonResult.ok();
        Map resultUser = new HashMap();
        jsonResult.setData(resultUser);

        List<Menu> menus = menuService.toMenuTree(userMenu, 0L);
        resultUser.put("menus",menus);

        resultUser.put("avatar",user.getAvatar());
        resultUser.put("nickname",user.getNickname());
        resultUser.put("userType",user.getUserType());
        resultUser.put("eid",user.getEid());
        resultUser.put("organizationName",user.getOrganizationName());

        List roleCodes = new ArrayList();
        user.getRoles().forEach(role -> {
            roleCodes.add(role.getRoleCode());
        });
        resultUser.put("roles",roleCodes);

        List authorities = new ArrayList();
        user.getAuthorities().forEach(authority->{
            authorities.add(authority.getAuthority());
        });
        resultUser.put("authorities",authorities);

        resultUser.put("configs",configManager.readFromCache());
        resultUser.put("dicts",dictManager.readFromCache());
        return jsonResult;
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping("/main/user")
    public JsonResult userInfo() {
        return this.userMenu();
//        return JsonResult.ok().setData(userService.getFullById(getLoginUserId()));
    }

    @PreAuthorize("hasAuthority('main:user:update')")
    @OperLog(value = "登录认证", desc = "修改个人信息", param = false, result = true)
    @ApiOperation("修改个人信息")
    @PutMapping("/main/user")
    public JsonResult updateInfo(User user) {
        user.setEid(getLoginUserId());
        // 不能修改的字段
        user.setState(null);
        user.setPassword(null);
        user.setUsername(null);
        user.setOrganizationId(null);
        if (userService.updateById(user)) {
            User loginUser = this.userService.getById(this.getLoginUserId());
            if (user.getNickname() != null) loginUser.setNickname(user.getNickname());
            if (user.getAvatar() != null) loginUser.setAvatar(user.getAvatar());
            return JsonResult.ok("保存成功").setData(userService.getFullById(user.getEid()));
        }
        return JsonResult.error("保存失败");
    }

    @PreAuthorize("hasAuthority('main:user:password')")
    @OperLog(value = "登录认证", desc = "修改自己密码", param = false, result = true)
    @ApiOperation("修改自己密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPsw", value = "旧密码", required = true, dataType = "string"),
            @ApiImplicitParam(name = "newPsw", value = "新密码", required = true, dataType = "string")
    })
    @PutMapping("/main/password")
    public JsonResult updatePsw(String oldPsw, String newPsw, HttpServletRequest request) {
        System.out.println("老密码："+oldPsw);
        System.out.println("新密码："+newPsw);
        oldPsw = request.getParameter("oldPsw");
        newPsw = request.getParameter("newPsw");
        if (StrUtil.hasBlank(oldPsw, newPsw)) {
            return JsonResult.error("参数不能为空");
        }
        if (getLoginUserId() == null) {
            return JsonResult.error("未登录");
        }
        if (!userService.comparePsw(userService.getById(getLoginUserId()).getPassword(), oldPsw)) {
            return JsonResult.error("原密码输入不正确");
        }
        User user = new User();
        user.setEid(getLoginUserId());
        user.setPassword(userService.encodePsw(newPsw));
        if (userService.updateById(user)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    @ApiOperation("图形验证码")
    @GetMapping("/file/captcha")
    public JsonResult captcha(HttpServletRequest request) {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        return JsonResult.ok().setData(specCaptcha.toBase64()).put("text", specCaptcha.text().toLowerCase());
    }

}
