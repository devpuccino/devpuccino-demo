package com.devpuccino.demo.resttemplate.product

import java.math.BigDecimal

class Outbound {
    data class Product(
        val id: String,
        val name: String,
        val price: BigDecimal
    )
}