package com.mt.common.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.security.JwtLoginFilter;
import com.mt.common.core.utils.ConfigManager;
import com.mt.common.core.web.*;
import com.mt.common.system.entity.Config;
import com.mt.common.system.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "参数管理")
@RestController
@RequestMapping("/api/sys/con")
@CrossOrigin(allowCredentials = "true")
public class ConfigController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ConfigController.class);
    @Autowired
    private ConfigService configService;

    @Autowired
    private ConfigManager configManager;

    //@PreAuthorize("hasAuthority('sys:con:list')")
    @ApiOperation("分页查询系统参数")
    @ApiPageParam
    @GetMapping("/page")
    public PageResult<Config> page(HttpServletRequest request) {
        System.out.println(configManager.getConfigByName("结算日"));
        PageParam<Config> pageParam = new PageParam<>(request);
        return new PageResult<>(configService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
    }
    /**
     * 新增系统参数
     */
    @ApiOperation("添加参数")
    @PostMapping()
    public JsonResult add(@RequestBody Config config) {
        if (configService.count(new QueryWrapper<Config>().eq("name", config.getName())) > 0) {
            return JsonResult.error("参数名称已存在");
        }
        if (configService.save(config)) {
            configManager.cache();
            return JsonResult.ok("添加成功").put("con",configManager.readFromCache());
        }
        return JsonResult.error("添加失败");
    }
    /**
     * 删除单个系统参数
     */
    @ApiOperation("删除系统参数")
    @DeleteMapping("/{eid}")
    public JsonResult remove(@PathVariable("eid") Long eid) {
        if (configService.removeById(eid)) {
            configManager.cache();
            return JsonResult.ok("删除成功").put("con",configManager.readFromCache());
        }
        return JsonResult.error("删除失败");
    }
    /**
     * 批量删除
     */
    @ApiOperation("批量删除系统参数")
    @DeleteMapping("/batch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (configService.removeByIds(ids)) {
            configManager.cache();
            return JsonResult.ok("删除成功").put("con",configManager.readFromCache());
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 修改系统参数
     * @param
     * @return
     */
    @ApiOperation("修改系统参数")
    @PutMapping()
    public JsonResult update(@RequestBody Config config) {
        if (configService.count(new QueryWrapper<Config>().eq("name", config.getName())
                .ne("eid", config.getEid())) > 0) {
            return JsonResult.error("系统参数名称已存在");
        }
        if (configService.updateById(config)) {
            configManager.cache();
            return JsonResult.ok("修改成功").put("con",configManager.readFromCache());
        }
        return JsonResult.error("修改失败");
    }
}


