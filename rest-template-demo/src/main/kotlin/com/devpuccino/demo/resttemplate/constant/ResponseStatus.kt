package com.devpuccino.demo.resttemplate.constant

enum class ResponseStatus(val code:String,val message:String) {
    SUCCESS("200-000","Success"),
    DATA_NOT_FOUND("404-001","Data not found")
}