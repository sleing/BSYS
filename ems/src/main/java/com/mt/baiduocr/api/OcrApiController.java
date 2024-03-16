package com.mt.baiduocr.api;


import com.mt.baiduocr.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * name
 *
 * @author gaohongtao
 * @date 2022/10/20
 */
@RestController
@RequestMapping("/api/ocr")
public class OcrApiController {

    @Autowired
    private OcrService ocrService;

    /**
     * 完整路径：http://localhost:80/api/ocr/awardHandle
     * 表单上传 form-datd
     * ocr处理奖状
     *
     * @param ocr 文件域 file
     * @return Json返回体
     */
    @PostMapping("/awardHandle")
    public Object awardHandle(@RequestParam("file") MultipartFile ocr) {
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        byte[] file = null;
        try {
            file = ocr.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            jsonObject.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            jsonObject.put("result", false);
            jsonObject.put("message", "上传图片失败");
        }
        try {
            jsonObject = ocrService.dataHandle(file);
        } catch (Exception e) {
            jsonObject.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            jsonObject.put("result", false);
            jsonObject.put("message", "图片识别出错");
        }
        return jsonObject;
    }

    /**
     * 完整路径：http://localhost:80/api/ocr/sealHandle
     * 表单上传 form-datd
     * ocr处理印章
     *
     * @param addr 文件域 url
     * @return Json返回体
     */
    @PostMapping("/sealHandle")
    public Object sealHeadle(@RequestParam("addr") String addr) {
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        try {
            //字符串替换（'/' 替换为 '//'）
            addr = addr.replaceAll("/", "\\\\");
            System.out.println("addr==========>"+addr);
            String url = "D:\\spring_files" + addr;
            jsonObject = ocrService.ocr_sear(url);

        } catch (Exception e) {
            System.out.println("error==============>" + e.getMessage());
            jsonObject.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            jsonObject.put("result", false);
            jsonObject.put("message", "图片识别出错");
        }
        return jsonObject;
    }

}