package com.mt.common.system.service.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.abel533.sql.SqlMapper;
import com.mt.common.core.config.SqlSessionFactoryConfig;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.utils.PlaceholderResolver;
import com.mt.common.core.utils.SpringContextUtils;
import com.mt.common.core.utils.StringUtils;
import com.mt.common.core.web.BaseService;
import com.mt.common.core.web.PageParam;
import com.mt.common.system.entity.Menu;
import com.mt.common.system.entity.Role;
import com.mt.common.system.entity.User;
import com.mt.common.system.entity.UserRole;
import com.mt.common.system.mapper.MenuMapper;
import com.mt.common.system.mapper.UserMapper;
import com.mt.common.system.mapper.UserRoleMapper;
import com.mt.common.system.service.SqlService;
import com.mt.common.system.service.UserService;
import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 * Created by wangfan on 2018-12-24 16:10
 */
@Service
public class SqlServiceImpl implements SqlService {

    @Autowired
    DataSource dataSource;

    @Autowired
    private SqlSessionFactoryConfig sqlSessionFactoryConfig;

//    /* find(newMenuId)  */ select max(`menu_id`)+1  from  `sys_menu`;
//    /* find(moduleId)  */ select `menu_id` from  `sys_menu` where `menu_name`  */ "测试1";
//    /* execute(!moduleId)  */ insert into `sys_menu`(`menu_id`, `parent_id`,`menu_name`,`menu_icon`,`path`,`component`,`menu_type`,`sort_number`,`authority`) values
//            (${newMenuId}, 0, '测试1', 'layui-icon layui-icon-senior', '', 0, 1, '');
//    /* execute(!moduleId)  */ insert into `sys_role_menu`(`role_id`,`menu_id`) values (1, ${newMenuId});
//    /* find(moduleId)  */ select `menu_id` from  `sys_menu` where `menu_name`  */ "测试1";

    /**
     * 根据上面的SQL执行，find 命令查询一个值放到 变量 newMenuId，execute 执行SQL语句，如果中间的变量不为空则执行，!moduleId表示为空则执行
     * ${newMenuId} 表示执行时需要替换的变量。
     *
     * @param sqlCongfig
     */
    @Override
    public void executeBatchSql(String sqlCongfig, Map<String, Object> context) {
        SqlSession session = null;
        SqlMapper sqlMapper =  null;
        try {
            try {
                session = this.getSqlSession();
                sqlMapper = new SqlMapper(session);
            } catch (Exception e) {
                throw new BusinessException("创建 sqlMapper出错， "+e.getMessage(),e);
            }
            if (context == null) {
                context = new HashMap<String, Object>();
            }
            List<String> sqls = Arrays.asList(StringUtils.split(sqlCongfig, ";"));
            Map<String, Object> finalContext = context;
            SqlMapper finalSqlMapper = sqlMapper;
            sqls.forEach((sql) -> {
                if (StringUtils.isEmpty(sql)) return;
                String[] command = this.parseSql(sql);
                this.executeSql(command, finalContext, finalSqlMapper);
            });
        }finally {
            if(session != null) {
                session.close();
            }
        }

    }

    @Override
    public void executeBatchSqlFromFile(String sqlCongfigFileName, Map<String, Object> context) {
        String sqlCongfig = null;
        try {
            sqlCongfig = FileUtils.readFileToString(new File(sqlCongfigFileName), CharsetUtil.UTF_8);
        } catch (IOException e) {
            System.err.println("读SQL执行文件 "+sqlCongfigFileName+" 出错,原因是："+e.getMessage());
            return;
            //throw new BusinessException("读SQL执行文件 "+sqlCongfigFileName+" 出错,原因是："+e.getMessage(),e);
        }
        this.executeBatchSql(sqlCongfig,context);
    }
    private String[] parseSql(String sql) {
        String command = StringUtils.substringBetween(sql, "/*", "*/");
        String sql1 = StringUtils.substringAfter(sql, "*/");
        String param = StringUtils.trim(StringUtils.substringBetween(command, "(", ")"));
        command = StringUtils.lowerCase(StringUtils.trim(StringUtils.substringBefore(command, "(")));

        return new String[]{command, param, sql1};
    }

    private void executeSql(String[] command, Map<String, Object> context,SqlMapper sqlMapper) {
        if ("find".equals(command[0])) {
            this.executeFindSql(command, context,sqlMapper);
        } else if ("execute".equals(command[0])) {
            this.executeExecuteSql(command, context,sqlMapper);
        }
    }

    private void executeFindSql(String[] command, Map<String, Object> context,SqlMapper sqlMapper) {
        String param = StringUtils.trim( command[1]);
        String sql = StringUtils.trim(command[2]);


        sql = PlaceholderResolver.getDefaultResolver().resolveByMapForSQL(sql, context);
        Map<String, Object> result = sqlMapper.selectOne(sql);

        if(result == null ||  result.values().size() == 0)
        {
            context.put(param, null);
        }
        else if(result.values().size() == 1)
        {
            String value = result.values().toArray()[0].toString();
            context.put(param, value);
        }
        else
        {
            throw new BusinessException("SQL语句中返回了多个值，这是不合法的："+sql);
        }

    }

    private void executeExecuteSql(String[] command, Map<String, Object> context,SqlMapper sqlMapper) {
        Boolean isNot = command[1].startsWith("!");
        String param = isNot ? StringUtils.trim(StringUtils.substringAfter(command[1], "!")) : command[1];
        String sql = StringUtils.trim(command[2]);

        Object paramValue = context.get(param);
        if (isNot && paramValue == null || !isNot && paramValue != null) {
            sql = PlaceholderResolver.getDefaultResolver().resolveByMapForSQL(sql, context);
            if (StringUtils.startsWith(sql, "insert")) {
                sqlMapper.insert(sql);
            } else if (StringUtils.startsWith(sql, "update")) {
                sqlMapper.update(sql);
            }
            context.put(param, "");
        }
    }

    private SqlSession getSqlSession() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);//更多参数请自行注入
        SqlSession session = bean.getObject().openSession();

        return session;

    }

}
