package com.devpuccino.demo.resttemplate.client.product.response

data class Status(
    val code: String,
    val message: String,
    val namespace: String
)

data class ProductServiceResponse<T>(
    val status: Status,
    val data: T? = null
)