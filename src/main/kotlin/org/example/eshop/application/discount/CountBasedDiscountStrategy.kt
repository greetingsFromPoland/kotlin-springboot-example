package org.example.eshop.application.discount

import org.example.eshop.model.entity.Product
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CountBasedDiscountStrategy : DiscountStrategy {
    override fun calculateDiscount(
        product: Product,
        count: Int,
    ): BigDecimal {
        TODO("Not yet implemented")
    }
}
