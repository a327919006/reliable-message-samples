package com.cn.rmq.sample.confrig;

import com.cn.rmq.sample.model.Constants;
import com.cn.rmq.sample.model.dto.BaseRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>Title: ControllerExceptionHandler</p>
 * <p>Description: 控制器异常处理</p>
 *
 * @author Chen Nan
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    /**
     * 控制器异常处理入口
     *
     * @param e 异常信息
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object resolveException(Exception e, HttpServletRequest request) {
        log.error(e.getClass().getName());
        BaseRsp retBean = new BaseRsp();

        if (e instanceof BindException) {
            BindException exs = (BindException) e;
            log.error("【参数校验异常】:" + e);

            List<FieldError> errors = exs.getFieldErrors();

            if (errors.size() > 0) {
                FieldError error = errors.get(0);
                retBean.code(Constants.CODE_FAILURE).msg(error.getField() + error.getDefaultMessage());
            }
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exs = (MethodArgumentNotValidException) e;
            log.error("【参数校验异常】:" + e);

            List<FieldError> errors = exs.getBindingResult().getFieldErrors();

            if (errors.size() > 0) {
                FieldError error = errors.get(0);
                retBean.code(Constants.CODE_FAILURE).msg(error.getField() + error.getDefaultMessage());
            }
        } else {
            log.error(e.getMessage(), e);
            retBean.code(Constants.CODE_FAILURE).msg("系统异常");
        }

        return retBean;
    }
}
