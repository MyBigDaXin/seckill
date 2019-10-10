package com.justfor.seckill.validator;


import cn.hutool.core.lang.Validator;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author lth
 * @date 2019年10月10日 14:48
 */


public class IsMobileValidator implements ConstraintValidator<IsMobile,String>{

    private boolean required = false;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return Validator.isMobile(value);
        }else{
            if(StringUtils.isEmpty(value)){
                return true;
            }else{
                return Validator.isMobile(value);
            }
        }
    }

}
