package com.devpuccino.demo.resttemplate.client

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ProductServiceClient(val restTemplate: RestTemplate) {

    val url = "https://localhost:8443/product-service/api/product"
    fun getAllProduct(): List<Product>? {
        try {
            val responseEntity: ResponseEntity<ProductServiceResponse> =
                restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, ProductServiceResponse::class.java)
            return responseEntity.body?.data
        } catch (ex: Exception) {
            return listOf()
        }
    }

    fun addProduct(product: com.devpuccino.demo.resttemplate.response.Product):Boolean {

    val httpEntity = HttpEntity<com.devpuccino.demo.resttemplate.response.Product>(product)
    val responseEntity:ResponseEntity<ProductServiceResponse>  = restTemplate.exchange(url,HttpMethod.POST,httpEntity,ProductServiceResponse::class.java)

    return responseEntity.statusCode == HttpStatus.OK

    }
}