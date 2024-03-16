package com.ddd.dev.generator.codegenerator;


import com.mt.common.core.utils.EntityManager;
import com.mt.common.core.web.base.EntityClass;

public abstract  class EntityClassGenerator extends AbstractGenerator  {

    public EntityClassGenerator() {
    }

    public AbstractGenerator initialize(EntityClass entityClass) {

        velocityContext.put("entityClass", entityClass);
        velocityContext.put("entityManager",new EntityManager());

        return this;
    }

    public abstract  String getTemplateFile(String tempName);

//    private static Class<?> getClazz(String className){
//        Class<?> clazz=null;
//        try {
//            clazz = Class.forName(className);
//            if(SessionFactory.getEntityClass(clazz)==null&&clazz.getSuperclass().equals(Entity.class)){
//                EntityClass entityClass = new EntityClass(clazz);
//                entityClass.preInit();
//                synchronized (SessionFactory.getEntityClasses()) {
//                    SessionFactory.getEntityClasses().put((Class<? extends Entity>)clazz, entityClass);
//                }
//                entityClass.postInit();
//            }
//
//        } catch (ClassNotFoundException e) {
//            //e.printStackTrace();
//            System.err.println("类不存在："+className);
//        }
//        return clazz;
//    }

}
