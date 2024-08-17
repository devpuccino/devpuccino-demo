package com.devpuccino.demo.resttemplate.client.product

import com.devpuccino.demo.resttemplate.exception.DataNotFoundException
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler

class ProductServiceErrorHandler: ResponseErrorHandler {
    private companion object{
        val logger: Logger = LogManager.getLogger(ProductServiceErrorHandler::class.java)
    }
    override fun hasError(response: ClientHttpResponse): Boolean {
        return response.statusCode != HttpStatus.OK
    }

    override fun handleError(response: ClientHttpResponse) {
        when(response.statusCode){
            HttpStatus.SERVICE_UNAVAILABLE -> throw Exception("product-service unavailable")
            HttpStatus.REQUEST_TIMEOUT -> throw Exception("product-service timeout")
            HttpStatus.NOT_FOUND -> throw DataNotFoundException()
            else -> throw Exception("Unexpected error")
        }
    }

}