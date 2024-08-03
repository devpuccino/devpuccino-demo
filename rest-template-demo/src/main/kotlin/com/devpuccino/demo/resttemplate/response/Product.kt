package com.devpuccino.demo.resttemplate.response

import java.math.BigDecimal

data class Product(
    val id: String? = null,
    val name: String,
    val price: BigDecimal,
)