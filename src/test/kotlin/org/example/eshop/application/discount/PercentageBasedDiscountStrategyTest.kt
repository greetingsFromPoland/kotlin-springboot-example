package org.example.eshop.application.discount

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.bigdecimal.shouldBeEqualIgnoringScale
import org.example.eshop.model.ProductFixture
import org.example.eshop.properties.DiscountProperties
import java.math.BigDecimal

class PercentageBasedDiscountStrategyTest : StringSpec() {
    val discountProperties = DiscountProperties(percentageBased = 0.1)
    val percentageBasedDiscountStrategy = PercentageBasedDiscountStrategy(discountProperties)

    init {

        "should apply correct discount for single product" {
            val product = ProductFixture.createProduct(price = BigDecimal("100.00"))
            percentageBasedDiscountStrategy.calculateDiscount(product, 1) shouldBeEqualIgnoringScale BigDecimal("10.00")
        }

        "should apply correct discount for multiple products" {
            val product = ProductFixture.createProduct(price = BigDecimal("100.00"))
            percentageBasedDiscountStrategy.calculateDiscount(product, 3) shouldBeEqualIgnoringScale BigDecimal("30.00")
        }

        "should return zero discount when discount rate is zero" {
            val noDiscountProperties = DiscountProperties(percentageBased = 0.0)
            val noDiscountStrategy = PercentageBasedDiscountStrategy(noDiscountProperties)
            val product = ProductFixture.createProduct(price = BigDecimal("100.00"))
            noDiscountStrategy.calculateDiscount(product, 1) shouldBeEqualIgnoringScale BigDecimal.ZERO
        }

        "should handle negative quantities by returning exception" {
            val product = ProductFixture.createProduct(price = BigDecimal("100.00"))
            shouldThrow<IllegalArgumentException> {
                percentageBasedDiscountStrategy.calculateDiscount(product, -1)
            }
        }

        "should handle zero quantity by returning zero discount" {
            val product = ProductFixture.createProduct(price = BigDecimal("100.00"))
            percentageBasedDiscountStrategy.calculateDiscount(product, 0) shouldBeEqualIgnoringScale BigDecimal.ZERO
        }

        "should apply discount correctly with different product prices" {
            val product = ProductFixture.createProduct(price = BigDecimal("200.00"))
            percentageBasedDiscountStrategy.calculateDiscount(product, 2) shouldBeEqualIgnoringScale BigDecimal("40.00")
        }
    }
}
