package com.devpuccino.demo.resttemplate.config

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder
import org.apache.hc.client5.http.ssl.TrustAllStrategy
import org.apache.hc.core5.ssl.SSLContextBuilder
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfiguration {

    @Bean
    fun snakeCaseMessageConverter() = jacksonObjectMapper().apply {
        this.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
    }

    @Bean("product-service-rest-template")
    fun productServiceRestTemplate(
        snakeCaseMessageConverter: MappingJackson2HttpMessageConverter,
        restTemplateBuilder: RestTemplateBuilder
    ): RestTemplate {

        return restTemplateBuilder
            .rootUri("https://192.168.7.100:18443/product-service")
            .messageConverters(snakeCaseMessageConverter)
            .requestFactory(createRequestFactory())
            .build()
    }

    fun createRequestFactory(): () -> HttpComponentsClientHttpRequestFactory = {
        val sslContext = SSLContextBuilder.create().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build()
        val sslFactory = SSLConnectionSocketFactoryBuilder.create()
            .setSslContext(sslContext)
            .setHostnameVerifier { _, _ -> true }
            .build()
        val connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
            .setSSLSocketFactory(sslFactory)
            .build()
        val httpClient = HttpClients.custom().setConnectionManager(connectionManager).build()
        HttpComponentsClientHttpRequestFactory(httpClient)
    }
}