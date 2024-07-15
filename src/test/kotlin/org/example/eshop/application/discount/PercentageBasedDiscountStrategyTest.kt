package org.example.eshop.application.discount

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.bigdecimal.shouldBeEqualIgnoringScale
import org.example.eshop.properties.DiscountProperties
import java.math.BigDecimal

class PercentageBasedDiscountStrategyTest : StringSpec() {
    val discountProperties = DiscountProperties(percentageBased = 0.1)
    val percentageBasedDiscountStrategy = PercentageBasedDiscountStrategy(discountProperties)

    init {

        "should apply correct discount for single product" {
            percentageBasedDiscountStrategy.calculateDiscount(
                BigDecimal("100.00"),
                1,
            ) shouldBeEqualIgnoringScale BigDecimal("10.00")
        }

        "should apply correct discount for multiple products" {
            percentageBasedDiscountStrategy.calculateDiscount(
                BigDecimal("300.00"),
                3,
            ) shouldBeEqualIgnoringScale BigDecimal("30.00")
        }

        "should return zero discount when discount rate is zero" {
            val noDiscountProperties = DiscountProperties(percentageBased = 0.0)
            val noDiscountStrategy = PercentageBasedDiscountStrategy(noDiscountProperties)
            noDiscountStrategy.calculateDiscount(BigDecimal("300.00"), 1) shouldBeEqualIgnoringScale BigDecimal.ZERO
        }

        "should handle negative quantities by returning exception" {
            shouldThrow<IllegalArgumentException> {
                percentageBasedDiscountStrategy.calculateDiscount(BigDecimal("100.00"), -1)
            }
        }

        "should handle zero quantity by returning zero discount" {
            percentageBasedDiscountStrategy.calculateDiscount(
                BigDecimal("100.00"),
                0,
            ) shouldBeEqualIgnoringScale BigDecimal.ZERO
        }

        "should apply discount correctly with different product prices" {
            percentageBasedDiscountStrategy.calculateDiscount(
                BigDecimal("400.00"),
                2,
            ) shouldBeEqualIgnoringScale BigDecimal("40.00")
        }
    }
}
