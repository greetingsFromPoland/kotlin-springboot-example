package org.example.eshop.model.api.discount

data class CalculateDiscountRequest(
    val productId: String,
    val quantity: Int,
)
