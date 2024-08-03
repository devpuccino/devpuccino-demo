package com.devpuccino.demo.resttemplate.product

import com.devpuccino.demo.resttemplate.client.product.ProductServiceClient
import com.devpuccino.demo.resttemplate.exception.DataNotFoundException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductService(val productServiceClient: ProductServiceClient) {
    fun getAllProduct(): List<Outbound.Product> {
        return productServiceClient.getAllProduct()?.map {
            Outbound.Product(id=it.id, name = it.name, price = it.price)
        }?: throw DataNotFoundException()
    }

    fun getProductById(productId: String): Outbound.Product {
        return Outbound.Product("1","book", BigDecimal.valueOf(100.00))
    }

    fun addProduct(product: Outbound.Product): Outbound.Product {
        return Outbound.Product("1","book", BigDecimal.valueOf(100.00))
    }

    fun updateProduct(productId: String, product: Outbound.Product): Outbound.Product? {
        return Outbound.Product("1","book", BigDecimal.valueOf(100.00))
    }

    fun deleteProduct(productId: String) {

    }
}