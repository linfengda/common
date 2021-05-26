package com.lfd.soa.common.exception;

import com.lfd.soa.common.bean.Result;
import com.lfd.soa.common.constants.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述: controller统一异常处理
 *
 * @author linfengda
 * @create 2018-11-20 10:36
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Result RESULT_404 = new Result(404, "请求地址未找到.");

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultErrorHandler(Exception e) {
        Result result;
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            if (StringUtils.isEmpty(businessException.getDetailMsg())) {
                result = new Result(businessException.getCode(), businessException.getMsg());
            } else {
                result = new Result(businessException.getCode(), businessException.getMsg(), businessException.getDetailMsg());
            }
        }else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) e;
            result = new Result(ErrorCode.PARAM_ERROR_CODE, argumentNotValidException.getMessage());
        }else if (e instanceof HttpRequestMethodNotSupportedException) {
            log.warn("404找不到URL:未知请求与方法", e);
            return RESULT_404;
        }else {
            log.error("error info:", e);
            result = new Result(ErrorCode.UNKNOWN_ERROR_CODE, "系统故障，请稍后再试！");
        }
        return result;
    }
}
