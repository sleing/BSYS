package com.ddd.dev.generator.codegenerator.js;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import org.springframework.stereotype.Component;

@Component
public class JsDetailGenerator extends EntityClassGenerator {
	private String templateFile= "frameResource/js/Detail.vm";

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
