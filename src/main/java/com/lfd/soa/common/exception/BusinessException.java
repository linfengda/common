package com.lfd.soa.common.exception;

import lombok.Getter;

/**
 * 描述: 业务异常类
 *
 * @author linfengda
 * @date 2019-01-23 13:22
 */
@Getter
public class BusinessException extends RuntimeException {

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(Integer code, String msg, String detailMsg) {
        super(null == detailMsg ? msg : msg + "," + detailMsg);
        this.code = code;
        this.msg = msg;
        this.detailMsg = detailMsg;
    }

    private int code = 1000;

    private String msg;

    private String detailMsg;
}
