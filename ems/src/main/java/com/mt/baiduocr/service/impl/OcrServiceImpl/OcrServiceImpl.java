package com.mt.baiduocr.service.impl.OcrServiceImpl;


import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.mt.baiduocr.service.OcrService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import static com.mt.baiduocr.vo.FileUtil.readFileByBytes;

@Service
public class OcrServiceImpl implements OcrService {

    @Value("${baidu.ocr.appId}")
    private String APP_ID;
    @Value("${baidu.ocr.apiKey}")
    private String API_KEY;
    @Value("${baidu.ocr.secretKey}")
    private String SECRET_KEY;


    private Pattern hAsDigitPattern = Pattern.compile(".*\\d+.*");
    private Pattern isDigitPattern = Pattern.compile("[0-9]{1,}");
    private Pattern isDigitPrePattern = Pattern.compile("^(\\d+)(.*)");
    private Pattern getStringOfNumbersPattern = Pattern.compile("\\d+");
    private Pattern splitNotNumberPattern = Pattern.compile("\\D+");
    private Pattern floatsNumberPattern = Pattern.compile("[\\d.]{1,}");
    private Pattern trimNumberPattern = Pattern.compile("\\s*|\\t|\\r|\\n");

    /**
     * 百度ocr（高精度包含位置类型 accurateGeneral ）
     *
     * @param bytes
     * @return
     */
    @Override
    public List<String> ocr_accurateGeneral(byte[] bytes) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        HashMap<String, String> options = new HashMap<String, String>();

        //参数（高精度含位置）
        options.put("recognize_granularity", "big");
        options.put("detect_direction", "true");
        options.put("vertexes_location", "true");
        options.put("probability", "true");

        //accurateGeneral（高精度含位置）
        org.json.JSONObject res = client.accurateGeneral(bytes, options);

        JSONArray jsonArray = res.getJSONArray("words_result");
        //输出JSON数组
        System.out.println("JSON数组===================>" + jsonArray);
        int length = jsonArray.length();
        List<String> list = new ArrayList<>(length);
        System.out.println("jsonArray length ==============>" + length);
        for (int i = 0; i < length; i++) {
            org.json.JSONObject result = (org.json.JSONObject) jsonArray.get(i);
            list.add(result.getString("words"));
        }

        return list;
    }




    /**
     * 百度ocr（印章识别 seal）
     *
     * @param url
     * @return
     */
    @Override
    public JSONObject ocr_sear(String url) throws IOException {

        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();

        // 调用印章识别
        byte[] image = readFileByBytes(url);
        org.json.JSONObject result = client.seal(image);
        // org.json.JSONObject result = client.sealUrl(url);  //url 可能本地的不行
        JSONArray jsonArray = result.getJSONArray("result");

        System.out.println("Result====================>" + result);
        System.out.println("jsonArray====================>" + jsonArray);

        //按照印章从左向右排序
//        jsonArray.sort(Comparator.comparing(obj->((JSONObject)obj).getJSONObject("location").getIntValue("left"),
//                Comparator.nullsFirst(Comparator.naturalOrder())));

//        jsonArray.sort(Comparator.comparing(obj -> {
//            int value = ((JSONObject) obj).getJSONObject("location").getIntValue("left");
//            return value;
//        }));

//        Collections.sort(jsonArray, new Comparator<JSONObject>() {
//            //重写compare方法
//            @Override
//            public int compare(JSONObject obj1, JSONObject obj2) {
//                int val1 = obj1.getJSONObject("location").getIntValue("left");  //获取位置left
//                int val2 = obj1.getJSONObject("location").getIntValue("left");  //获取位置left
//                return val1 - val2;
//            }
//        })

        //转化为String
        List<String> list =  turnToList(jsonArray);
        //整理转化为jsonObject
        JSONObject jsonObject =  handleOcrSeal(list);
        return jsonObject;
    }

    /**
     * jsonArray 转化为 list
     *
     * @param jsonArray
     * @return list
     */
    public List<String> turnToList(JSONArray jsonArray){
        int length = jsonArray.length();
        Map<Integer, Integer> map_left = new TreeMap<Integer, Integer>();   //treeMap按照key排序
        //按照印章从左向右排序
        List<String> list = new ArrayList<>(length);
        for( int k = 0 ; k < length ; k++){
            org.json.JSONObject result = (org.json.JSONObject)jsonArray.get(k);
            map_left.put(Integer.valueOf(result.getJSONObject("location").getInt("left")),Integer.valueOf(k));
        }
        Set<Integer> keySet = map_left.keySet();
        for(Integer i : keySet){
            i = map_left.get(i);    //获取map中的value值（位置由小到大的顺序）
            org.json.JSONObject result = (org.json.JSONObject)jsonArray.get(i);
            org.json.JSONObject jsonObject1 = result.getJSONObject("major");
//            org.json.JSONObject jsonObject2 = result.getJSONObject("location");
            list.add(jsonObject1.toString());
            //System.out.println("jsonArray===>"+i+"====>"+jsonArray.get(i));
        }
        return list;
    }

    /**
     * 印章识别 整理识别结果转化为jsonObject返回
     * @param list
     * @return JSONObject（fastJson)
     * @throws Exception
     */
    public JSONObject handleOcrSeal(List<String> list){
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        int len = list.size();
        jsonObject.put("number",len);

        for(int i = 0; i < len; i++){
            System.out.println("ocr seal list ========>" + list.get(i));

            //String key = "印章" + i;
            jsonObject.put(String.valueOf(i), JSONObject.parseObject(String.valueOf(list.get(i))));
        }
        return jsonObject;
    }


    @Override
    public JSONObject dataHandle(byte[] bytes) throws Exception {
        List<String> list = ocr_accurateGeneral(bytes);
        //return structuralization(list);
        return null;
    }

}
