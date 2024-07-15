package org.example.eshop.model.api.discount

data class CalculateDiscountResponse(
    val totalPrice: Double,
    val uuid: String,
    val quantity: Int,
)
