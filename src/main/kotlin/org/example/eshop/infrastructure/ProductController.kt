package org.example.eshop.infrastructure

import org.example.eshop.application.product.ProductService
import org.example.eshop.model.api.ProductResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/product")
class ProductController(
    private val productService: ProductService,
) {
    @GetMapping("{id}")
    fun getProductById(
        @PathVariable id: String,
    ): ProductResponse {
        TODO("Not yet implemented")
    }
}
