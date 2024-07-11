package org.example.eshop.application.discount

import org.example.eshop.model.entity.Product
import java.math.BigDecimal

interface DiscountStrategy {
    fun calculateDiscount(
        product: Product,
        count: Int,
    ): BigDecimal
}
