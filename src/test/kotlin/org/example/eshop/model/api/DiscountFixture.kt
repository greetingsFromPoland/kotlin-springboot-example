package org.example.eshop.model.api

import org.example.eshop.model.api.discount.CalculateDiscountRequest
import org.example.eshop.model.api.discount.CalculateDiscountResponse
import java.math.BigDecimal

class DiscountFixture {
    companion object {
        fun createCalculateDiscountRequest(
            productId: String,
            quantity: Int = 1,
        ) = CalculateDiscountRequest(
            productId = productId,
            quantity = quantity,
        )

        fun createCalculateDiscountResponse(discount: BigDecimal = BigDecimal.ZERO) =
            CalculateDiscountResponse(
                discount = discount,
            )
    }
}
