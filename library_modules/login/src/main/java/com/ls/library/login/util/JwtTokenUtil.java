package com.ls.library.login.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ls.library.login.context.BaseContextConstants;
import com.ls.library.login.entity.JwtUserInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <描述功能>
 *
 * @author Lang
 * @Classname JwtTokenUtil
 * @Version 1.0.0
 * @Date 2021/12/17 22:07
 **/
@Slf4j
public class JwtTokenUtil {

    /**
     * Token过期时间30分钟
     * @author lang
     **/
    private static final long EXPIRE_TIME = 30 * 60 * 1000;

    public static final String TOKEN = "token";

    /**
     * <p> 校验token是否正确 </p>
     * @Param token     token
     * @Param secret    私钥
     * @Return boolean
     */
    public static boolean verify(String token, String secret)throws Exception {
        try {
            // 设置加密算法
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * <描述功能> <p>生成签名,30min后过期 </p>
     * @author lang
     * @param userInfo 用户
     * @param secret 私钥
     * @return java.lang.String
     **/
    public static String sign(JwtUserInfo userInfo, String secret) {
        String options = "";
        if (userInfo.getOptions() != null && !userInfo.getOptions().isEmpty()) {
            options = JSONUtil.toJsonStr(userInfo.getOptions());
        }
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(Convert.toStr(userInfo.getUserId(), StringPool.EMPTY))
                .withClaim(BaseContextConstants.JWT_KEY_USERNAME, userInfo.getUserName())
                .withClaim(BaseContextConstants.JWT_KEY_CELL_PHONE, userInfo.getCellPhone())
                .withClaim(BaseContextConstants.JWT_KEY_OPTIONS, options)
                .withExpiresAt(date)
                .sign(algorithm);

    }

    /**
     * <描述功能> <p> 获得用户名 </p>
     * @author lang
     * @param request
     * @return java.lang.String
     **/
    public static JwtUserInfo getUserByToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        DecodedJWT jwt = JWT.decode(token);
        String userIdStr = jwt.getSubject();
        String userName = Convert.toStr(jwt.getClaim(BaseContextConstants.JWT_KEY_USERNAME).asString(), StringPool.EMPTY);
        String cellPhone = Convert.toStr(jwt.getClaim(BaseContextConstants.JWT_KEY_CELL_PHONE).asString(), StringPool.EMPTY);
        Integer userId = Convert.toInt(userIdStr, 0);
        Map<String, Object> options = new HashMap<>();
        String optionStr = Convert.toStr(jwt.getClaim(BaseContextConstants.JWT_KEY_OPTIONS).asString(), StringPool.EMPTY);
        if (StringUtils.isNotBlank(optionStr)) {
            options = JSONUtil.parseObj(optionStr);
        }
        return new JwtUserInfo(userId, userName, cellPhone, options);
    }
}
