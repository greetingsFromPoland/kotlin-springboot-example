package org.example.eshop.infrastructure

import mu.KotlinLogging
import org.example.eshop.application.product.ProductService
import org.example.eshop.model.api.ProductResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/product")
class ProductController(
    private val productService: ProductService,
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/{id}")
    fun getProductById(
        @PathVariable id: String,
    ): ResponseEntity<ProductResponse> =
        try {
            productService.getProduct(id)?.let {
                ResponseEntity.ok(it)
            } ?: ResponseEntity.notFound().build()
        } catch (e: Exception) {
            logger.error(e) { "Failed to get product by ID: $id" }
            ResponseEntity.internalServerError().build()
        }
}
