package com.mt.common.core.web;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mt.common.core.security.JwtUtil;
import com.mt.common.core.security.LoginUser;
import com.mt.common.core.utils.EntityManager;
import com.mt.common.core.utils.UserAgentGetter;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.base.ColumnInfo;
import com.mt.common.core.web.base.EntityClass;
import com.mt.common.system.entity.BusinessLog;
import com.mt.common.system.entity.OperRecord;
import com.mt.common.system.entity.User;
import com.mt.common.system.mapper.ForeignKeyExcuteDao;
import com.mt.common.system.service.BusinessLogService;
import com.mt.common.system.service.OperRecordService;
import com.mt.common.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller基类
 * Created by wangfan on 2017-06-10 10:10
 */
public abstract class BaseService {//<M extends BaseMapper<T>, T> extends ServiceImpl<M, T>

    @Autowired
    private ForeignKeyExcuteDao foreignKeyExcuteDao;

    @Autowired
    private OperRecordService operRecordService;

    @Autowired
    private BusinessLogService businessLogService;

    @Autowired
    private UserService userService;
    /**
     * 获取当前登录的user
     */
    public LoginUser getLoginUser() {
        return JwtUtil.getLoginUser();
    }
    public User getUser() {
        User user = this.userService.getById(this.getLoginUserId());
        return user;
    }
    /**
     * 获取当前登录的userId
     */
    public Long getLoginUserId() {
        LoginUser loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getEid();
    }


    public Long getCurrentOrganizationId() {
        LoginUser user = this.getLoginUser();
        return user.getOrganizationId();
    }

    public String getCurrentOrganizationName() {
        LoginUser user = this.getLoginUser();
        return user.getOrganizationName();
    }

    private ThreadLocal<SecurityExpression> securityExpressionHolder = new ThreadLocal<>();
    private SecurityExpression getSecurityExpression()
    {
        SecurityExpression securityExpression = null;
        securityExpression = securityExpressionHolder.get();
        if(securityExpression == null)
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            securityExpression = new SecurityExpression(authentication);
            securityExpressionHolder.set(securityExpression);
        }
        return securityExpression;
    }
    public final boolean hasAuthority(String authority) {

        return this.getSecurityExpression().hasAuthority(authority);

    }

    public final boolean hasAnyAuthority(String... authorities) {
        return this.getSecurityExpression().hasAnyAuthority(authorities);
    }

    public final boolean hasRole(String role) {
        return hasAnyRole(role);
    }

    public final boolean hasAnyRole(String... roles) {
        return this.getSecurityExpression().hasAnyRole(roles);
    }


    /**
     * 设置请求分页数据
     */
    protected void startPage() {

    }

    //保存实体时设置公共字段
    public void setSavePulicColumns(BaseEntity entity) {
        entity.setCreatorId(this.getLoginUserId());
        entity.setCreateDatetime(new Date());
        entity.setCreatorName(this.getLoginUser().getNickname());
    }

    public Map<Class<? extends BaseEntity>, EntityUsage> checkForeignEntity(Class<? extends BaseEntity> clazz, Long entityId) {

        //本方法所实现的功能是检查所传入的类删除时判断是否被其他实体所引用  作者:April
        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = new HashMap<Class<? extends BaseEntity>, EntityUsage>();
        List<ColumnInfo> s = EntityManager.getEntityReferenceClasses(clazz);//与传入类相关的所有实体列表
        System.out.println("共有" + s.size() + "个实体与" + clazz.getSimpleName() + "有关");
        for (ColumnInfo e : s) {
            System.out.println(e.getForeignEntity() + "=>" + e.getFieldName()); //输出打印
        }
        for (int i = 0; i < s.size(); i++) {
            HashMap<String, String> maps = new HashMap<String, String>();
            Map<Long, String> idAndNames = new HashMap<Long, String>();
            EntityUsage eu = new EntityUsage();
            maps.put("tableName", s.get(i).getForeignEntity());
            maps.put("eid", entityId + "");
            maps.put("fieldName", s.get(i).getFieldName());
            List<HashMap<String, String>> oneOfEntitymaps = foreignKeyExcuteDao.findAllForeignKeys(maps);

            if (oneOfEntitymaps.size() > 0) {
                eu.setEntityLabel(s.get(i).getLabel());
                for (HashMap<String, String> oneMap : oneOfEntitymaps) {
                    idAndNames.put(Long.valueOf(String.valueOf(oneMap.get("eid"))), oneMap.get("name") + "");
                }
                eu.setUsageIdNames(idAndNames);
                entityUsageMap.put((Class<? extends BaseEntity>) s.get(i).getFieldType(), eu);
            }
        }
        return entityUsageMap;
    }

    /**
     * 写入操作日志
     *
     * @param module      操作模块
     * @param description 操作的描述
     * @param params
     * @param result
     */
    public void log(String module, String description, Object params, Object result) {
        // 获取reques对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (attributes == null ? null : attributes.getRequest());
        // 构建操作日志
        OperRecord operRecord = new OperRecord();
        operRecord.setUserId(getLoginUserId());
        if (request != null) {
            operRecord.setRequestMethod(request.getMethod());
            operRecord.setUrl(request.getRequestURI());
            operRecord.setIp(UserAgentGetter.getIp(request));
        }
        operRecord.setParam(StrUtil.sub(JSON.toJSONString(params), 0, 2000));
        operRecord.setResult(StrUtil.sub(JSON.toJSONString(result), 0, 2000));
        operRecord.setCreateTime(new Date());

        operRecordService.saveAsync(operRecord);
    }

    //写入业务日志
    public void businessLog(BusinessLog businessLog) {
        this.setSavePulicColumns(businessLog);
        businessLog.setUserId(getLoginUserId());
        businessLog.setUserName(getLoginUser().getNickname());
        System.out.println("进来了");
        System.out.println(businessLog.getEntity());
        System.out.println(businessLog.getBusiness());
        businessLogService.saveAsync(businessLog);
    }

    public abstract boolean canDownloadAttachment(String formName, Long id);

    public static class EntityUsage {
        private Class<?> clazz;
        private String tableName;
        private String entityName;
        private String entityLabel;
        private EntityClass entityClass;
        private Map<Long, String> usageIdNames = new HashMap<Long, String>();

        public EntityUsage add(Long id, String name) {
            this.getUsageIdNames().put(id, name);
            return this;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public void setClazz(Class<?> clazz) {
            this.clazz = clazz;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public String getEntityLabel() {
            return entityLabel;
        }

        public void setEntityLabel(String entityLabel) {
            this.entityLabel = entityLabel;
        }

        public EntityClass getEntityClass() {
            return entityClass;
        }

        public void setEntityClass(EntityClass entityClass) {
            this.entityClass = entityClass;
        }

        public Map<Long, String> getUsageIdNames() {
            return usageIdNames;
        }

        public void setUsageIdNames(Map<Long, String> usageIdNames) {
            this.usageIdNames = usageIdNames;
        }
    }
}
