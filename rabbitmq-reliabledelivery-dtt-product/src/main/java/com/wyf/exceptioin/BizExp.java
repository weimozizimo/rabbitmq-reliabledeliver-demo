package com.wyf.exceptioin;

import lombok.Data;

/**
*@Description 异常类
*@Author weiyifei
*@date 2021/6/16
*/
@Data
public class BizExp extends RuntimeException {

    private Integer code;

    private String errMsg;

    public BizExp(Integer code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }
}
