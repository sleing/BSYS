package com.ddd.dev.generator.codegenerator.js;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import org.springframework.stereotype.Component;

@Component
public class JsRouteGenerator    extends EntityClassGenerator {
	private String templateFile= "frameResource/js/Route.vm";

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
