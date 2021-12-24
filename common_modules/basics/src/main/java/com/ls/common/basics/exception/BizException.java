package com.ls.common.basics.exception;

import com.ls.common.basics.result.ResultConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 * @author lang
 * @date 2021-12-14 17:04:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -778887391066124051L;
    private int code;
    private String message;

    public BizException(String message){
        super(message);
        this.code = ResultConstant.Code.FAIL;
        this.message = message;
    }
    public BizException(int code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(int code, String message, Throwable e){
        super(message, e);
        this.code = code;
        this.message = message;
    }
}
