package com.mt.common.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mt.common.system.entity.Dict;

import java.util.List;

/**
 * 字典服务类
 * @author 王某某
 */
public interface DictService extends IService<Dict> {

    /**
     * 转化为树形结构
     *
     * @param dicts    菜单list
     * @param parentId 最顶级id
     * @return List<Dict>
     */
    List<Dict> toDictTree(List<Dict> dicts, Integer parentId);

}
