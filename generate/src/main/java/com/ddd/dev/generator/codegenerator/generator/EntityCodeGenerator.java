package com.ddd.dev.generator.codegenerator.generator;


import com.ddd.dev.generator.codegenerator.entity.FieldAttribute;
import com.ddd.dev.generator.codegenerator.entity.GenerationAttribute;
import com.ddd.dev.generator.codegenerator.generator.impl.java.entity.EntityGenerator;
import com.ddd.dev.generator.codegenerator.util.GeneratorUtil;
import com.mt.common.core.config.Config;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.utils.D4Util;
import com.mt.common.core.web.base.EntityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


import java.util.*;

@Component
public class EntityCodeGenerator {
    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger();
//	@Resource(name="codeTypeServiceBean")
//	private static CodeTypeService codeTypeService = (CodeTypeService) SpringContextUtil.getBean("codeTypeServiceBean");

//	@Autowired
//	private GenerationParameter generationParameter;



    public static void main(String[] args) {
        String text = GeneratorUtil.readFile("C:/Users/Administrator/Desktop/javaE.txt");

    }
    public synchronized Map<String, String> generatorBatchEntity(String filename) {

        List<String> tables = readWordTables(filename);
        //排查术语表和示例
        tables.remove(0);
        tables.remove(0);
        tables.remove(0);
        List<String> classNames = new ArrayList<>();
        Map<String,GenerationAttribute> generationAttributes = new HashMap<>();

        for(int i=0;i<tables.size();i++)
        {
            try {
                GenerationAttribute ga = this.generatorEntityAttribute(tables.get(i));
                generationAttributes.put(ga.getEntityClass().getClassName(),ga);
            }catch (Exception e)
            {
                throw  new BusinessException("生成实体属性出错，原因是："+e.getMessage()+"\n"+tables.get(i),e);
            }

        }
        generationAttributes.forEach((className, ga) -> {
            try {
                this.generatorEntityCode(ga,generationAttributes);
                classNames.add(className);
            }catch (Exception e)
            {

                throw  new BusinessException("生成实体代码出错，原因是："+e.getMessage()+"\n"+className,e);
            }
        });

        Map<String, String> result = new HashMap<>();
        result.put("successMsg",StringUtils.join(tables,","));
        return result;
    }
    public synchronized GenerationAttribute generatorEntityAttribute(String text) {
        GenerationAttribute ga = new GenerationAttribute();
        ga.setEntityClass(new EntityClass());
        List<CodeTableConfig> codeTableConfigs = new ArrayList<CodeTableConfig>();
        List<FieldAttribute> attris = new ArrayList<FieldAttribute>();
//        text = StringUtils.trimToEmpty(text);
//        text = StringUtils.replace(text, " ", "");
        String[] rows = text.split("\n");
        for (int i = 0; i < rows.length; i++) {
            int step = i + 1;

//            if ((i + 1) == rows.length) {
//                int tabNum = 0;
//                for (int k = 0; k < rows[i].length(); k++) {
//                    if (rows[i].charAt(k) == '\t') {
//                        tabNum++;
//                    }
//                }
//                if (tabNum < 3) {
//                    throw new BusinessException("第" + (i + 1) + "行缺少数据，只有" + (tabNum) + "列");
//                }
//                for (int j = 0; j < 8 - tabNum; j++) {
//                    rows[i] = rows[i] + '\t';
//                }
//            }

            String[] cols=StringUtils.split(StringUtils.replace(rows[i],"\t"," \t "),"\t");
            int emptyCount = 0;
            for (int k =0;k<cols.length;k++)
            {
                cols[k] = StringUtils.replace(cols[k]," ","");
                if(StringUtils.isEmpty(cols[k]))
                {
                    emptyCount ++;
                }
            }
            if(emptyCount > cols.length-1)
            {
                continue;
            }






            int j = 0;
//			for (; j < cols.length; j++) {
//				cols[j] = StringUtils.replace(cols[j]," ", "");
//				//System.out.print(cols[j]+"|");
//			}
            //System.out.println();
//            if (!(cols.length == 7 || cols.length == 9)) {
//                throw new BusinessException("第" + (i + 1) + "行缺少数据，只有" + (cols.length) + "列");
//            }

            if (i == 0) {
                if ("".equals(cols[1])) {
                    throw new BusinessException("请写上实体名");
                }
                if ("".equals(cols[3])) {
                    throw new BusinessException("请写上中文名");
                }
                String moduleName = cols[5];
                if ("".equals(moduleName)) {
                    throw new BusinessException("请写上模块名");
                }
                String[] moduleNames = StringUtils.split(moduleName, ":");
                if (moduleNames.length != 3) {
                    throw new BusinessException("模块名名称格式为:  子系统代码:模块代码:模块名称，例如：erp:student:学生管理");
                }
                ga.getEntityClass().setClassName(StringUtils.capitalize(cols[1]));
                ga.getEntityClass().setTableName(D4Util.convertCamelCaseName2UnderScore(ga.getEntityClass().getClassName()));
                ga.getEntityClass().setLabel(cols[3]);
                ga.getEntityClass().setSubSystemName(moduleNames[0]);
                ga.getEntityClass().setModuleName(moduleNames[1]);
                ga.getEntityClass().setModuleLabel(moduleNames[2]);
                ga.getEntityClass().setPackages(StringUtils.join(new String[]{Config.basePackage, ga.getEntityClass().getSubSystemName(), "entity", ga.getEntityClass().getModuleName()}, "."));

            } else if (i >= 2) {
                if (StringUtils.isEmpty(cols[1])) {
                    throw new BusinessException("第" + (i + 1) + "行的 字段名称 不能为为空");
                }
                if (StringUtils.isEmpty(cols[2])) {
                    throw new BusinessException("第" + (i + 1) + "行的 字段类型 不能为为空");
                }
                if (StringUtils.isEmpty(cols[3])) {
                    throw new BusinessException("第" + (i + 1) + "行的 中文名称 不能为为空");
                }

                FieldAttribute fa = new FieldAttribute();
                fa.setVisitModifier("private");
                fa.setAttributeModifier("");
                String type = cols[2].replaceAll(" *", "");
                type = StringUtils.capitalize(type);
                if (type.charAt(0) > 'Z' || type.charAt(0) < 'A') {
                    throw new BusinessException("生成出错，原因是：属性：" + cols[1] + "的类型：" + type + " 应该使用包装类型");

                }
                if (StringUtils.startsWith(type,"String(")) {
                    int startIndex = type.indexOf("(");
                    fa.setType(type.substring(0, startIndex));
                    fa.getColumnInfo().setLength(Integer.parseInt(type.substring(startIndex + 1, type.length() - 1)));
                } else {
                    fa.setType(type);
                }
                String fieldName = StringUtils.uncapitalize(cols[1].replaceAll(" *", ""));

                fa.setFieldName(fieldName);
                String columnName = D4Util.convertCamelCaseName2UnderScore(fieldName);
                fa.getColumnInfo().setColumnName(columnName);
                fa.getColumnInfo().setLabel(cols[3]);
                if (cols[4].toLowerCase().contains("非空")) {
                    fa.getColumnInfo().setNullable(false);
                }
                if (cols[4].toLowerCase().contains("唯一")) {
                    fa.getColumnInfo().setUnique(true);
                }
//				if(cols[5].toLowerCase().contains("true")){
//					//fa.getColumnInfo().setComposition(true);
//				}
                if (StringUtils.startsWith(type,"CodeTable")) {
                    String codeTypeKey =StringUtils.substringBetween(type,"<",">");
                    List<String> codeTables = null;

                    String codeTableStr = StringUtils.substringAfter(type, ">");
                    if(StringUtils.startsWith(codeTableStr,":"))
                    {
                        codeTableStr = StringUtils.removeStart(codeTableStr,":");
                    }
                    if(StringUtils.startsWith(codeTableStr,"："))
                    {
                        codeTableStr = StringUtils.removeStart(codeTableStr,"：");
                    }
                    codeTableStr = StringUtils.trimToEmpty(codeTableStr);

                    if(StringUtils.contains(codeTableStr,"|"))
                    {
                        codeTables = Arrays.asList(StringUtils.split(codeTableStr, '|'));
                    }
                    else
                    {
                        codeTables = Arrays.asList(StringUtils.split(codeTableStr, ','));
                    }

                    CodeTableConfig codeTableConfig = new CodeTableConfig();
                    codeTableConfig.setCodeTypeKey(codeTypeKey);
                    codeTableConfig.setCodeTypeName(cols[3]);
                    codeTableConfig.setCodeTables(codeTables);
                    codeTableConfigs.add(codeTableConfig);
                    fa.setCodeTableConfig(codeTableConfig);
                }
                fa.getColumnInfo().setComment(cols[5]);
                //TODO:ycs 加逻辑 添加一列 component 组件列 where 搜索条件

                if(cols.length ==9) {
                    fa.getColumnInfo().setComponent(cols[6]);
                    if (cols[7].equals("是")) {
                        fa.getColumnInfo().setWhere(true);
                    } else {
                        fa.getColumnInfo().setWhere(false);
                    }
                }

                if(StringUtils.isEmpty(fa.getColumnInfo().getComponent()))
                {
                    if (fa.getType().contains("String") || fa.getType().contains("Character")) {
                        fa.getColumnInfo().setComponent("文本");
                    } else if (fa.getType().contains("remark")) {
                        fa.getColumnInfo().setComponent("文本域");
                    } else if (fa.getType().contains("CodeTable")) {
                        fa.getColumnInfo().setComponent("字典下拉单选");
                    } else if (fa.getType().contains("Boolean")) {
                        fa.getColumnInfo().setComponent("开关");
                    } else if (fa.getType().contains("Byte")
                            || fa.getType().contains("Short")
                            || fa.getType().contains("Integer")
                            || fa.getType().contains("Long")
                    ) {
                        fa.getColumnInfo().setComponent("数字，整数");
                    } else if (fa.getType().contains("Float") || fa.getType().equals("Double")) {
                        fa.getColumnInfo().setComponent("数字，小数");
                    } else if (fa.getType().contains("Date")) {
                        fa.getColumnInfo().setComponent("日期选择");
                    }else{
                        fa.getColumnInfo().setComponent("对象选择");
                        //throw  new BusinessException(ga.getEntityClass().getTableName()+"中"+fa.getFieldName() +"解析组件异常："+fa.getType());
                    }
                }

                attris.add(fa);

            }
        }
        ga.setFieldAttributeList(attris);
        ga.setCodeTableConfigs(codeTableConfigs);
        return ga;
    }

    public synchronized Map<String, String> generatorEntity(String text) {
        GenerationAttribute ga = this.generatorEntityAttribute(text);
        String className = this.generatorEntityCode(ga);

        Map<String, String> result = new HashMap<String, String>();
        result.put("successMsg", className);
        return result;
    }
    public synchronized String generatorEntityCode(GenerationAttribute ga) {
        return generatorEntityCode(ga,null);
    }
    public synchronized String generatorEntityCode(GenerationAttribute ga,Map<String,GenerationAttribute> generationAttributes) {

        EntityGenerator entityGenerator =  new EntityGenerator();
        entityGenerator.initialize(ga);
        entityGenerator.setGenerationAttributes(generationAttributes);
        try {
            Class.forName(ga.getEntityClass().getClassFullName());
//            System.out.println(ga.getEntityClass().getClassFullName());
            //result.put("errorMsg", "已存在实体："+ga.getEntityClass().getClassFullName());
//			return result;
            logger.warn("已存在实体：" + ga.getEntityClass().getClassFullName());
        } catch (ClassNotFoundException e) {
        }
        entityGenerator.generate(null);
        //实体生成成功之后去处理码表
        this.createCodeTables(ga.getCodeTableConfigs());

        return ga.getEntityClass().getClassFullName();
    }
    private void createCodeTables(List<CodeTableConfig> codeTableConfigs) {
//		for(CodeTableConfig codeTableConfig : codeTableConfigs)
//		{
//			List<CodeTable> codeTables = new ArrayList<CodeTable>();
//			for(String name : codeTableConfig.getCodeTables())
//			{
//				CodeTable codeTable = new CodeTable();
//				codeTable.setCode(codeTableConfig.getCodeTypeKey());
//				codeTable.setName(name);
//				codeTables.add(codeTable);
//			}
//		}
    }

    private List<String> readWordTables(String filename)
    {
        List<String> tables = new ArrayList<>();

        try (FileInputStream in = new FileInputStream(filename); // 载入文档
             POIFSFileSystem pfs = new POIFSFileSystem(in);
             HWPFDocument hwpf = new HWPFDocument(pfs);) {

            Range range = hwpf.getRange();// 得到文档的读取范围
            TableIterator it = new TableIterator(range);
            // 迭代文档中的表格

            while (it.hasNext()) {
                StringBuilder tableBuilder=new StringBuilder();
                Table tb = (Table) it.next();

                // 迭代行，默认从0开始,可以依据需要设置i的值,改变起始行数，也可设置读取到那行，只需修改循环的判断条件即可
                for (int i = 0; i < tb.numRows(); i++) {
                    StringBuilder rowBuilder=new StringBuilder();
                    TableRow tr = tb.getRow(i);
                    // 迭代列，默认从0开始
                    int emptyCellCount = 0;

                    for (int j = 0; j < tr.numCells(); j++) {
                        TableCell td = tr.getCell(j);// 取得单元格
                        // 取得单元格的内容
                        String rowStr = "";
                        for (int k = 0; k < td.numParagraphs(); k++) {
                            Paragraph para = td.getParagraph(k);
                            String s = para.text();
                            // 去除后面的特殊符号
                            if (null != s && !"".equals(s)) {
                                s = s.substring(0, s.length() - 1);
                            }
                            s=s.trim();
                            rowStr +=s;
                        }
                        if(StringUtils.isEmpty(rowStr))
                        {
                            emptyCellCount++;
                        }
                        rowBuilder.append(rowStr).append("\t");
                    }
                    //如果是空行则不添加
                    if(emptyCellCount < tr.numCells()-3)
                    {
                        String s=StringUtils.replace(rowBuilder.toString(),"\n","");
                        tableBuilder.append(s).append( "\n");
                    }

                }
                tables.add(tableBuilder.toString());
            }

        } catch (Exception e) {
            throw  new BusinessException("读取模型定义文件出错，原因是："+e.getMessage(),e);
        }

        return tables;
    }

}
