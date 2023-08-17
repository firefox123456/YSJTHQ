package huangqi.web.exception;

import huangqi.base.result.ResultCode;
import huangqi.base.result.ReturnDataFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 异常处理类
 *
 * @author "黄骐"
 * @date 2023/08/17 23:06
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public  ReturnDataFormat handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("非法异常，异常原因：{}", e.getMessage(), e);
        e.printStackTrace();
        return ReturnDataFormat.error().code(ResultCode.IllegalArgument.getCode()).message(e.getMessage());
    }
}
