package com.devpuccino.demo.resttemplate.config

import com.devpuccino.demo.resttemplate.client.product.ProductServiceErrorHandler
import com.devpuccino.demo.resttemplate.interceptor.RestTemplateLoggingInterceptor
import com.devpuccino.demo.resttemplate.properties.ProductServiceProperties
import org.apache.hc.client5.http.classic.HttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder
import org.apache.hc.client5.http.io.HttpClientConnectionManager
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder
import org.apache.hc.client5.http.ssl.TrustAllStrategy
import org.apache.hc.core5.ssl.SSLContextBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfiguration {


    @Bean
    fun productServiceRestTemplate(restTemplateBuilder: RestTemplateBuilder, productServiceProperties: ProductServiceProperties): RestTemplate {
        return restTemplateBuilder
            .requestFactory(createRequestFactory())
            .errorHandler(ProductServiceErrorHandler())
            .interceptors(RestTemplateLoggingInterceptor())
            .rootUri(productServiceProperties.baseUrl)
            .build()
    }


    fun createRequestFactory(): ()->BufferingClientHttpRequestFactory = {
        val sslContext = SSLContextBuilder.create().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build()

        val sslFactory = SSLConnectionSocketFactoryBuilder.create()
            .setSslContext(sslContext)
            .setHostnameVerifier{ _,_ -> true}
            .build()

        val connectionManager:HttpClientConnectionManager = PoolingHttpClientConnectionManagerBuilder.create()
            .setSSLSocketFactory(sslFactory)
            .build()

        val httpClient:HttpClient = HttpClients.custom().setConnectionManager(connectionManager).build()

        BufferingClientHttpRequestFactory(HttpComponentsClientHttpRequestFactory(httpClient))
    }


}
