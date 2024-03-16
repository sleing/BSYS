package com.mt.common.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.security.JwtLoginFilter;
import com.mt.common.core.utils.CoreUtil;
import com.mt.common.core.utils.DictManager;
import com.mt.common.core.web.BaseController;
import com.mt.common.core.web.JsonResult;
import com.mt.common.core.web.PageParam;
import com.mt.common.core.web.PageResult;
import com.mt.common.system.entity.Dict;
import com.mt.common.system.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author 王某某
 */
@Api(tags = "字典管理")
@RestController
@RequestMapping("/api/sys/dictmanagement")
public class DictController extends BaseController {
    @Autowired
    private DictService dictService;

    @Autowired
    private DictManager dictManager;

    @Autowired
    private JwtLoginFilter jwtLoginFilter;

    @PreAuthorize("hasAuthority('sys:dict:list')")
    @ApiOperation("分页查询字典")
    @ApiPageParam
    @GetMapping("/page")
    public PageResult<Dict> page(HttpServletRequest request) {
        PageParam<Dict> pageParam = new PageParam<>(request);
        return new PageResult<>(dictService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
    }

    @PreAuthorize("hasAuthority('sys:dict:list')")
    @ApiOperation("查询全部字典")
    @GetMapping()
    public JsonResult list(HttpServletRequest request) {
        PageParam<Dict> pageParam = new PageParam<>(request);
        List<Dict> list = dictService.list(pageParam.getOrderWrapper());
        return JsonResult.ok().setData(dictService.toDictTree(list, 0));
    }

    @PreAuthorize("hasAuthority('sys:dict:list')")
    @ApiOperation("根据id查询字典")
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id") Integer id) {
        return JsonResult.ok().setData(dictService.getById(id));
    }

    @PreAuthorize("hasAuthority('sys:dict:save')")
    @ApiOperation("添加字典")
    @PostMapping()
    public JsonResult add(@RequestBody Dict dict) {
        if (dictService.count(new QueryWrapper<Dict>().eq("dict_name", dict.getDictName())) > 0) {
            return JsonResult.error("字典名称已存在");
        }
        if (dictService.save(dict)) {
            //将修改后的字典进行redis缓存
            dictManager.cache();
            return JsonResult.ok("添加成功").put("dict", dictManager.readFromCache());
        }
        return JsonResult.error("添加失败");
    }

    @PreAuthorize("hasAuthority('sys:dict:update')")
    @ApiOperation("修改字典")
    @PutMapping()
    public JsonResult update(@RequestBody Dict dict) {
        if (dictService.count(new QueryWrapper<Dict>().eq("dict_name", dict.getDictName())
                .ne("dict_id", dict.getDictId())) > 0) {
            return JsonResult.error("字典名称已存在");
        }
        if (dictService.updateById(dict)) {
            //将修改后的字典进行redis缓存
            dictManager.cache();
            return JsonResult.ok("修改成功").put("dict", dictManager.readFromCache());

        }
        return JsonResult.error("修改失败");
    }

    @PreAuthorize("hasAuthority('sys:dict:remove')")
    @ApiOperation("删除字典")
    @DeleteMapping("/{id}")
    public JsonResult remove(@PathVariable("id") Integer id) {
        if (traverseDelect(id)) {
            if (dictService.removeById(id)) {
                //将修改后的字典进行redis缓存
                dictManager.cache();
                return JsonResult.ok("删除成功").put("dict", dictManager.readFromCache());
            }
        }
        return JsonResult.error("删除失败");
    }

    public boolean b = false;

    /**
     * 遍历删除字典
     *
     * @param id 字典顶层id
     * @return boolean
     */
    public boolean traverseDelect(Integer id) {
        QueryWrapper<Dict> dict = new QueryWrapper<Dict>().select("dict_id").eq("parent_id", id);
        List<Dict> list = dictService.list(dict);
        if (list.isEmpty()) {
            b = true;
        } else {
            list.forEach(item -> {
                if (dictService.removeById(item.getDictId())) {
                    traverseDelect(item.getDictId());
                    b = true;
                } else {
                    b = false;
                }

            });
        }
        return b;
    }

    @PreAuthorize("hasAuthority('sys:dict:save')")
    @ApiOperation("批量添加字典")
    @PostMapping("/batch")
    public JsonResult saveBatch(@RequestBody List<Dict> list) {
        // 对集合本身进行非空和重复校验
        StringBuilder sb = new StringBuilder();
        sb.append(CoreUtil.listCheckBlank(list, "dictCode", "字典标识"));
        sb.append(CoreUtil.listCheckBlank(list, "dictName", "字典名称"));
        sb.append(CoreUtil.listCheckRepeat(list, "dictCode", "字典标识"));
        sb.append(CoreUtil.listCheckRepeat(list, "dictName", "字典名称"));
        if (sb.length() != 0) {
            return JsonResult.error(sb.toString());
        }
        // 数据库层面校验
        if (dictService.saveBatch(list)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    @PreAuthorize("hasAuthority('sys:dict:remove')")
    @ApiOperation("批量删除字典")
    @DeleteMapping("/batch")
    public JsonResult removeBatch(@RequestBody List<Integer> ids) {
        if (dictService.removeByIds(ids)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }


}
