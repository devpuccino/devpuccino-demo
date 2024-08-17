package com.devpuccino.demo.resttemplate.exception

import com.devpuccino.demo.resttemplate.dto.response.CommonResponse
import com.devpuccino.demo.resttemplate.dto.response.Status
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {
    companion object{
        private  val logger:Logger = LogManager.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception:Exception){
        logger.error("handlerException",exception)
    }

    @ExceptionHandler(DataNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleDataNotFoundException(exception:DataNotFoundException):CommonResponse<Any?>{
        logger.error(exception.message)
        return CommonResponse(status= Status(com.devpuccino.demo.resttemplate.constant.ResponseStatus.DATA_NOT_FOUND))
    }
}