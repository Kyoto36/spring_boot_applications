package com.ls.library.sys_log.aspect;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.thread.threadlocal.NamedThreadLocal;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.ls.common.basics.annotation.SysLog;
import com.ls.common.basics.enumerate.BusinessType;
import com.ls.common.basics.result.ApiResponse;
import com.ls.common.basics.result.ResultConstant;
import com.ls.common.basics.util.CommonUtil;
import com.ls.common.basics.util.ThreadPoolUtil;
import com.ls.library.sys_log.model.SysOperateLog;
import com.ls.library.sys_log.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *  系统操作日志记录切面
 * @author lang
 * @date 2021年12月14日09点48分
 */
@Component
@Aspect
@Slf4j
public class SysLogAspect {

    public static final int MAX_LENGTH = 65535;
    private static final ThreadLocal<SysOperateLog> beginTimeThreadLocal = new NamedThreadLocal<SysOperateLog>("ThreadLocal beginTime");

    private SysLogService sysLogService;

    public SysLogAspect(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Pointcut("@annotation(com.ls.common.basics.annotation.SysLog)")
    public void controllerAspect() {

    }

    /**
     * 前置通知 (在方法执行之前返回)用于拦截Controller层记录用户的操作的开始时间
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLog sysLogAnno = CommonUtil.getTargetAnno(joinPoint);
        final BusinessType businessType = sysLogAnno.businessType();
        final String des = sysLogAnno.des();
        Map<String, String[]> logParams = request.getParameterMap();


        SysOperateLog log = new SysOperateLog();

        log.setRequestIp(ServletUtil.getClientIP(request));
        log.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        log.setType(businessType.toString());
        log.setHttpMethod(request.getMethod());
        if(MapUtil.isNotEmpty(logParams)){
            log.setRequestParams(JSONUtil.toJsonStr(logParams));
        }
        log.setDescription(des);
        //线程绑定变量（该数据只有当前请求的线程可见）
        LocalDateTime date = LocalDateTime.now();
        log.setStartTime(date);
        beginTimeThreadLocal.set(log);
    }


    /**
     * 后置通知(在方法执行之后并返回数据) 用于拦截Controller层无异常的操作
     * @param joinPoint 切点
     */
    @AfterReturning(value = "controllerAspect()", returning = "returnValue")
    public void after(JoinPoint joinPoint, Object returnValue){
        try {
            //请求开始时间
            SysOperateLog threadLocalLog = beginTimeThreadLocal.get();

            threadLocalLog.setFinishTime(LocalDateTime.now());
            threadLocalLog.setConsumingTime(threadLocalLog.getStartTime().until(threadLocalLog.getFinishTime(), ChronoUnit.MILLIS));
            if(Objects.nonNull(returnValue)){
                    ApiResponse response = (ApiResponse)returnValue;
                    if(ResultConstant.Code.SUCCESS == response.getCode()){
                        threadLocalLog.setState("1");
                    }else{
                        threadLocalLog.setState("0");
                    }
            }


            //调用线程保存至log表
            ThreadPoolUtil.getPool().execute(new SaveSystemLogThread(threadLocalLog, sysLogService));
            beginTimeThreadLocal.remove();
        } catch (Exception e) {
            log.error("AOP后置通知异常", e);
        }
    }

    /**
     * 异常通知
     *
     * @param e
     */
    @AfterThrowing(value = "controllerAspect()", throwing = "e")
    public void doAfterThrowable(JoinPoint joinPoint, Throwable e) {
        tryCatch((aaa) -> {

            SysOperateLog sysLog = beginTimeThreadLocal.get();
            sysLog.setType(BusinessType.EXCEPTION.toString());
            sysLog.setState("0");

            // 异常描述
            sysLog.setExDetail(ExceptionUtil.stacktraceToString(e, MAX_LENGTH));
            // 异常详情
            sysLog.setExDesc(ExceptionUtil.getSimpleMessage(e));

            sysLog.setFinishTime(LocalDateTime.now());
            sysLog.setConsumingTime(sysLog.getStartTime().until(sysLog.getFinishTime(), ChronoUnit.MILLIS));
            //调用线程保存至log表
            ThreadPoolUtil.getPool().execute(new SaveSystemLogThread(sysLog, sysLogService));
            beginTimeThreadLocal.remove();

        });
    }


    /**
     * 保存日志至数据库
     */
    private static class SaveSystemLogThread implements Runnable {

        private SysOperateLog log;
        private SysLogService logService;

        public SaveSystemLogThread(SysOperateLog esLog, SysLogService logService) {
            this.log = esLog;
            this.logService = logService;
        }

        @Override
        public void run() {

            logService.save(log);
        }
    }

    private void tryCatch(Consumer<String> consumer) {
        try {
            consumer.accept("");
        } catch (Exception e) {
            log.warn("记录操作日志异常", e);
            beginTimeThreadLocal.remove();
        }
    }

}
