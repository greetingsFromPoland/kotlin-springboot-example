package org.example.eshop.application.discount

import org.example.eshop.model.entity.Product
import org.example.eshop.properties.DiscountProperties
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PercentageBasedDiscountStrategy(
    private val discountProperties: DiscountProperties,
) : DiscountStrategy {
    override fun calculateDiscount(
        product: Product,
        count: Int,
    ): BigDecimal {
        TODO("Not yet implemented")
    }
}