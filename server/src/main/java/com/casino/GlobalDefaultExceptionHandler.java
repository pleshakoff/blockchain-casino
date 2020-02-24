package com.casino;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@ControllerAdvice
public class GlobalDefaultExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    private static final Logger log = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
    private final MessageSource messageSource;
    public GlobalDefaultExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }



    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<Object> handleBindException(HttpServletRequest request, BindException ex)
    {
        return ResponseEntity.badRequest().body(ex.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleBindException(HttpServletRequest request, MethodArgumentNotValidException ex)
    {
        return ResponseEntity.badRequest().body(getMessageFromErrorList(ex.getBindingResult()));
    }


    private String getMessageFromErrorList(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        StringBuilder mess = new StringBuilder();
        for (ObjectError error : allErrors) {
            mess.append(messageSource.getMessage(error, LocaleContextHolder.getLocale())).append("; ");
        }
        return mess.toString();
    }


    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MissingServletRequestPartException.class,
            NoHandlerFoundException.class,
            AsyncRequestTimeoutException.class
    })
    @Nullable
    public final ResponseEntity<Object> handleExcluded(Exception ex, WebRequest request) throws Exception {
        throw ex;
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleException(HttpServletRequest request, Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }







}