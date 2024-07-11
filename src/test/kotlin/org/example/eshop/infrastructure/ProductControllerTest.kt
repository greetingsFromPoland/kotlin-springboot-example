package org.example.eshop.infrastructure

import io.kotest.core.spec.style.StringSpec
import io.mockk.mockk
import org.example.eshop.application.product.ProductService

class ProductControllerTest : StringSpec() {
    private val productService: ProductService = mockk()
    private val productController = ProductController(productService)

    init {

        "should return product by id" {
            // TODO("Not yet implemented")
        }

        "should return 404 when product not found" {
            // TODO("Not yet implemented")
        }
    }
}
