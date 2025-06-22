package com.ngbilling.config;

import com.ngbilling.enums.EnumError;
import com.ngbilling.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServiceException> handleServiceException(ServiceException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex);
    }

    //testar este metodo
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ServiceException> handleAllExceptions(Exception ex) {
        ServiceException serviceException = new ServiceException(EnumError.ERRO_GENERICO);
        return ResponseEntity.status(serviceException.getHttpStatus()).body(serviceException);
    }
}
