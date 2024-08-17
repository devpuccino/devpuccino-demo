package com.devpuccino.demo.resttemplate.client.product.model

import java.math.BigDecimal

data class ProductServiceResponse(
    val status: Status,
    val data:List<Product>? = null
)
data class Status(
    val code:String,
    val message:String,
    val namespace:String
)
data class Product(
    val id:String,
    val name:String,
    val price:BigDecimal,
    val amount:Int
)