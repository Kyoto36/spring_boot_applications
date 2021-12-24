package com.ls.common.basics.result;

public interface ResultConstant {

    interface Code{
        // 成功
        int SUCCESS = 0;

        // 失败
        int FAIL = -1;

        // 数据已存在
        int EXISTS = 1001;

        // 数据不存在
        int NOT_EXISTS = 1002;
    }

    interface Message{
        String SUCCESS = "SUCCESS";
    }

}
