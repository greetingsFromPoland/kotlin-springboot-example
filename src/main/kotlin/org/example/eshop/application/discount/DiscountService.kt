package org.example.eshop.application.discount

import mu.KotlinLogging
import org.example.eshop.application.product.ProductService
import org.example.eshop.model.api.discount.CalculateDiscountRequest
import org.example.eshop.model.api.discount.CalculateDiscountResponse
import org.springframework.stereotype.Service

@Service
class DiscountService(
    private val productService: ProductService,
    private val discountStrategies: List<DiscountStrategy>,
) {
    private val logger = KotlinLogging.logger {}

    fun calculateDiscount(request: CalculateDiscountRequest): CalculateDiscountResponse {
        TODO("Not yet implemented")
    }
}
