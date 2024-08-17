package com.devpuccino.demo.resttemplate.client.product

import com.devpuccino.demo.resttemplate.client.product.model.Product
import com.devpuccino.demo.resttemplate.client.product.model.ProductServiceResponse
import com.devpuccino.demo.resttemplate.properties.ProductServiceProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ProductServiceClient(val restTemplate: RestTemplate) {
    @Autowired
    lateinit var productServiceProperties: ProductServiceProperties

    fun getAllProduct(): List<Product>? {
        val url = productServiceProperties.endpoints.getAllProduct
        return try {
            val responseEntity = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, ProductServiceResponse::class.java)
            responseEntity.body?.data
        } catch (ex: Exception) {
            listOf()
        }
    }

    fun addProduct(product: com.devpuccino.demo.resttemplate.dto.response.Product): Boolean {
        val url = productServiceProperties.endpoints.insertProduct
        val httpEntity = HttpEntity<com.devpuccino.demo.resttemplate.dto.response.Product>(product)
        val responseEntity: ResponseEntity<ProductServiceResponse> = restTemplate.exchange(url, HttpMethod.POST, httpEntity, ProductServiceResponse::class.java)
        return responseEntity.statusCode == HttpStatus.OK
    }

}