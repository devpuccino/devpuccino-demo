package com.devpuccino.demo.resttemplate.client.product

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.web.client.RestTemplate
import kotlin.test.Test


@ExtendWith(SpringExtension::class)
@SpringBootTest
class ProductServiceClientTest {
    @Autowired
    private lateinit var productServiceClient: ProductServiceClient

    @Autowired
    @Qualifier("product-service-rest-template")
    private lateinit var restTemplate: RestTemplate
    private lateinit var mockServer: MockRestServiceServer


    @BeforeEach
    fun init(){
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    @Nested
    inner class GetAllProduct{
        @Test
        fun `should response `(){
            val givenResponse = """
                {
                  "status": {
                    "code": "200-000",
                    "message": "Success",
                    "namespace": "PRODUCT-SERVICE"
                  },
                  "data": [
                    {
                      "id": "1",
                      "name": "pen",
                      "price": 10,
                      "amount": 100
                    },
                    {
                      "id": "2",
                      "name": "notebook",
                      "price": 99,
                      "amount": 10
                    }
                  ]
                }
            """.trimIndent()
            mockServer
                .expect(ExpectedCount.once(), requestTo("https://192.168.7.100:18443/product-service/api/product"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(givenResponse))
            val actual = productServiceClient.getAllProduct()
            mockServer?.verify()
            println(actual)
        }
    }
}