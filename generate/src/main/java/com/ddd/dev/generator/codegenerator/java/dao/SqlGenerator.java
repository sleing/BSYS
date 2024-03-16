package com.ddd.dev.generator.codegenerator.java.dao;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import com.mt.common.core.config.Config;
import com.mt.common.core.utils.EntityManager;
import com.mt.common.core.web.base.EntityClass;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Component
public class SqlGenerator extends EntityClassGenerator {

	private String templateFile="frameResource/java/Sql.vm";

	@Override
	public String getTemplateFile(String tempName) {
		String temp = this.templateFile;
		if(tempName == null)
		{
		}
		else
		{
			temp = temp.replace(".vm","");
			temp = temp + "-"+tempName+".vm";
		}
		return temp;
	}
	public  static  String getFileName(String classFullName)
	{
		String className = StringUtils.substringAfterLast(classFullName,".");
		EntityClass entityClass = new EntityManager().getEntityClassByClassName(className);

		return getFileName(entityClass);
	}
	public  static  String getFileName(EntityClass entityClass)
	{
		String packageName= entityClass.getPackages().replace("entity", "dao")+".mapper";
		packageName = StringUtils.replace(packageName,".","/");
		StringBuilder fileName = new StringBuilder();
		fileName.append(Config.serverPath).append(File.separator)
				.append("/src/main/resources/").append(packageName).append(File.separator)
				.append(entityClass.getClassName()).append(".sql");
		return fileName.toString();
	}
	public  static List<String> getAllFileNames()
	{
		List<String> fileNames = new ArrayList<>();
		EntityManager.entityClassesByClassName.values().forEach((entityClass)->{
			fileNames.add(getFileName(entityClass));
		});
		return fileNames;
	}
//

}
