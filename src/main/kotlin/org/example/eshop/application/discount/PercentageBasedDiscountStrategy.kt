package org.example.eshop.application.discount

import mu.KotlinLogging
import org.example.eshop.properties.DiscountProperties
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PercentageBasedDiscountStrategy(
    private val discountProperties: DiscountProperties,
) : DiscountStrategy {
    private val logger = KotlinLogging.logger {}

    override fun calculateDiscount(
        intermediatePrice: BigDecimal,
        quantity: Int,
    ): BigDecimal {
        logger.trace { "Calculating discount for product quantity: $quantity, intermediatePrice: $intermediatePrice" }

        require(quantity >= 0) { "Quantity must be non-negative" }

        if (quantity == 0) return BigDecimal.ZERO

        val discountRate = discountProperties.percentageBased?.toBigDecimal() ?: return BigDecimal.ZERO
        return intermediatePrice.multiply(discountRate).also {
            logger.debug { "Applying discount $discountRate for product quantity: $quantity, amount: $it" }
        }
    }

    override fun isApplicable(productQuantity: Int?): Boolean = true
}
