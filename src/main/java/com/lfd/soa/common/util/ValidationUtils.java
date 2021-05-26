package com.lfd.soa.common.util;

import lombok.Data;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ：lsc
 * @version ：V1.0.0
 * @date ：2021/1/28 19:03
 * @desc ：校验工具类
 * 用来校验javax.validation.constraints校验框架下的注解
 **/
public class ValidationUtils {
  private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  public static <T> ValidationResult validateEntity(T obj) {
    ValidationResult result = new ValidationResult();
    Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
    // if( CollectionUtils.isNotEmpty(set) ){
    if (set != null && set.size() != 0) {
      result.setHasErrors(true);
      Map<String, String> errorMsg = new HashMap<String, String>();
      for (ConstraintViolation<T> cv : set) {
        errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
      }
      result.setErrorMsg(errorMsg);
    }
    return result;
  }

  public static <T> ValidationResult validateProperty(T obj, String propertyName) {
    ValidationResult result = new ValidationResult();
    Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
    if (set != null && set.size() != 0) {
      result.setHasErrors(true);
      Map<String, String> errorMsg = new HashMap<String, String>();
      for (ConstraintViolation<T> cv : set) {
        errorMsg.put(propertyName, cv.getMessage());
      }
      result.setErrorMsg(errorMsg);
    }
    return result;
  }

  /**
   * @author ：lsc
   * @date   ：2021/1/28 19:04
   * @desc   ：校验结果
   **/
  @Data
  public static class ValidationResult {
    // 校验结果是否有错
    private boolean  hasErrors;
    // 校验错误信息
    private Map<String, String> errorMsg;
  }
}
