package com.devpuccino.demo.resttemplate.service

import com.devpuccino.demo.resttemplate.client.ProductServiceClient
import com.devpuccino.demo.resttemplate.response.Product
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductService(val productServiceClient: ProductServiceClient) {

    fun getAllProduct(): List<Product> {
        val result: List<Product> = productServiceClient.getAllProduct()?.map { product ->
            Product(id = product.id, name = product.name, price = product.price)
        } ?: listOf()
        return result
    }

    fun getProductById(productId: String): Product {
        return Product("1", "book", BigDecimal.valueOf(100.00))
    }

    fun addProduct(product: Product): Product {
        val result = productServiceClient.addProduct(product)
        if (result) {
            return product
        } else {
            throw Exception("Can not add product")
        }
    }

    fun updateProduct(productId: String, product: Product): Product? {
        return Product("1", "book", BigDecimal.valueOf(100.00))
    }

    fun deleteProduct(productId: String) {

    }
}