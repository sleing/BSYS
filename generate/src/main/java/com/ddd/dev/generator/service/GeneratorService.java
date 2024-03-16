package com.ddd.dev.generator.service;

import com.ddd.dev.generator.codegenerator.entity.GenerationAttribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GeneratorService {
    public Map<String, String> generateModel(String modelDefinition);
    public Map<String, String> generateBatchModels(String filename);




    public Map<String, String> generateLimsModel(String filename);





    public  String generateMethod(String controllerMethodName) throws IOException;

    public  Map<String,String> generateBaseCode(String className,String tempName);

    public void excuteSql(String className);

    public void excuteAllSql();

    public  void cleanJpa()  ;
    public  void setJpa() ;

    public  void cleanJimureport()  ;
    public  void setJimureport() ;



}

