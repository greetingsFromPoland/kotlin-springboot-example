package org.example.eshop.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import org.example.eshop.application.discount.DiscountService
import org.example.eshop.application.product.ProductService
import org.example.eshop.model.api.ProductResponse
import org.example.eshop.model.api.discount.CalculateDiscountRequest
import org.example.eshop.model.api.discount.CalculateDiscountResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProductController::class)
@ContextConfiguration(classes = [ProductControllerTest.ControllerTestConfig::class, ProductController::class])
class ProductControllerTest : StringSpec() {
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun productService(): ProductService = mockk()

        @Bean
        fun discountService(): DiscountService = mockk()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var discountService: DiscountService

    init {
        "should return product by id" {
            val expectedProduct =
                ProductResponse(
                    id = "1",
                    name = "Product 1",
                    price = 100.0,
                )

            every { productService.getProduct("1") } returns expectedProduct

            mockMvc
                .perform(get("/api/v1/product/1"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(expectedProduct.id))
                .andExpect(jsonPath("$.name").value(expectedProduct.name))
                .andExpect(jsonPath("$.price").value(expectedProduct.price))
        }

        "should return 404 when product not found" {

            every { productService.getProduct("1") } returns null

            mockMvc
                .perform(get("/api/v1/product/1"))
                .andExpect(status().isNotFound)
        }

        "should calculate price with discount" {
            val uuid = "1"
            val request = CalculateDiscountRequest(quantity = 10)
            val expectedResponse =
                CalculateDiscountResponse(
                    totalPrice = 90.0,
                    uuid = uuid,
                    quantity = 10,
                )
            val objectMapper = ObjectMapper()

            every { discountService.calculateFinalPrice(uuid, request) } returns expectedResponse

            mockMvc
                .perform(
                    post("/api/v1/product/$uuid/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)),
                ).andExpect(status().isOk)
                .andExpect(jsonPath("$.totalPrice").value(expectedResponse.totalPrice))
                .andExpect(jsonPath("$.uuid").value(expectedResponse.uuid))
                .andExpect(jsonPath("$.quantity").value(expectedResponse.quantity))
        }

        "should return 404 when product not found for price calculation" {
            val uuid = "1"
            val request = CalculateDiscountRequest(quantity = 10)
            val objectMapper = ObjectMapper()

            every { discountService.calculateFinalPrice(uuid, request) } returns null

            mockMvc
                .perform(
                    post("/api/v1/product/$uuid/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)),
                ).andExpect(status().isNotFound)
        }

        "should catch exception when occurred during price calculation" {
            val uuid = "1"
            val request = CalculateDiscountRequest(quantity = 10)
            val objectMapper = ObjectMapper()

            every { discountService.calculateFinalPrice(uuid, request) } throws RuntimeException("Failed to calculate price")

            mockMvc
                .perform(
                    post("/api/v1/product/$uuid/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)),
                ).andExpect(status().isInternalServerError)
        }
    }
}
