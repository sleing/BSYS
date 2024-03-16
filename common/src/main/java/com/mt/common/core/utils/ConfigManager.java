package com.mt.common.core.utils;

import com.mt.common.system.entity.Config;
import com.mt.common.system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zx
 */

@Component
public class ConfigManager  {

    @Autowired
    private ConfigService configService;


    @Autowired
    private RedisTemplate redisTemplate;



    private static final String redisKey ="configs";
    /**
     * 将系统参数加入到redis
     */
    public void cache() {
        redisTemplate.delete(ConfigManager.redisKey);

        //获取系统参数
        List<Config> configs = configService.list();

        //将系统参数数据加入redis
        BoundHashOperations configsOps = redisTemplate.boundHashOps(ConfigManager.redisKey);

        configs.forEach(config -> {
            configsOps.put(config.getName(), config);
        });
    }

    public Map<String, Config>  readFromCache(){
        //将系统参数数据加入redis
        BoundHashOperations configsOps = redisTemplate.boundHashOps(ConfigManager.redisKey);

        return configsOps.entries();
    }

    /**
     * 根据系统参数名称获取系统参数
     */

    public Config getConfigByName(String name) {
        BoundHashOperations configsOps = redisTemplate.boundHashOps(ConfigManager.redisKey);

        return (Config) configsOps.get(name);
    }
}
