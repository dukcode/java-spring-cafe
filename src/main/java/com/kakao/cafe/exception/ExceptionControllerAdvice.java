package com.kakao.cafe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(ClientException.class)
    public ModelAndView handleException(ModelAndView modelAndView, ClientException exception) {

        HttpStatus httpStatus = exception.getHttpStatus();
        String message = exception.getMessage();
        logger.info("ClientException handling [STATUS] : {}, [MESSAGE] : {}", httpStatus.toString(), message);

        modelAndView.setViewName("/error-page/400");
        modelAndView.setStatus(httpStatus);
        modelAndView.addObject("status", httpStatus.toString());
        modelAndView.addObject("message", message);

        return modelAndView;
    }

}
