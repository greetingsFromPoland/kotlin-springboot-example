package org.example.eshop.infrastructure

import mu.KotlinLogging
import org.example.eshop.application.discount.DiscountService
import org.example.eshop.application.product.ProductService
import org.example.eshop.model.api.ProductResponse
import org.example.eshop.model.api.discount.CalculateDiscountRequest
import org.example.eshop.model.api.discount.CalculateDiscountResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(
    private val productService: ProductService,
    private val discountService: DiscountService,
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/{uuid}")
    fun getProductById(
        @PathVariable uuid: String,
    ): ResponseEntity<ProductResponse> =
        try {
            productService.getProduct(uuid)?.let {
                ResponseEntity.ok(it)
            } ?: ResponseEntity.notFound().build()
        } catch (e: Exception) {
            logger.error(e) { "Failed to get product by ID: $uuid" }
            ResponseEntity.internalServerError().build()
        }

    @PostMapping("/{uuid}/calculate-price")
    fun calculatePrice(
        @PathVariable uuid: String,
        @RequestBody request: CalculateDiscountRequest,
    ): ResponseEntity<CalculateDiscountResponse> =
        try {
            discountService.calculateFinalPrice(uuid, request)?.let {
                ResponseEntity.ok(it)
            } ?: ResponseEntity.notFound().build()
        } catch (e: IllegalArgumentException) {
            logger.error(e) { "Failed to calculate price for product ID: $uuid caused by request: $request" }
            ResponseEntity.badRequest().build()
        } catch (e: Exception) {
            logger.error(e) { "Failed to calculate price for product ID: $uuid caused by request: $request" }
            ResponseEntity.internalServerError().build()
        }
}
