package com.devpuccino.demo.resttemplate.config

import org.apache.hc.client5.http.classic.HttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder
import org.apache.hc.client5.http.io.HttpClientConnectionManager
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder
import org.apache.hc.client5.http.ssl.TrustAllStrategy
import org.apache.hc.core5.ssl.SSLContextBuilder
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfiguration {

    @Bean
    fun productServiceRestTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate {
        return restTemplateBuilder
            .requestFactory(createRequestFactory())
            .build()
    }

    fun createRequestFactory(): ()->HttpComponentsClientHttpRequestFactory = {
        val sslContext = SSLContextBuilder.create().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build()

        val sslFactory = SSLConnectionSocketFactoryBuilder.create()
            .setSslContext(sslContext)
            .setHostnameVerifier{ _,_ -> true}
            .build()

        val connectionManager:HttpClientConnectionManager = PoolingHttpClientConnectionManagerBuilder.create()
            .setSSLSocketFactory(sslFactory)
            .build()

        val httpClient:HttpClient = HttpClients.custom().setConnectionManager(connectionManager).build()

        HttpComponentsClientHttpRequestFactory(httpClient)
    }


}
