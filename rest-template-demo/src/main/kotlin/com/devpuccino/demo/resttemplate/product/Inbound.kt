package com.devpuccino.demo.resttemplate.product

import java.math.BigDecimal

class Inbound {
    data class Status(
        val code: String,
        val message: String,
        val namespace: String
    )

    data class ProductServiceResponse<T>(
        val status: Status,
        val data: T? = null
    )

    data class Product(
        val id: String,
        val name: String,
        val price: BigDecimal,
        val amount: Long
    )
}
