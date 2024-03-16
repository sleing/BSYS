package com.mt.common.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mt.common.core.web.PageParam;
import com.mt.common.system.entity.User;

import java.util.List;
import java.util.Map;


public interface SqlService  {

    void  executeBatchSql(String sqlCongfig,Map<String,Object> context);
    void executeBatchSqlFromFile(String sqlCongfig, Map<String, Object> context) ;

}
