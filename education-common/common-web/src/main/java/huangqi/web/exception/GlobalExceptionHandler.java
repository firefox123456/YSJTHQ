package huangqi.web.exception;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import huangqi.base.result.ResultCode;
import huangqi.base.result.ReturnDataFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.modeler.OperationInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理类
 *
 * @author "黄骐"
 * @date 2023/08/17 23:06
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public  ReturnDataFormat handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数异常，异常原因：{}", e.getMessage(), e);
        e.printStackTrace();
        return ReturnDataFormat.error().code(ResultCode.IllegalArgument.getCode()).message(e.getMessage());
    }

    /**
     * 流控处理
     */
    @ExceptionHandler(FlowException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody // 该注解用于把对象转为json
    public ReturnDataFormat handlerFlowException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        log.info("{}, 流控, {}", request.getRequestURI(), ex.toString());
        return ReturnDataFormat.error().message("流控");
    }


    /**
     * 熔断降级处理
     */
    @ExceptionHandler(DegradeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReturnDataFormat handlerDegradeException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        log.info("{}, 熔断降级, {}", request.getRequestURI(),ex.toString());
        return ReturnDataFormat.error().message("熔断降级");
    }

}
