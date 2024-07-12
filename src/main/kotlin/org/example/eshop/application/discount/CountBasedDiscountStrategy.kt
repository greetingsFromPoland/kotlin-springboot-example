package org.example.eshop.application.discount

import mu.KotlinLogging
import org.example.eshop.model.entity.Product
import org.example.eshop.properties.DiscountProperties
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CountBasedDiscountStrategy(
    private val discountProperties: DiscountProperties,
) : DiscountStrategy {
    private val logger = KotlinLogging.logger {}

    override fun calculateDiscount(
        product: Product,
        quantity: Int,
    ): BigDecimal {
        logger.trace { "Calculating discount for product: $product, quantity: $quantity" }

        require(quantity >= 0) { "Quantity must be non-negative" }

        val discountRate = findHighestDiscount(quantity)?.toBigDecimal() ?: return BigDecimal.ZERO
        return product.price.multiply(BigDecimal(quantity)).multiply(discountRate).also {
            logger.debug { "Applying discount $discountRate for product quantity: $quantity, amount: $it" }
        }
    }

    private fun findHighestDiscount(productQuantity: Int): Double? =
        discountProperties.countBased
            .filterKeys { it <= productQuantity }
            .maxByOrNull { it.key }
            ?.value

    override fun isApplicable(productQuantity: Int?): Boolean {
        logger.trace { "Checking if discount is applicable for product quantity: $productQuantity" }

        require(productQuantity != null && productQuantity >= 0) { "Quantity must be non-negative and not null" }

        return productQuantity?.let {
            discountProperties.countBased.keys.any { it <= productQuantity }.also {
                if (it) logger.debug { "Found applicable discount for product quantity: $productQuantity" }
            }
        } ?: false
    }
}
