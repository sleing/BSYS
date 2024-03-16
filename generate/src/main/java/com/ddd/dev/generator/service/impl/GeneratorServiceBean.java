package com.ddd.dev.generator.service.impl;


import com.ddd.dev.generator.codegenerator.BaseCodeGenerator;
import com.ddd.dev.generator.codegenerator.generator.EntityCodeGenerator;
import com.ddd.dev.generator.codegenerator.java.dao.SqlGenerator;
import com.ddd.dev.generator.service.GeneratorService;
import com.mt.common.core.config.Config;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.utils.EntityManager;


import java.text.DecimalFormat;

import com.mt.common.system.service.SqlService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class GeneratorServiceBean implements GeneratorService {

    @Autowired
    EntityCodeGenerator entityCodeGenerator;

    @Autowired
    private BaseCodeGenerator baseCodeGenerator;

    @Autowired
    private SqlService sqlService;

    @Override
    public Map<String, String> generateModel(String modelDefinition) {
        Map<String, String> result = this.entityCodeGenerator.generatorEntity(modelDefinition);
        return result;
    }

    @Override
    public Map<String, String> generateBatchModels(String filename) {
        Map<String, String> result = this.entityCodeGenerator.generatorBatchEntity(filename);
        return result;
    }
    @Override
    public Map<String, String> generateLimsModel(String filename) {
        return null;
    }




    @Override
    public Map<String, String> generateBaseCode(String className, String tempName) {
        return baseCodeGenerator.generateBaseCode(className, tempName);
    }

    @Override
    public void excuteSql(String classFullName) {
        try {
            String sqlFileName = SqlGenerator.getFileName(classFullName);
            this.sqlService.executeBatchSqlFromFile(sqlFileName, null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(classFullName + "出现" + e.getMessage());
        }
    }

    @Override
    public void excuteAllSql() {
        EntityManager.entityClassesByClassName.values().forEach((entityClass) -> {
            this.excuteSql(entityClass.getClassFullName());
        });

    }


    @Override
    public String generateMethod(String controllerMethodName) throws IOException {
        String className = StringUtils.substringBeforeLast(controllerMethodName, "#");
        String entityClassName = StringUtils.substringAfterLast(className, ".").trim();
        String packages = StringUtils.substringBeforeLast(className, ".").trim();
        String subSystemName = StringUtils.substringAfterLast(StringUtils.substringBefore(packages, ".controller"), ".");
        String moduleName = StringUtils.substringAfterLast(packages, ".");

        entityClassName = StringUtils.substringBefore(entityClassName, "Controller");

        controllerMethodName = StringUtils.substringAfterLast(controllerMethodName, "#");
        String fileName = StringUtils.join(new String[]{Config.serverPath, "src/main/java", StringUtils.replace(packages, ".", File.separator), entityClassName + "Controller" + ".java"}, File.separator);

        String codes = FileUtils.readFileToString(new File(fileName), "UTF-8");
        String[] codeLines = StringUtils.split(codes, "\r\n");
        String signatureLine = null;
        int signatureLineIndex = 0;
        for (int i = 0; i < codeLines.length; i++) {
            String line = codeLines[i];
            line = StringUtils.trimToEmpty(line);
            line = line.replaceAll(" +", " ");
            line = line.replace(" (", "(");
            if (StringUtils.startsWith(line, "public") && StringUtils.contains(line, " " + controllerMethodName + "(")) {
                signatureLine = line;
                signatureLineIndex = i;
                break;
            }
        }
        if (signatureLine == null) {
            System.out.println("没有找到方法：" + controllerMethodName);
            new BusinessException("没有找到方法：" + controllerMethodName);
            return "没有找到方法：" + controllerMethodName;
        }
        signatureLine = signatureLine.replace(" )", ")");
        signatureLine = signatureLine.replace(" ,", ",");

        String returnType = signatureLine.split(" ")[1];
        String params = StringUtils.trimToEmpty(StringUtils.substringBetween(signatureLine, "(", ")"));
        params = StringUtils.replace(params, "@RequestParam", "");
        params = StringUtils.replace(params, "@RequestBody", "");

        List<String> paramsList = new ArrayList<String>();
        if (!params.equals("")) {
            String[] ps = StringUtils.split(params, ",");
            for (String p : ps) {
                p = StringUtils.trimToEmpty(p);
                p = p.replaceAll(" +", " ");
                paramsList.add(StringUtils.split(p, " ")[1]);
            }
        }
        String controllerRequestMapping = StringUtils.substringBefore(StringUtils.substringAfter(codes, "@RequestMapping(\""), "\"");

        String mappingLine = null;
        for (int i = signatureLineIndex; i > 0; i--) {
            String line = codeLines[i];
            line = StringUtils.trimToEmpty(line);
            if (StringUtils.contains(line, "Mapping")) {
                mappingLine = line;
                break;
            }
        }
        mappingLine.replaceAll(" +", "");
        String methodRequestMapping = StringUtils.substringBetween(mappingLine, "Mapping(\"", "\"");
        String httpMethod = StringUtils.substringBetween(mappingLine, "@", "Mapping");
        ;



        StringBuilder newCodes = null;
        int insertPos = 0;

        //service
        newCodes = new StringBuilder();
        newCodes.append("\npublic ").append(returnType)
                .append(" ").append(controllerMethodName).append("(").append(params).append(");").append("\n");

        fileName = StringUtils.join(new String[]{Config.serverPath, "src/main/java", StringUtils.replace(StringUtils.replace(packages, ".", File.separator), "controller", "service"), entityClassName + "Service" + ".java"}, File.separator);
        codes = FileUtils.readFileToString(new File(fileName), "UTF-8");
        insertPos = StringUtils.lastIndexOf(codes, "}");
        codes = StringUtils.substring(codes, 0, insertPos - 1) + newCodes.toString() + StringUtils.substring(codes, insertPos);
        FileUtils.write(new File(fileName), codes, "UTF-8");

        //ServiceBean
        newCodes = new StringBuilder();
        newCodes.append("\npublic ").append(returnType)
                .append(" ").append(controllerMethodName).append("(").append(params).append(")\n{")
                .append("\n");
        newCodes.append("\t//");
        if (!"void".equals(returnType)) {
            newCodes.append("return ");
        }
        newCodes.append("this.").append(StringUtils.uncapitalize(entityClassName)).append("Dao").append(".")
                .append(controllerMethodName).append("(");
        if (paramsList.size() > 0) {
            for (String p : paramsList) {
                newCodes.append(p).append(",");
            }
            newCodes.deleteCharAt(newCodes.length() - 1);
        }
        newCodes.append(");").append("\n");
        if (!"void".equals(returnType)) {
            newCodes.append("\treturn null;\n");
        }
        newCodes.append("}\n");

        fileName = StringUtils.join(new String[]{Config.serverPath, "src/main/java", StringUtils.replace(StringUtils.replace(packages, ".", File.separator), "controller", "service"), "impl", entityClassName + "ServiceBean" + ".java"}, File.separator);
        codes = FileUtils.readFileToString(new File(fileName), "UTF-8");
        insertPos = StringUtils.lastIndexOf(codes, "}");
        codes = StringUtils.substring(codes, 0, insertPos - 1) + newCodes.toString() + StringUtils.substring(codes, insertPos);
        FileUtils.write(new File(fileName), codes, "UTF-8");

        //dao
        newCodes = new StringBuilder();
        newCodes.append("\n//public ").append(returnType)
                .append(" ").append(controllerMethodName).append("(").append(params).append(");\n");

        fileName = StringUtils.join(new String[]{Config.serverPath, "src/main/java", StringUtils.replace(StringUtils.replace(packages, ".", File.separator), "controller", "dao"), entityClassName + "Dao" + ".java"}, File.separator);
        codes = FileUtils.readFileToString(new File(fileName), "UTF-8");
        insertPos = StringUtils.lastIndexOf(codes, "}");
        codes = StringUtils.substring(codes, 0, insertPos - 1) + newCodes.toString() + StringUtils.substring(codes, insertPos);
        FileUtils.write(new File(fileName), codes, "UTF-8");

        //dao implemenation
        newCodes = new StringBuilder();
        newCodes.append("\n\t<!--\n\t<select id=\"").append(controllerMethodName).append("\" resultType=\"java.lang.Integer\" parameterType=\"java.lang.Long\">")
                .append("\n\t\t").append(" delete from  where eid = #{}").append("\n\t</select>\n\t-->").append("\n");

        fileName = StringUtils.join(new String[]{Config.serverPath, "src/main/resources", StringUtils.replace(StringUtils.replace(packages, ".", File.separator), "controller", "dao"), "mapper", entityClassName + "Mapper" + ".xml"}, File.separator);
        codes = FileUtils.readFileToString(new File(fileName), "UTF-8");
        insertPos = StringUtils.lastIndexOf(codes, "</mapper>");
        codes = StringUtils.substring(codes, 0, insertPos - 1) + newCodes.toString() + StringUtils.substring(codes, insertPos);
        FileUtils.write(new File(fileName), codes, "UTF-8");

        //UI service
        newCodes = new StringBuilder();

        newCodes.append("\n\tstatic ").append(controllerMethodName).append("(");
        if (paramsList.size() > 0) {
            for (String p : paramsList) {
                newCodes.append(p).append(",");
            }
            newCodes.deleteCharAt(newCodes.length() - 1);
        }
        newCodes.append(")").append("\n\t{\n");

        newCodes.append("\t\tconst query = {\n");
        if (paramsList.size() > 0) {
            for (String p : paramsList) {
                newCodes.append(p).append(",");
            }
            newCodes.deleteCharAt(newCodes.length() - 1);
        }
        newCodes.append("\n\t}\n");
        newCodes.append("\t\treturn axios({\n");
        newCodes.append("\turl:'/" + subSystemName + "/" + moduleName + "/" + entityClassName + "/" + controllerMethodName + "',\n");
        newCodes.append("\tmethod:'" + httpMethod.toUpperCase() + "',\n");
        newCodes.append("\tparams: query,\n");
//        newCodes.append("\t\tvar body = simpleServer.getArgs(this."+controllerMethodName+",arguments)\n");
//        newCodes.append("\t\treturn simpleServer.connection('"+httpMethod.toUpperCase()+"', '")
//        .append(controllerRequestMapping)
//        .append(methodRequestMapping).append("',body);\n");
        newCodes.append("\t});\n");
        newCodes.append("\t}\n");

        fileName = StringUtils.join(new String[]{Config.uiPath, "src/views", subSystemName, moduleName, StringUtils.lowerCase(entityClassName), entityClassName + "Service" + ".js"}, File.separator);
        codes = FileUtils.readFileToString(new File(fileName), "UTF-8");
        insertPos = StringUtils.lastIndexOf(codes, "}");
        codes = StringUtils.substring(codes, 0, insertPos - 1) + newCodes.toString() + StringUtils.substring(codes, insertPos);
        FileUtils.write(new File(fileName), codes, "UTF-8");

        return "生成成功";
    }

    @Override
    public void cleanJpa() {
        JpaHandler.cleanJpa();
    }

    @Override
    public void setJpa() {
        JpaHandler.setJpa();
    }

    @Override
    public void cleanJimureport() {
        JimureportHandler.clean();
    }

    @Override
    public void setJimureport() {
        JimureportHandler.set();
    }
}
