package com.devpuccino.demo.resttemplate.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.product-service")
class ProductServiceProperties {
    lateinit var baseUrl: String
    lateinit var endpoints: ProductServiceEndpoint
}

class ProductServiceEndpoint {
    lateinit var getAllProduct: String
    lateinit var insertProduct: String
    lateinit var getProductById: String
}