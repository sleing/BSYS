package com.ddd.dev.generator.codegenerator.html;

import com.ddd.dev.generator.codegenerator.EntityClassGenerator;
import org.springframework.stereotype.Component;

@Component
public class FormGenerator  extends EntityClassGenerator {
	private String templateFile= "frameResource/html/Form.vm";

	@Override
	public String getTemplateFile(String tempName) {
		if(tempName == null)
		{
		}
		else
		{
			this.templateFile = this.templateFile.replace(".vm","");
			this.templateFile = this.templateFile + "-"+tempName+".vm";
		}
		return this.templateFile;
	}
}
