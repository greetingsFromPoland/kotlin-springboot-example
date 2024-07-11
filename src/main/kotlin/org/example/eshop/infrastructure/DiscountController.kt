package org.example.eshop.infrastructure

import org.example.eshop.model.api.discount.CalculateDiscountRequest
import org.example.eshop.model.api.discount.CalculateDiscountResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/product/discount")
class DiscountController {
    @PostMapping("/calculate")
    fun calculateDiscount(request: CalculateDiscountRequest): CalculateDiscountResponse {
        TODO()
    }
}
