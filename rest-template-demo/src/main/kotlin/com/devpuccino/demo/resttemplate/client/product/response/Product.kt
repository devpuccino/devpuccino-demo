package com.devpuccino.demo.resttemplate.client.product.response

import java.math.BigDecimal

data class Product(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val amount: Long
)