package org.example.eshop.application.discount

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.bigdecimal.shouldBeEqualIgnoringScale
import io.kotest.matchers.shouldBe
import org.example.eshop.properties.DiscountProperties
import java.math.BigDecimal

class CountBasedDiscountStrategyTest : StringSpec() {
    private val discountProperties: DiscountProperties =
        DiscountProperties(
            countBased =
                mutableMapOf(
                    2 to 0.1,
                    3 to 0.2,
                    4 to 0.3,
                ),
        )

    private val countBasedDiscountStrategy = CountBasedDiscountStrategy(discountProperties)

    init {
        "should not be applicable when product quantity is null" {
            shouldThrow<IllegalArgumentException> {
                countBasedDiscountStrategy.isApplicable(null) shouldBe false
            }
        }

        "should be applicable when product quantity is bigger than discount quantity" {
            countBasedDiscountStrategy.isApplicable(5) shouldBe true
        }

        "should be applicable when product quantity is the same as discount quantity" {
            countBasedDiscountStrategy.isApplicable(4) shouldBe true
        }

        "should not be applicable when product quantity is smaller than discount quantity" {
            countBasedDiscountStrategy.isApplicable(1) shouldBe false
        }

        "calculate discount for quantity matching discount threshold" {
            countBasedDiscountStrategy.calculateDiscount(BigDecimal("200.00"), 2) shouldBeEqualIgnoringScale BigDecimal("20.00")
        }

        "should return exception when product quantity is negative" {
            shouldThrow<IllegalArgumentException> {
                countBasedDiscountStrategy.calculateDiscount(BigDecimal("200.00"), -1)
            }
        }

        "calculate discount for quantity above discount threshold" {
            countBasedDiscountStrategy.calculateDiscount(BigDecimal("500.00"), 5) shouldBeEqualIgnoringScale BigDecimal("150.00")
        }

        "return zero discount for quantity below any discount threshold" {
            countBasedDiscountStrategy.calculateDiscount(BigDecimal("100.00"), 1) shouldBeEqualIgnoringScale BigDecimal.ZERO
        }

        "calculate discount correctly with different product prices" {
            countBasedDiscountStrategy.calculateDiscount(BigDecimal("600.00"), 3) shouldBeEqualIgnoringScale BigDecimal("120.00")
        }

        "handle edge cases for product quantity" {
            countBasedDiscountStrategy.calculateDiscount(BigDecimal("200.00"), 0) shouldBe BigDecimal.ZERO
        }

        "should handle negative quantities by returning exception" {
            shouldThrow<IllegalArgumentException> {
                countBasedDiscountStrategy.calculateDiscount(BigDecimal("200.00"), -1)
            }
        }
    }
}
