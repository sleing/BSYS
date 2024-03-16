
package com.mt.common.core.utils;

import com.mt.common.system.entity.Dict;
import com.mt.common.system.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * @author zx
 */

@Component
public class DictManager {

    @Autowired
    private DictService dictService;

    @Autowired
    private RedisTemplate redisTemplate;



    private static final String redisKey ="dicts";
    /**
     * 将系统参数加入到redis
     */

    public void cache() {
        redisTemplate.delete(DictManager.redisKey);

        //获取字典
        List<Dict> listD = dictService.list();
        List<Dict> dicts = dictService.toDictTree(listD, 0);

        //将字典数据加入redis
        BoundHashOperations dictsOps = redisTemplate.boundHashOps(DictManager.redisKey);

        dicts.forEach(dict -> {
            dictsOps.put(dict.getDictName(), dict);
        });
    }

    public Map<String, Dict>  readFromCache(){
        //将系统参数数据加入redis
        BoundHashOperations dictsOps = redisTemplate.boundHashOps(DictManager.redisKey);

        return dictsOps.entries();
    }

    /**
     * 根据系统参数名称获取系统参数
     */
    public Dict getDictByName(String name) {
        BoundHashOperations dictsOps = redisTemplate.boundHashOps(DictManager.redisKey);

        return (Dict) dictsOps.get(name);
    }
}



