package com.devpuccino.demo.resttemplate.client

import com.devpuccino.demo.resttemplate.client.product.ProductServiceClient
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
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

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ProductServiceClientTest {
    @Autowired
    private lateinit var productServiceClient: ProductServiceClient

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private lateinit var mockServer:MockRestServiceServer

    @BeforeEach
    fun setup(){
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    @Test
    fun `should return product list`(){
        val responseJson = """
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
                        "price": 10.0,
                        "amount": 100
                    }
                ]
            }
        """.trimIndent()

        mockServer.expect(ExpectedCount.once(), requestTo("https://localhost:8443/product-service/api/product"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(responseJson))

        val actual = productServiceClient.getAllProduct()
        Assertions.assertEquals(1,actual?.size)
        Assertions.assertEquals("pen",actual?.get(0)?.name)
    }

    @Test
    fun `should throw exception when service response timeout`(){
        mockServer.expect(ExpectedCount.once(), requestTo("https://localhost:8443/product-service/api/product"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.REQUEST_TIMEOUT))
        val actual = productServiceClient.getAllProduct()
        Assertions.assertEquals(0, actual?.size)
    }

    @Test
    fun `should throw exception when service response unavailable`(){
        mockServer.expect(ExpectedCount.once(), requestTo("https://localhost:8443/product-service/api/product"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE))
        val actual = productServiceClient.getAllProduct()
        Assertions.assertEquals(0, actual?.size)

    }

}