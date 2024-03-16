package com.mt.common.system.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.common.system.entity.Config;

import com.mt.common.system.mapper.ConfigMapper;
import com.mt.common.system.service.ConfigService;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {


}
