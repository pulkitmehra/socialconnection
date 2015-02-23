package com.exercise.socialconnection.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.exercise.socialconnection.services.UnknownPersonException;
import com.exercise.socialconnection.web.model.ErrorResponse;

/**
 * Advice to convert all raised exceptions in to JSON/XML response.
 * See format class {@link ErrorResponse}
 * 
 * @author pulkit.mehra
 * @Since: Feb 23, 2015
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    /** The log. */
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * Handle unknown person exception.
     *
     * @param exception the exception
     * @param response the response
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(UnknownPersonException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleUnknownPersonException(
            final UnknownPersonException exception, final HttpServletResponse response,
            final HttpServletRequest request) {
        LOG.debug(exception.getMessage(), exception);
        ErrorResponse error = new ErrorResponse();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        return error;
    }

    /**
     * Handle all exception.
     *
     * @param exception the exception
     * @param response the response
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleException(final Throwable exception,
            final HttpServletResponse response, final HttpServletRequest request) {
        LOG.debug(exception.getMessage(), exception);
        ErrorResponse error = new ErrorResponse();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());
        error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return error;
    }
}
