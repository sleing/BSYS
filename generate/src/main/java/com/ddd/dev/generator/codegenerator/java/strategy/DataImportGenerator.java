package com.ddd.dev.generator.codegenerator.java.strategy;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import org.springframework.stereotype.Component;

@Component
public class DataImportGenerator extends EntityClassGenerator {
	private String templateFile="frameResource/java/DataImport.vm";


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
