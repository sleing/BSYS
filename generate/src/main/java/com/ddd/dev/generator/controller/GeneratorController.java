package com.ddd.dev.generator.controller;


import com.ddd.dev.generator.service.GeneratorService;
import com.mt.common.core.annotation.OperLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "代码生成")
@RestController
@RequestMapping("/api/generate/generator")
public class GeneratorController {
//
//    @Autowired
//    private CodeTableDao codeTableDao;

    @Autowired
    private GeneratorService generatorService;

//    @Autowired
//    private OperatorService operatorService;

    @OperLog(value = "生成模型", desc = "生成模型")
    @ApiOperation("生成模型")
    @GetMapping("/generateModel")
    public Map<String, String> generateModel(String modelDefinition) {
        Map<String, String> result = this.generatorService.generateModel(modelDefinition);
//        System.out.println(modelDefinition);
        return result;
    }

    @OperLog(value = "批量生成模型", desc = "批量生成模型")
    @ApiOperation("批量生成模型")
    @GetMapping("/generateBatchModels")
    public Map<String, String> generateBatchModels(String filename) {
        Map<String, String> result = this.generatorService.generateBatchModels(filename);
//        System.out.println(modelDefinition);
        return result;
    }

    @GetMapping("/generateBaseCode")
    public Map<String, String> generateBaseCode(String className) {
//        CodeTable codeTable = this.codeTableDao.findCodeTable(1l);
//        System.out.println(codeTable.toString());
//        PageDTO pageDTO = new PageDTO();
//        pageDTO.setStartIndex(0);
//        pageDTO.setPageSize(100);
//        List<CodeTable> codeTables = this.codeTableDao.findCodeTables(pageDTO);
//        System.out.println(codeTables);
//
//        codeTables = this.codeTableDao.findAllCodeTables();
//        System.out.println(codeTables);
//
//        codeTables = this.codeTableDao.findAllCodeTablesWithIdName();
//        System.out.println(codeTables);

        Map<String, String> results = new HashMap<String, String>();

        className = StringUtils.trimToEmpty(className);
        for (String className1 : StringUtils.split(className, "\n")) {
            results.putAll(this.generatorService.generateBaseCode(className1, null));
        }

        return results;
    }

    @GetMapping("/excuteSql")
    public void excuteSql(String className) {
        className = StringUtils.trimToEmpty(className);
        for (String className1 : StringUtils.split(className, "\n")) {
            this.generatorService.excuteSql(className);
        }
    }

    @GetMapping("/excuteAllSql")
    public void excuteAllSql() {
        this.generatorService.excuteAllSql();
    }

    @GetMapping("/generateMethod")
    public String generateMethod(String controllerMethodName) throws IOException {
        return this.generatorService.generateMethod(controllerMethodName);
    }


//    @GetMapping("/createSuperOperator")
//    public  void createSuperOperator() throws IOException {
//        this.operatorService.createSuperUser();
//    }

    @GetMapping("/testException")
    public void testException() throws Exception {
//         BusinessException BusinessException = new BusinessException("这是个测试");
//         BusinessException.setCode("590");
//         BusinessException.putExtendedData("name", "小明");
//         BusinessException.putExtendedData("age", "11");
//        throw BusinessException;

    }

    @GetMapping("/cleanJpa")
    public void cleanJpa() throws IOException {
        this.generatorService.cleanJpa();
    }


    @GetMapping("/setJpa")
    public void setJpa() throws IOException {
        this.generatorService.setJpa();
    }

    @GetMapping("/cleanJimureport")
    public void cleanJimureport() throws IOException {
        this.generatorService.cleanJimureport();
    }


    @GetMapping("/setJimureport")
    public void setJimureport() throws IOException {
        this.generatorService.setJimureport();
    }

}
