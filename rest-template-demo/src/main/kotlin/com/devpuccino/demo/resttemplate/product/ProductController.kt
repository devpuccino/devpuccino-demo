package com.devpuccino.demo.resttemplate.product

import com.devpuccino.demo.resttemplate.common.outbound.CommonResponse
import com.devpuccino.demo.resttemplate.common.outbound.Status
import com.devpuccino.demo.resttemplate.constant.ResponseStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/product")
class ProductController(private val productService: ProductService) {

    @GetMapping
    @ResponseBody
    fun getAllProduct(): CommonResponse<List<Outbound.Product>> {
        return CommonResponse(Status.success(), productService.getAllProduct())
    }

    @GetMapping("/{product-id}")
    @ResponseBody
    fun getProduct(@PathVariable("product-id") productId:String):CommonResponse<Outbound.Product>{
        return CommonResponse(status = Status.success(),productService.getProductById(productId))
    }

    @PostMapping
    @ResponseBody
    fun addProduct(@RequestBody product:Outbound.Product):CommonResponse<Outbound.Product>{
        return CommonResponse(status = Status.success(),productService.addProduct(product))
    }

    @PutMapping("/{product-id}")
    @ResponseBody
    fun updateProduct(@PathVariable("product-id") productId:String,@RequestBody product:Outbound.Product):CommonResponse<Outbound.Product>{
        return CommonResponse(status = Status.success(),productService.updateProduct(productId,product))
    }

    @DeleteMapping("/{product-id}")
    @ResponseBody
    fun deleteProduct(@PathVariable("product-id") productId:String):CommonResponse<*>{
        return CommonResponse(status = Status.success(),productService.deleteProduct(productId))
    }


}