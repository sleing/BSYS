package com.ddd.dev.generator.codegenerator;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.config.Config;
import com.mt.common.core.utils.D4Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.util.Properties;

public abstract class AbstractGenerator implements IGenerator{

    private static Logger logger = LogManager.getLogger();

    protected VelocityContext velocityContext = new VelocityContext();

    private String lastOutputFile ;

    public AbstractGenerator() {
        this.velocityContext.put("config", new Config());
        this.velocityContext.put("stringUtils", new StringUtils());
        this.velocityContext.put("d4Util", new D4Util());
    }

    public AbstractGenerator initialize() {
        return this;
    }
    @Override
    public void generate(String tempName) {
        try {
            this.generate(velocityContext, this.getTemplateFile(tempName));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("生成代码出错："+e.getMessage());
        }

    }

    public abstract  String getTemplateFile(String tempName);

    public  void generate(VelocityContext velocityContext,String templateFile) throws Exception {
//        velocityContext.put("serverPath", sql.serverPath);
//        velocityContext.put("uiPath", sql.uiPath);
//        velocityContext.put("applicationName", sql.applicationName);
//        velocityContext.put("sbuSystem", sql.sbuSystem);
//        velocityContext.put("sql", )
//
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties p = new Properties();
        p.put(VelocityEngine.FILE_RESOURCE_LOADER_PATH,Config.velocityTemplate);
        velocityEngine.init(p);

        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate(templateFile, Config.charset_UTF8, velocityContext, stringWriter);
        this.lastOutputFile = velocityContext.get("outputFile").toString();
        File file = new File(this.lastOutputFile);
        if(! this.beforeSave(this.lastOutputFile))
        {
            return;
        }


        File pDirec = new File(file.getParent());
        if(!pDirec.exists()) pDirec.mkdirs();
        Template template = velocityEngine.getTemplate(templateFile, Config.charset_UTF8);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,Config.charset_UTF8);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        template.merge(velocityContext,bufferedWriter);
        bufferedWriter.flush();
        bufferedWriter.close();
        logger.info("生成文件："+this.lastOutputFile);
    }

    public boolean beforeSave(String fileName)
    {
        File file = new File(fileName);
        if(file.exists())
        {
            logger.warn(String.format("文件已存在：%s,放弃生成，如果需要重新生成，请删除此文件", this.lastOutputFile));
            return false;
        }
        else

        {
            return true;
        }
    }
    public String getLastOutputFile() {
        return lastOutputFile;
    }

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
