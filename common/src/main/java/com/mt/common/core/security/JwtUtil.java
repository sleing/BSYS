package com.mt.common.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.mt.common.core.config.Config;
import com.mt.common.core.utils.SpringContextUtils;
import com.mt.common.system.service.UserService;
import org.apache.commons.lang3.StringUtils;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import com.mt.common.system.entity.User;
/**
 * @Author Scott
 * @Date 2018-07-12 14:23
 * @Desc JWT工具类
 **/
public class JwtUtil {
	// 这几个是国际通用，不建议修改
	public static final String TOKEN_TYPE = "Bearer";  // token认证类型
	public static final String TOKEN_HEADER_NAME = "Authorization";  // token在header中字段名称
	public static final String TOKEN_PARAM_NAME = "access_token";  // token在参数中字段名称

//	public static final Long TOKEN_EXPIRE_TIME = 1000 * 60 * 300L;  // token过期时间,单位为微秒
//	public static final Long TOKEN_WILL_EXPIRE =  1000 * 60 * 30L;  // token将要过期自动刷新,单位分钟
	public static final String TOKEN_KEY = "5b6Q5Lyg6L+Q5byA5Y+R55qE6IyC56C857O757uf";  // 生成token的key
	/**
	 * 校验token是否正确
	 *
	 * @param token  密钥
	 * @param secret 用户的密码
	 * @return 是否正确
	 */
	public static boolean verify(String token, String username, String secret) {
		try {
			// 根据密码生成JWT效验器
			Algorithm algorithm = Algorithm.HMAC256(secret + TOKEN_KEY);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
			// 效验TOKEN
			DecodedJWT jwt = verifier.verify(token);
			return true;
		}
		catch (TokenExpiredException e )
		{
			throw new AccountExpiredException("身份令牌过期,须重新登录!");
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}
	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @return token中包含的用户名
	 */
	public static Date getExpiration(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getExpiresAt();
		} catch (JWTDecodeException e) {
			return null;
		}
	}
	/**
	 * 生成签名,5min后过期
	 *
	 * @param username 用户名
	 * @param secret   用户的密码
	 * @return 加密的token
	 */
	public static String sign(String username, String secret) {
		Date date = new Date(System.currentTimeMillis() + Config.tokenExpireTime);
		Algorithm algorithm = Algorithm.HMAC256(secret+TOKEN_KEY);
		// 附带username信息
		return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
	}

	/**
	 * 根据request中的token获取用户账号
	 *
	 * @param request
	 * @return
	 * @throws AccountExpiredException
	 */
	public static String getUserNameByToken(HttpServletRequest request) throws AccountExpiredException {
		String accessToken = request.getHeader("X-Access-Token");
		String username = getUsername(accessToken);
		if (StringUtils.isEmpty(username)) {
			throw new AccountExpiredException("未获取到用户");
		}
		return username;
	}


	/**
	 * 获取 request 里传递的 token
	 *
	 * @param request
	 * @return
	 */
	public static String getTokenByRequest(HttpServletRequest request) {
		String token = request.getParameter("token");
		if (token == null) {
			token = request.getHeader(JwtUtil.TOKEN_HEADER_NAME);
		}
		return token;
	}

	/**
	 * 验证Token
	 */
	public static String verifyToken(HttpServletRequest request, UserDetailsService userDetailsService, RedisUtil redisUtil) {
		String token = getTokenByRequest(request);

		token = verifyToken(token,userDetailsService, redisUtil);

		return token;
	}

	/**
	 * 刷新token（保证用户在线操作不掉线）
	 * @param token
	 * @param userName
	 * @param passWord
	 * @param
	 * @return
	 */
	private static String  jwtTokenRefresh(String token, String userName, String passWord) {
//		String cacheToken = String.valueOf(redisUtil.get(JwtUtil.PREFIX_USER_TOKEN + token));
//		if (StringUtils.isNotEmpty(cacheToken)) {
//			// 校验token有效性
//			if (!JwtUtil.verify(cacheToken, userName, passWord)) {
//				String newAuthorization = JwtUtil.sign(userName, passWord);
//				// 设置Toekn缓存有效时间
//				redisUtil.set(JwtUtil.PREFIX_USER_TOKEN + token, newAuthorization);
//				redisUtil.expire(JwtUtil.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);
//			}
//			//update-begin--Author:scott  Date:20191005  for：解决每次请求，都重写redis中 token缓存问题
////            else {
////                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, cacheToken);
////                // 设置超时时间
////                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME / 1000);
////            }
//			//update-end--Author:scott  Date:20191005  for：解决每次请求，都重写redis中 token缓存问题
//			return null;
//		}
//		return null;


		// token将要过期签发新token, 防止突然退出登录
		if ((JwtUtil.getExpiration(token).getTime() - new Date().getTime())  < Config.tokenWillExpireTime) {
			UserService userService = SpringContextUtils.getBean(UserService.class);
			//刷新用户缓存
			userService.resetCache(userName);
			String access_token_new = JwtUtil.sign(userName, passWord );
		 	return access_token_new;
		}
		return token;
	}

	/**
	 * 验证Token
	 */
	public static String verifyToken(String token, UserDetailsService userDetailsService, RedisUtil redisUtil) {
		if (StringUtils.isBlank(token)) {
			throw new AccountExpiredException("身份令牌不能为空!");
		}

		// 解密获得username，用于和数据库进行对比
		String username = JwtUtil.getUsername(token);
		if (username == null) {
			throw new AccountExpiredException("身份令牌非法无效!");
		}

		// 查询用户信息
		LoginUser user = (LoginUser)userDetailsService.loadUserByUsername(username);
		if (user == null) {
			throw new AccountExpiredException("用户不存在!");
		}
		// 判断用户状态
		if (user.getState()!= 0) {
			throw new AccountExpiredException("账号已被锁定,请联系管理员!");
		}
		if (!JwtUtil.verify(token, username, user.getPassword())) {
			throw new AccountExpiredException("身份令牌无效!");
		}
		// 校验token是否超时失效 & 或者账号密码是否错误
		token =jwtTokenRefresh(token, username, user.getPassword());

		return token;
	}
	public static LoginUser getLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) return null;
		Object object = authentication.getPrincipal();
		if (object != null) return (LoginUser) object;

		return null;
	}
}
