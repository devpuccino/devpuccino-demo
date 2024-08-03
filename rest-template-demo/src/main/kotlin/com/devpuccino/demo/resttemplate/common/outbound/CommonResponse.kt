package com.devpuccino.demo.resttemplate.common.outbound

import com.devpuccino.demo.resttemplate.constant.Namespace.DEMO_SERVICE
import com.devpuccino.demo.resttemplate.constant.ResponseStatus


data class CommonResponse<T>(
    val status: Status,
    val data: T? = null
)
data class Status(
    val code: String,
    val message: String,
    val namespace: String = DEMO_SERVICE
){
    constructor(responseStatus: ResponseStatus):this(responseStatus.code,responseStatus.message)
    companion object{
        fun success() = Status(ResponseStatus.SUCCESS)
    }
}