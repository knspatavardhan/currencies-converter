package com.knspatavardhan.currenciesconverterwrapper.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice
public class ErrorResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String PARAMETERS = "parameters";
    private static final String MESSAGE = "message";

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
                                                                   WebRequest request) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(Map.of(
                STATUS, httpStatus.value(),
                ERROR, "Bad Request",
                PARAMETERS, Map.of("propertyName", Optional.ofNullable(ex.getPropertyName()).orElse(""),
                        "value", Optional.ofNullable(ex.getValue()).orElse("")),
                MESSAGE, "Invalid input: " + ex.getValue()
        ), httpStatus);
    }

    @ExceptionHandler(RuntimeWebException.class)
    public ResponseEntity<Object> handleWebRuntimeException(final RuntimeWebException runtimeWebException) {
        final HttpStatus httpStatus = Optional.ofNullable(runtimeWebException.getHttpStatus())
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(Map.of(
                STATUS, httpStatus.value(),
                MESSAGE, Optional.ofNullable(runtimeWebException.getMessage()).orElse(httpStatus.toString()),
                PARAMETERS, Optional.ofNullable(runtimeWebException.getParams()).orElse(Collections.emptyMap()),
                ERROR, Optional.ofNullable(runtimeWebException.getThrowable())
                        .map(Throwable::getMessage)
                        .orElse("Unknown error")
        ), httpStatus);
    }

}
