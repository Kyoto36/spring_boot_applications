package com.ls.common.basics.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> ApiResponse<T> success(T data){
        return ApiResponse.<T>builder()
                .code(ResultConstant.Code.SUCCESS)
                .msg(ResultConstant.Message.SUCCESS)
                .data(data).build();
    }

    public static <T> ApiResponse<T> validateFail(String msg){
        return ApiResponse.<T>builder().code(ResultConstant.Code.FAIL).msg(msg).build();
    }

    public static <T> ApiResponse<T> validateFail(String msg, T data){
        return ApiResponse.<T>builder().code(ResultConstant.Code.FAIL).msg(msg).data(data).build();
    }

    public static <T> ApiResponse<T> of(int code, String msg, T data){
        return ApiResponse.<T>builder().code(code).msg(msg).data(data).build();
    }

    public static <T> ApiResponse<T> fail(String msg){
        return ApiResponse.<T>builder().code(ResultConstant.Code.FAIL).msg(msg).build();
    }

    public static <T> ApiResponse<T> fail(int code,String msg){
        return ApiResponse.<T>builder().code(code).msg(msg).build();
    }
}
