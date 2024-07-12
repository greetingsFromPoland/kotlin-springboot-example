package org.example.eshop.application.discount

import mu.KotlinLogging
import org.example.eshop.model.entity.Product
import org.example.eshop.properties.DiscountProperties
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PercentageBasedDiscountStrategy(
    private val discountProperties: DiscountProperties,
) : DiscountStrategy {
    private val logger = KotlinLogging.logger {}

    override fun calculateDiscount(
        product: Product,
        quantity: Int,
    ): BigDecimal {
        logger.trace { "Calculating discount for product: $product, quantity: $quantity" }

        require(quantity >= 0) { "Quantity must be non-negative" }

        val discountRate = discountProperties.percentageBased?.toBigDecimal() ?: return BigDecimal.ZERO
        return product.price.multiply(BigDecimal(quantity)).multiply(discountRate).also {
            logger.debug { "Applying discount $discountRate for product quantity: $quantity, amount: $it" }
        }
    }

    override fun isApplicable(productQuantity: Int?): Boolean = true
}
