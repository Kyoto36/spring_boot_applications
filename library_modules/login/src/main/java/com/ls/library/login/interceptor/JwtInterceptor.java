package com.ls.library.login.interceptor;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ls.common.basics.result.ApiResponse;
import com.ls.library.login.context.BaseContextHandler;
import com.ls.library.login.entity.JwtUserInfo;
import com.ls.library.login.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * <描述功能>
 *
 * @author Lang
 * @Classname JwtInterceptor
 * @Version 1.0.0
 * @Date 2021/12/17 22:10
 **/
@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private static final String OPTIONS = "OPTIONS";

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(OPTIONS.equals(request.getMethod().toUpperCase())){
            // 通过所有OPTION请求
            return true;
        } else {
            // 从 http 请求头中取出 token
            String token = request.getHeader(JwtTokenUtil.TOKEN);
            log.debug(request.getRequestURI());
            String responseMsg = "您无权访问";
            if (token != null) {
                try {
                    boolean result = JwtTokenUtil.verify(token, secret);
                    if (result) {
                        log.debug("通过拦截器");
                        final JwtUserInfo userByToken = JwtTokenUtil.getUserByToken(request);
                        BaseContextHandler.setJwtUserInfo(userByToken);
                        return true;
                    }
                }catch (SignatureVerificationException e) {
                    log.info(e.getMessage());
                    responseMsg = "无效签名";
                } catch (TokenExpiredException e) {
                    log.info(e.getMessage());
                    responseMsg = "token过期";
                }  catch (AlgorithmMismatchException e){
                    log.info(e.getMessage());
                    responseMsg = "不支持的签名";
                } catch (Exception e) {
                    log.info(e.getMessage());
                    responseMsg = "token无效";
                }


            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {

                out = response.getWriter();
                out.append(JSONUtil.toJsonStr(ApiResponse.validateFail(responseMsg)));
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(500);
            }
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
    }
}
