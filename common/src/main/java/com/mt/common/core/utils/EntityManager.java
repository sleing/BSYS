package com.mt.common.core.utils;


import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.config.Config;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.base.ColumnInfo;
import com.mt.common.core.web.base.EntityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EntityManager {
    private static Logger logger = LogManager.getLogger();

    public static Map<String, EntityClass> entityClassesByClassName  = new HashMap<String,EntityClass>();
    public static Map<String,EntityClass> entityClassesByTableName  = new HashMap<String,EntityClass>();
    public static Map<String,String> tableNames2ClassNames = new HashMap<String,String>();
    public static Map<String,String> classNames2TableNames = new HashMap<String,String>();
    public static  List<Class<? extends BaseEntity>> allEntityClass;//保存所有实体的CLASS
    static
    {
        String packageNames = Config.basePackage;
        logger.info("开始扫描.....");
        List<Class<? extends BaseEntity>> classes = ClassUtil.getClassListByAnnotation(packageNames, DEntity.class) ;
        logger.info("结束扫描.....");
        allEntityClass =classes;//赋值
        for(Class<? extends BaseEntity> clazz : classes)
        {
            EntityClass entityClass = new EntityClass(clazz);
            if(entityClassesByClassName.containsKey(entityClass.getClassName()))
            {
                logger.error("实体类名定义重复："+entityClass.getClassName());
            }
            if(entityClassesByTableName.containsKey(entityClass.getTableName()))
            {
                logger.error("数据库表名重复："+entityClass.getTableName());
            }
            entityClassesByClassName.put(entityClass.getClassName(), entityClass);
            entityClassesByTableName.put(entityClass.getTableName(), entityClass);
            classNames2TableNames.put(entityClass.getClassName(), entityClass.getTableName());
            tableNames2ClassNames.put(entityClass.getTableName(),entityClass.getClassName());

        }
        logger.info(String.format("提取到%d个实体", entityClassesByClassName.size()));
    }

    /*通过传入实体Class,找到引用这个Class的其他实体*/
    public static  List<ColumnInfo> getEntityReferenceClasses(Class<? extends BaseEntity> clazz) {
    List<ColumnInfo> entityReferenceClasses=new ArrayList<ColumnInfo>();//返回与改实体相关的所有实体;
          for(Class<?> entutyClazz:allEntityClass){
              EntityClass entityClass = new EntityClass(entutyClazz);
               Field[] fields=entutyClazz.getDeclaredFields();//得到每个实体的所有字段
                for(Field c :fields){
                       if(c.getAnnotationsByType(DColumn.class).length>0&&c.getAnnotationsByType(DColumn.class)[0].foreignEntity().equals(clazz.getSimpleName())){
                             if(c.getAnnotationsByType(Column.class).length>0&&!c.getAnnotationsByType(Column.class)[0].name().equals("")){
                                 ColumnInfo  cif=new ColumnInfo();
                                 cif.setForeignEntity(entityClass.getTableName());
                                 cif.setFieldName(c.getAnnotationsByType(Column.class)[0].name());
                                 cif.setFieldType(entutyClazz);
                                 cif.setLabel(entutyClazz.getSimpleName()+"("+entityClass.getLabel()+")");
                                 entityReferenceClasses.add(cif);
                                 break;
                             }
                             else{
                                 throw new BusinessException("注解Column必须存在，且name不能为空");
                             }
                       }
                }
          }
        return entityReferenceClasses;
    };
    public  EntityClass getEntityClassByClassName(String className)
    {
        return  entityClassesByClassName.get(className);
    }
    public  EntityClass getEntityClassByTableName(String tableName)
    {
        return  entityClassesByTableName.get(tableName);
    }

}
