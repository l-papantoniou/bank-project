package com.bank.bankproject.handler;

import com.bank.bankproject.exception.ErrorMessage;
import com.bank.bankproject.exception.NotFoundException;
import com.bank.bankproject.exception.NotValidDataException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.Locale;

@Log4j2
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException exception, WebRequest request, Locale locale) {
        return getErrorResponseEntity(request, locale, "exception.notFoundException", HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
    }

    @ExceptionHandler(value = {NotValidDataException.class})
    public ResponseEntity<ErrorMessage> handleNotValidDataException(NotFoundException exception, WebRequest request, Locale locale) {
        return getErrorResponseEntity(request, locale, "exception.notValidDataException", HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
    }

    private ResponseEntity<ErrorMessage> getErrorResponseEntity(WebRequest request, Locale locale, String errorMessage, HttpStatus httpStatus, String exceptionMessage) {
        Date date = new Date();
        String description = messageSource.getMessage(errorMessage, null, locale);

        String path = request.getDescription(false);
        ErrorMessage message = new ErrorMessage(httpStatus.value(),
                httpStatus.getReasonPhrase(),
                date, description, path, exceptionMessage);
        return new ResponseEntity<>(message, httpStatus);
    }

}
