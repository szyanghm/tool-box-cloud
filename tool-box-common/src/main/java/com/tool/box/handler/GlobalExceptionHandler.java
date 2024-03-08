package com.tool.box.handler;

import com.tool.box.exception.InternalApiException;
import com.tool.box.utils.SystemUtils;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 全局异常处理
 *
 * @Author v_haimiyang
 * @ControllerAdvice 捕获 Controller 层抛出的异常，如果添加 @ResponseBody 返回信息则为JSON 格式。
 * @RestControllerAdvice 相当于 @ControllerAdvice 与 @ResponseBody 的结合体。
 * @ExceptionHandler 统一处理一种类的异常，减少代码重复率，降低复杂度。
 * @Date 2023/6/28 15:22
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * web远程调用service服务内部接口异常捕获 此方法返回ErrorResponseEntity 便于NotBreakerConfiguration中47行,好Json解析成InternalApiException对象
     *
     * @param e        exception
     * @param response response
     * @return 响应结果
     */
    @ExceptionHandler(InternalApiException.class)
    public ResultVO<?> internalApiExceptionHandler(final Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        InternalApiException exception = (InternalApiException) e;
        log.error("具体错误信息:{}", SystemUtils.getErrorMessage(exception));
        return new ResultVO(exception.getCode(), exception.getMessage());
    }

    /**
     * 捕获  RuntimeException 异常
     *
     * @param request  request
     * @param e        exception
     * @param response response
     * @return 响应结果
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultVO<?> runtimeExceptionHandler(HttpServletRequest request,
                                               final Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        RuntimeException exception = (RuntimeException) e;
        log.error("具体错误信息:{}", SystemUtils.getErrorMessage(exception)); //会记录出错的代码行等具体信息
        return new ResultVO(response.getStatus(), exception.getMessage());
    }

    /**
     * 通用的接口映射异常处理方
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            return new ResponseEntity<>(new ResultVO(status.value(),
                    exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()),
                    status);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            log.error("参数转换失败，方法：{}，参数：{},信息：{}",
                    Objects.requireNonNull(exception.getParameter().getMethod()).getName(),
                    exception.getName(), exception.getLocalizedMessage());
            return new ResponseEntity<>(new ResultVO(status.value(), "参数转换失败"), status);
        }
        return new ResponseEntity<>(new ResultVO(status.value(), "参数转换失败"), status);
    }
}
