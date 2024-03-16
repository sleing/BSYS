package com.mt.common.core.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mt.common.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class D4Util {

    public static String convertCamelCaseName2UnderScore(String name) {
        return StringUtils.lowerCase(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(name), "_"));
    }

    public static Map<String, String> createMap() {

        Map aaa = null;
//        aaa.pu
        return new LinkedHashMap<String, String>();
    }

    public static String addMapItem(Map<String, String> map, String key, String value) {
//        System.out.print(key);
//        System.out.print(value);
//        System.out.print(map.toString());
        map.put(key, value);
        return "";
    }

    //解析方法名
    public static String getAttributerGetterName(String attritureName) {
        String invokeMethodName = attritureName.substring(0, 1).toUpperCase().concat(attritureName.substring(1));
        return "get" + invokeMethodName;
    }

    //通过传入的对象和字符串调用该对象的方法,并返回结果
    public static String invokeMethodByString(Object obj, String method) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
        Class clazz = obj.getClass();
        try {
            Method method1 = clazz.getMethod(method);
            return method1.invoke(obj) + "";
        } catch (NoSuchMethodException e) {
            throw new BusinessException(clazz.getCanonicalName() + " 无此构造方法:" + method);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 拼接多个条件的模糊查询sql
     *
     * @param columns
     * @param contents
     * @return 拼接好的sql
     */
    public static String assembleSql(String[] columns, String[] contents) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < columns.length; i++) {
            if (!"".equals(contents[i])) {//要求不是空内容，不然拼接出来的sql报错
                str.append(com.mt.common.core.utils.D4Util.assembleSql(columns[i], contents[i]) + " and ");
            }
        }
        return str.toString().substring(0, str.length() - 4);
    }

    public static String assembleSql(String columnName, String content) {
        if (columnName == null || "".equals(columnName) || content == null || "".equals(content)) {
            return " 1=1 ";
        }
        String[] items = content.split("\\s+");//or
        String[] anditems = content.split("\\s+and\\s");//and
        String sql = "";
        String hump = "";
        hump = humpToUnderline(columnName);
        if (anditems.length > 1) {
            sql = "(";
            for (int i = 0; i < anditems.length; i++) {
                if (i == anditems.length - 1) {
                    sql += "t0." + hump + "  LIKE '%" + anditems[i] + "%')";
                } else {
                    sql += "t0." + hump + "  LIKE '%" + anditems[i] + "%'  AND ";
                }

            }
        } else {
            if (items.length == 0 || items.length == 1 && "".equals(items[0])) {
                sql = "(" + "t0." + hump + "  LIKE '%' )";
            } else {
                sql = "(";
                for (int i = 0; i < items.length; i++) {
                    if (i == items.length - 1) {
                        sql += "t0." + hump + "  LIKE '%" + items[i] + "%')";
                    } else {
                        sql += "t0." + hump + "  LIKE '%" + items[i] + "%'  OR ";
                    }

                }
            }
        }

        return sql;
    }


    /**
     * Description: 首字母大写
     * @author 袁泽锋
     * @since 2019/12/1 16:34
     * @param str 字符串
     * @return java.lang.String
     */
    public static String capitalize(String str) {
        char[] cs = str.toCharArray();
        if (cs.length > 0 && cs[0] >= 97 && cs[0] <= 122) cs[0] -= 32;
        return String.valueOf(cs);
    }

    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */

    public static String humpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;//定位
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toUpperCase();
    }


    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */

    public static String humpToUnderlineNew(String para) {
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;//定位
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /***
     * 下划线命名转为驼峰命名
     *
     * @param para
     *        下划线命名的字符串
     */
    public static String underlineToHump(String para) {
        StringBuilder result = new StringBuilder();
        String a[] = para.split("_");
        for (String s : a) {
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 将对象转为json串
     *
     * @param object 对象
     * @return json
     */
    static {
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    }
    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
    }
    //通过传入FileInputStream数组,返回压缩文件
    /*
     * @parms fileAddress 数据库的 attachment_addr字段数组(必填)
     * @parms fileNames 数据库的attachment_real_name 字段数组(必填)
     * @parms compressFileName要下载的压缩文件名，如果不填默认为当前时间戳
     * @parms fileType 压缩文件的后缀,不填默认为zip
     */
   /* public static OutputStream getZipFile(String[] fileAddress,String[] fileNames,String compressFileName) throws IOException {
        if(fileAddress!=null&&fileAddress.length>0){

            if(compressFileName==null||compressFileName.equals("")){
                compressFileName=System.currentTimeMillis()+""+(int)(Math.random()*10000);
            }
            File zipFile=new File(Config.uploadFileAddress+"\\"+compressFileName+".zip");
            //实例化 ZipOutputStream对象
            ZipOutputStream zipOutputStream=new ZipOutputStream(new FileOutputStream(zipFile));
            //创建ZipEntry对象
            ZipEntry zipEntry=null;
            FileInputStream fileInputStream=null;
            File[]  files=new File[fileAddress.length];
            for(int i=0;i<fileAddress.length;i++){
                String file=Config.uploadFileAddress+fileAddress[i];
                 files[i] =new File(file);
                if(!files[i].exists()){
                    throw new BusinessException(fileNames[i]+"文件不存在请重新上传");
                }
                fileInputStream=new FileInputStream(files[i]);
             zipEntry=new ZipEntry(fileNames[i]);
             zipOutputStream.putNextEntry(zipEntry);
             Integer len;
             byte[] buffer=new byte[1024];
             while ((len=fileInputStream.read(buffer))>0){
                   zipOutputStream.write(buffer,0,len);
             }
            }



            zipOutputStream.closeEntry();
            zipOutputStream.close();
            fileInputStream.close();
            zipFile.delete();
        }
        else{
            throw new BusinessException("输入流不能为空");
        }



        return null;
    }
*/

    /**
     * 处理数据方法
     * <p>批量将List<Object>类型的数据转换成List<Map<T>>的数据</p>
     *
     * @param rows 行数据
     * @return rowList 返回处理的结果 List<Map<String ,String>> 其中是已经处理好的  对象->map
     * @author gjt
     */
    public static List<Map> objectToListMap(List<Object> rows) {

        if (rows.size() < 0) {
            throw new BusinessException("传入数据不能为空");
        }

        List<Map> rowList = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            Class clazz = rows.get(i).getClass();
            Field[] fields = clazz.getDeclaredFields();
            Map<String, Object> getFieldsNames = new HashMap<>();
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                try {
                    getFieldsNames.put(fields[j].getName(), fields[j].get(rows.get(i)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            rowList.add(getFieldsNames);
        }
        return rowList;
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }


    /**
     * Description: 生成随机码
     * Date: 2019/9/15 17:07
     *
     * @param nOfNum    码中数字的数量
     * @param nOfLetter 码中字母的数量
     * @param Ordered   是否有序分布字母与数字，是则字母在前
     * @return java.lang.String
     **/
    public static String genRandomCode(int nOfNum, Integer nOfLetter, boolean Ordered) {
        char[] num = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] letter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z'};

        StringBuilder pwd = new StringBuilder();
        Random random = new Random();
        if (Ordered) {
            for (int i = 0, pwdLen = nOfNum + nOfLetter; i < pwdLen; i++) {
                if (nOfLetter > 0) {
                    pwd.append(letter[random.nextInt(letter.length)]);
                    nOfLetter--;
                } else {
                    pwd.append(num[random.nextInt(num.length)]);
                    nOfNum--;
                }
            }
        }
        for (int i = 0, pwdLen = nOfNum + nOfLetter; i < pwdLen; i++) {
            if (nOfNum > 0) {
                if (nOfLetter <= 0 || random.nextInt(2) == 0) {
                    pwd.append(num[random.nextInt(num.length)]);
                    nOfNum--;
                    continue;
                }
            }
            pwd.append(letter[random.nextInt(letter.length)]);
            nOfLetter--;
        }

        return pwd.toString();
    }

    /**
     * Description: 创建文件
     * Date: 2019/9/12 9:56
     *
     * @param destFileName 文件名
     * @return boolean
     **/
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            return false;
        }
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            return file.getParentFile().mkdirs();
        }
        //创建目标文件
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }

    //Map里面的key和字符串里面${}里面的名称保持以一致
    //如：daw${name} Map里面有name为key的键值对即可替换
    public static String processTemplate(String template, Map<String, Object> params){
        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(template);
        while (m.find()) {
            String param = m.group();
            Object value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value==null ? "" : value.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static List<String> getPatterns(String regex,String value)
    {
        if(value == null || "".equals(value))
        {
            return null;
        }
        List<String> results = new ArrayList<String>();
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(value);
        while(matcher.find()){
            String group = matcher.group();
            results.add(group);
        }

        return results;
    }

    public static String getPattern(String regex,String value)
    {
        String result = "";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(value);
        while(matcher.find()){
            result = matcher.group();
            break;
        }

        return result;
    }
    public static String getNowDateTimeAsFileName() {
        String datetime = getSimpleDateFormat("yyyyMMddHHmmssS").format(
                new Date());
        return datetime;
    }
    private static SimpleDateFormat getSimpleDateFormat(String format) {
        SimpleDateFormat tempDate = new SimpleDateFormat(format);
        return tempDate;
    }
}
