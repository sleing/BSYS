package com.mt.common.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.mt.common.core.web.BaseController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "报表")
@RestController
@RequestMapping("/api/sys/report")
public class ReportController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @GetMapping("/exportPdf/{excelConfigId}")
    public void exportPdf(@PathVariable String excelConfigId, HttpServletRequest request, HttpServletResponse response){
        RestTemplate restTemplate = new RestTemplate();
        //创建url路径 a
        String url = "http://localhost:8081/jmreport/exportPdf";
        Map<String,Object> map = new HashMap<>();

//        JSONObject object = new JSONObject();
//        request.getParameterMap().entrySet()
//        //此处为示例，需要传递api和sql的正确参数值
//        object.put("name","张三");
//        //queryParam中有个特殊属性 dpi每英寸点数,windows是96，可不用传
//        object.put("dpi","96");
//        let url = "http://localhost:8081/jmreport/view/590489426670854144?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzIyOTYyODAsInVzZXJuYW1lIjoiYWRtaW4ifQ.Cord88qnkYQQ9wRRUI1p3F8FOjJiBE0bL087dOxm2mg"
//   http://localhost:8081/api/sys/report/exportPdf/590489426670854144?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzIzOTQ5ODIsInVzZXJuYW1lIjoiYWRtaW4ifQ.SyoE0fAdNmvk2dV3kJlRW_xAKNsoHLhcDRI_JgGSeTw
        map.put("excelConfigId",excelConfigId);
        map.put("queryParam",request.getParameterMap());
        HttpHeaders headers = new HttpHeaders();
        //如果有字典需要传token
        headers.add("token",request.getParameter("token"));
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(map,headers);
        try {
            String apiResult = restTemplate.postForObject(url, httpEntity, String.class);
            JSONObject jsonObject = JSONObject.parseObject(apiResult);
            Object code = jsonObject.get("code");
            Object message = jsonObject.get("message");
            if(null != code && !"".equals(String.valueOf(code)) && "200".equals(String.valueOf(code))){
                JSONObject result = jsonObject.getJSONObject("result");
                //文件byte64字符串
                String file = result.getString("file");
                //文件名称
                String name = result.getString("name");
                //转换成byte
                byte[] buffer = Base64Utils.decodeFromString(file);
                name = URLEncoder.encode(name, "UTF-8");
                response.setContentType("application/pdf");
//                response.setHeader("Content-Disposition", "attachment;filename=" + name);
                response.getOutputStream().write(buffer);
                response.getOutputStream().flush();
            }else{
                logger.error("调用失败:"+String.valueOf(message));
            }
        } catch (RestClientException e) {
            logger.error("访问积木报表的导出pdf API出错，原因是："+e.getMessage());
            e.printStackTrace();
        }catch (Exception e) {
            logger.error("访问积木报表的导出pdf出错，原因是："+e.getMessage());
            e.printStackTrace();
        }
    }
}
