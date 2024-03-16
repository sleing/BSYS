package com.mt.common.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.common.system.entity.Dict;
import com.mt.common.system.mapper.DictMapper;
import com.mt.common.system.service.DictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典服务实现类
 * @author 王某某
 *
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public List<Dict> toDictTree(List<Dict> dicts, Integer parentId) {
        List<Dict> list = new ArrayList<>();
        for (Dict dict : dicts) {
            if (parentId.equals(dict.getParentId())) {
                dict.setChildren(toDictTree(dicts, dict.getDictId()));
                list.add(dict);
            }
        }
        return list;
    }
}
