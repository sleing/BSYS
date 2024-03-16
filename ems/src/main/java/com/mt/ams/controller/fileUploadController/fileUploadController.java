package com.mt.ams.controller.fileUploadController;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(tags = "文件上传")
@RestController
public class fileUploadController {

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/mm/dd/");

    @PostMapping("/fileUpload")
    public Map<String, Object> fileUpload(MultipartFile file, HttpServletRequest req) {
        Map<String, Object> result = new HashMap<>();
        String originName = file.getOriginalFilename();
        if (originName.endsWith(".png") || originName.endsWith(".jpg")) {
            result.put("status", "error");
            result.put("msg", "文件类型不对");
            System.out.println("文件类型不对---------------");
            return result;
        }
        String format = sdf.format(new Date());
        String realPath = req.getServletContext().getRealPath("/") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String newName = UUID.randomUUID().toString() + ".jpg";
        try {
            file.transferTo(new File(folder, newName));
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + format + newName;
            result.put("url", url);
        } catch (IOException e) {
            result.put("status", "error");
            result.put("msg", e.getMessage());
            System.out.println("文件出错---------------");
            return result;
        }

        return result;
    }

}
