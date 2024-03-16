package com.mt.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mt.common.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AccountExpiredException;

import java.util.Date;

/**
 * @Author Scott
 * @Date 2018-07-12 14:23
 * @Desc JWT工具类
 **/
public class QrUtil {
	// 这几个是国际通用，不建议修改

	public static final String KEY = "Q5Lyg65byA5Y+R555b6qE6IyC56L+QC857O757uf";  // 生成qrCode的key

	/**
	 * 从二维码中提取类型
	 * @param qrCode
	 * @return
	 */
	public static String getType(String qrCode) {
		String type = StringUtils.substringBefore(qrCode,"-");
		return type;
	}

	/**
	 * 从二维码解析数据对象
	 * @param qrCode
	 * @return
	 */
	public static Qr parse(String qrCode) {
		try {
			String type = StringUtils.substringBefore(qrCode,"-");
			Class<Qr> clazz = Qr.getQrClass(type);
			qrCode = StringUtils.substringAfter(qrCode,"-");
			DecodedJWT jwt = JWT.decode(qrCode);
			if ((jwt.getExpiresAt().getTime() - new Date().getTime())  < 0)
			{
				throw new AccountExpiredException("二维码已经过期，不能使用,请检查二维码的有效期");
			}
			String data = jwt.getClaim("data").asString();
			return JSON.parseObject(data,clazz);

		} catch (Exception e) {
			throw  new BusinessException("解析二维码数据出错，原因是："+e.getMessage());
		}
	}

	/**
	 * 生成二维码
	 * @param qr
	 * @return
	 */
	public static String generate(Qr qr) {
		String data = JSON.toJSONString(qr);
		String qrCode = generate(data,qr.expireTime);
		return qr.getClass().getName()+"-"+qrCode;
	}

	private static String generate(String data,Long expireTime) {
		Date date = new Date(System.currentTimeMillis() + expireTime);
		Algorithm algorithm = Algorithm.HMAC256(KEY);

		return JWT.create().withClaim("data", data).withExpiresAt(date).sign(algorithm);
	}
}
