package com.devpuccino.demo.resttemplate.product

import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductService {
    fun getAllProduct(): List<Outbound.Product> {
        return listOf()
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