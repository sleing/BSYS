package com.ddd.dev.generator.codegenerator.java.dao;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Component;

@Component
public class DaoImplGenerator   extends EntityClassGenerator {

	private String templateFile="frameResource/java/DaoImpl.vm";


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
}
