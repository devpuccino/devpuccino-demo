package com.devpuccino.demo.resttemplate.interceptor

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.util.StreamUtils
import java.io.InputStream

class RestTemplateLoggingInterceptor:ClientHttpRequestInterceptor {
    private companion object {
        val logger: Logger = LogManager.getLogger(RestTemplateLoggingInterceptor::class.java)
    }
    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        val startTime = System.currentTimeMillis()
        logRequest(request.method.name(),request.uri.toString(),body)

        val response: ClientHttpResponse = execution.execute(request, body)
        logResponse(response.statusCode.value(),response.body,startTime)

        return response
    }
    private fun logRequest(httpMethod:String,url:String,body:ByteArray){
        val logRequest = StringBuilder("CLIENT REQUEST")
        logRequest.append(" [$httpMethod]")
        logRequest.append(" url=$url")
        if(body != null && body.isNotEmpty()) {
            logRequest.append(" body=[${String(body)}]")
        }
        logger.info(logRequest)
    }
    private fun logResponse(httpStatus:Int,responseBody:InputStream,startTime:Long){
        val responseBody = StreamUtils.copyToString(responseBody,Charsets.UTF_8)
        logger.info("CLIENT RESPONSE [{} ms] status=[{}] body=[{}]",System.currentTimeMillis() - startTime, httpStatus,responseBody)
    }
}