package com.cleon.demo.api.exception;

import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Locale;

@ControllerAdvice
@Slf4j
public class RestApiErrorHandler {

    private static final String FORMAT_1 = "%s %s";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(HttpServletRequest request, Exception ex,
                                                 Locale locale) {
        log.warn(ex.getMessage());
        Error error = ErrorUtils
                .createError(ErrorCode.GENERIC_ERROR.getErrMsgKey(), ErrorCode.GENERIC_ERROR.getErrCode(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value()).setUrl(request.getRequestURL().toString())
                .setReqMethod(request.getMethod())
                .setTimestamp(Instant.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Error> handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
                                                                          HttpMediaTypeNotSupportedException ex,
                                                                          Locale locale) {
        log.error(ex.getMessage());
        Error error = ErrorUtils
                .createError(ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrMsgKey(),
                        ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrCode(),
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()).setUrl(request.getRequestURL().toString())
                .setReqMethod(request.getMethod())
                .setTimestamp(Instant.now());
        log.info("HttpMediaTypeNotSupportedException :: request.getMethod(): " + request.getMethod());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleHttpMessageNotReadableException(HttpServletRequest request,
                                                                       HttpMessageNotReadableException ex,
                                                                       Locale locale) {
        Error error = ErrorUtils
                .createError(ErrorCode.HTTP_MESSAGE_NOT_READABLE.getErrMsgKey(),
                        ErrorCode.HTTP_MESSAGE_NOT_READABLE.getErrCode(),
                        HttpStatus.NOT_ACCEPTABLE.value()).setUrl(request.getRequestURL().toString())
                .setReqMethod(request.getMethod())
                .setTimestamp(Instant.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Error> handleJsonParseException(HttpServletRequest request,
                                                          JsonParseException ex,
                                                          Locale locale) {
        Error error = ErrorUtils
                .createError(ErrorCode.JSON_PARSE_ERROR.getErrMsgKey(),
                        ErrorCode.JSON_PARSE_ERROR.getErrCode(),
                        HttpStatus.NOT_ACCEPTABLE.value()).setUrl(request.getRequestURL().toString())
                .setReqMethod(request.getMethod())
                .setTimestamp(Instant.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Error> handleHttpRequestMethodNotSupportedException(
            HttpServletRequest request,
            HttpRequestMethodNotSupportedException ex,
            Locale locale) {
        Error error = ErrorUtils
                .createError(ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrMsgKey(),
                        ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrCode(),
                        HttpStatus.NOT_IMPLEMENTED.value()).setUrl(request.getRequestURL().toString())
                .setReqMethod(request.getMethod())
                .setTimestamp(Instant.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Error> handleIllegalArgumentException(
            HttpServletRequest request,
            IllegalArgumentException ex,
            Locale locale) {
        Error error = ErrorUtils
                .createError(String
                                .format(FORMAT_1, ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getErrMsgKey(), ex.getMessage()),
                        ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getErrCode(),
                        HttpStatus.BAD_REQUEST.value()).setUrl(request.getRequestURL().toString())
                .setReqMethod(request.getMethod())
                .setTimestamp(Instant.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
