package com.hw.springbootrestful.web.exception;

import com.hw.springbootrestful.dto.code.BaseRestCode;
import com.hw.springbootrestful.dto.response.BaseRestResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**
 * @author hw 全局异常捕获处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseRestResponse constraintViolationException(ConstraintViolationException ex) {
        logger.error(ex.getMessage());
        return new BaseRestResponse(BaseRestCode.REST_SERVER_ERROR, "Bad Request");
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseRestResponse noHandlerFoundException(Exception ex) {
        logger.error(ex.getMessage());
        return new BaseRestResponse(BaseRestCode.REST_BAD_REQUEST_NOT_FOUND, "Not Found");
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseRestResponse unknownException(Exception ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
        return new BaseRestResponse(BaseRestCode.REST_SERVER_ERROR, "Internal Server Error");
    }

}
