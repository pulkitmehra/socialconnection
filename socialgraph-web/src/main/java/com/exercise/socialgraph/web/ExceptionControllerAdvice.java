package com.exercise.socialgraph.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.exercise.socialgraph.core.services.UnknownPersonException;
import com.exercise.socialgraph.web.model.ErrorResponse;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UnknownPersonException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleUnknownPersonException(
            final UnknownPersonException exception, final HttpServletResponse response,
            final HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        return error;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleException(final Throwable exception,
            final HttpServletResponse response, final HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());
        error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return error;
    }
}
