package com.ddd.dev.generator.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.annotation.OperLog;
import com.mt.common.core.utils.CoreUtil;
import com.mt.common.core.web.BaseController;
import com.mt.common.core.web.JsonResult;
import com.mt.common.core.web.PageParam;
import com.mt.common.core.web.PageResult;
import com.mt.common.system.entity.Role;
import com.mt.common.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wangfan on 2018-12-24 16:10
 */
@Api(tags = "test")
@RestController
@RequestMapping("/api/generate/test")
public class TestController extends BaseController {


    @OperLog(value = "角色管理", desc = "查询全部")
    @ApiOperation("查询全部角色")
    @GetMapping("/page")
    public JsonResult list(HttpServletRequest request) {
        return JsonResult.ok().setData("ddd");
    }


}
