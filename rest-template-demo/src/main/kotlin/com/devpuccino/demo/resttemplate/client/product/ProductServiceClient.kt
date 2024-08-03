package com.devpuccino.demo.resttemplate.client.product

import com.devpuccino.demo.resttemplate.client.product.response.Product
import com.devpuccino.demo.resttemplate.client.product.response.ProductServiceResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ProductServiceClient(@Qualifier("product-service-rest-template") val restTemplate: RestTemplate) {
    fun getAllProduct(): List<Product>? {

            val response: ResponseEntity<ProductServiceResponse<List<Product>>> = restTemplate.exchange(
                "/api/product",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                object : ParameterizedTypeReference<ProductServiceResponse<List<Product>>>() {})
            return if (response.statusCode == HttpStatus.OK && response.body != null) {
                response.body?.data
            } else {
                null
            }
    }
}