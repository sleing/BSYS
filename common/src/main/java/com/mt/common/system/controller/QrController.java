package com.mt.common.system.controller;

import com.google.zxing.WriterException;
import com.mt.common.core.utils.QrCodeUtil;
import com.mt.common.core.web.JsonResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;

/**
 * @ClassName QrController
 * @Description TODO 二维码设备
 * @Author ycs
 * @Date 2021/8/20 9:03
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/qr")
@CrossOrigin(allowCredentials = "true")
public class QrController {
    /**
     * 二维码扫码接收
     *
     * @param content 二维码信息
     * @return code 0000代表成功否则失败
     */
    //api/erp/trans/Driver/signIn
    //api/qr/receive
    @PostMapping("/receive")
    public String receive(@RequestBody String content) {
        String[] str = content.split("&&");
        String vgdecoderesult = str[0].substring(str[0].indexOf("=") + 1);
        String devicenumber = str[1].substring(str[1].indexOf("=") + 1);
        System.out.println(vgdecoderesult);//解码内容
        System.out.println(devicenumber);//设备号
        return "0000";
    }
    ////司机打卡
    //@PostMapping("driverSignIn")
    //public JsonResult signIn(@RequestBody String content){
    //    return this.driverService.signIn(content);
    //}
    /**
     * 获取二维码
     *
     * @return
     */
//    @GetMapping("/getQrCode")
//    public JsonResult getQrCode() {
//        QrOne qrOne = new Qr();
//        qrOne.setSubject("测试");
//        String qrcode = QrUtil.generate(qrOne);
//        System.out.println(qrcode);
//        Qr newQr = QrUtil.parse(qrcode);
//        return JsonResult.ok().put("qrcode", qrcode);
//    }
    @GetMapping("createQRCode")
    public byte[] createQRCode(@Param("content")String content, HttpServletResponse response) throws IOException, WriterException {
        return QrCodeUtil.createQRCode(400, 400, content);
    }

}
