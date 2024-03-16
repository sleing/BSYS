package com.mt.common.core.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mt.common.core.web.JsonResult;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName QrCodeUtil
 * @Description TODO:二维码工具类
 * @Author ycs
 * @Date 2021/8/25 11:34
 * @Version 1.0
 */
public class QrCodeUtil {
    /**
     * 生成一个二维码图片
     *
     * @param width
     * @param height
     * @param content
     * @return 返回一个二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] createQRCode(int width, int height, String content) throws WriterException, IOException {
        // 二维码基本参数设置
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码字符集utf-8
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);// 设置纠错等级L/M/Q/H,纠错等级越高越不易识别，当前设置等级为最高等级H
        hints.put(EncodeHintType.MARGIN, 0);// 可设置范围为0-10，但仅四个变化0 1(2) 3(4 5 6) 7(8 9 10)
        // 生成图片类型为QRCode
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        // 创建位矩阵对象
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, format, width, height, hints);
        // 设置位矩阵转图片的参数
        // MatrixToImageConfig config = new MatrixToImageConfig(Color.black.getRGB(), Color.white.getRGB());
        // 位矩阵对象转流对象
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", os);
        return os.toByteArray();
    }

    /**
     * 测试
     * @param args
     * @throws WriterException
     * @throws IOException
     */
    public static void main(String[] args) throws WriterException, IOException {
        byte[] b = createQRCode(400, 400, JsonResult.ok().put("data", "遇见最好的自己！").toString());
        OutputStream os = new FileOutputStream("D:\\bestme.png");
        os.write(b);
        os.close();
    }

    //将二维码中的信息字符串解析为Map

    public static Map<String, Object> parsing(String content){
        Map<String, Object> infoMap = new HashMap<>();
        String[] str = content.split("&&");
        String vgdecoderesult = str[0].substring(str[0].indexOf("=") + 1);
        String devicenumber = str[1].substring(str[1].indexOf("=") + 1);
        infoMap.put("devicenumber",devicenumber);
        if(StringUtils.contains(vgdecoderesult,"*")){
            String[] infos = StringUtils.split(vgdecoderesult, "*");
            for (String info:infos
                 ) {
                if(StringUtils.contains(info,":")) {
                    String[] keyAndValue = StringUtils.split(info, ":");
                    infoMap.put(keyAndValue[0],keyAndValue[1]);
                }else{
                    infoMap.put(info,info);
                }

            }

        }else{
            if(StringUtils.contains(vgdecoderesult,":")) {
                String[] keyAndValue = StringUtils.split(vgdecoderesult, ":");
                infoMap.put(keyAndValue[0],keyAndValue[1]);
            }else{
                infoMap.put(vgdecoderesult,vgdecoderesult);
            }
        }

        return infoMap;
    }

}