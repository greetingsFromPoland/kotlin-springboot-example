package org.example.eshop.application.discount

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.bigdecimal.shouldBeEqualIgnoringScale
import io.kotest.matchers.shouldBe
import org.example.eshop.model.ProductFixture
import org.example.eshop.properties.DiscountProperties
import java.math.BigDecimal

class CountBasedDiscountStrategyTest : StringSpec() {
    private val discountProperties: DiscountProperties =
        DiscountProperties(
            countBased =
                mapOf(
                    2 to 0.1,
                    3 to 0.2,
                    4 to 0.3,
                ),
        )

    private val countBasedDiscountStrategy = CountBasedDiscountStrategy(discountProperties)

    init {
        "should not be applicable when product quantity is null" {
            countBasedDiscountStrategy.isApplicable(null) shouldBe false
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
            val product = ProductFixture.createProduct(price = BigDecimal("100.00"))
            countBasedDiscountStrategy.calculateDiscount(product, 2) shouldBeEqualIgnoringScale BigDecimal("20.00")
        }

        "calculate discount for quantity above discount threshold" {
            val product = ProductFixture.createProduct(price = BigDecimal("100.00"))
            countBasedDiscountStrategy.calculateDiscount(product, 5) shouldBeEqualIgnoringScale BigDecimal("150.00")
        }

        "return zero discount for quantity below any discount threshold" {
            val product = ProductFixture.createProduct(price = BigDecimal("100.00"))
            countBasedDiscountStrategy.calculateDiscount(product, 1) shouldBeEqualIgnoringScale BigDecimal.ZERO
        }

        "calculate discount correctly with different product prices" {
            val product = ProductFixture.createProduct(price = BigDecimal("200.00"))
            countBasedDiscountStrategy.calculateDiscount(product, 3) shouldBeEqualIgnoringScale BigDecimal("120.00")
        }

        "handle edge cases for product quantity" {
            val product = ProductFixture.createProduct(price = BigDecimal("200.00"))
            countBasedDiscountStrategy.calculateDiscount(product, 0) shouldBe BigDecimal.ZERO
            countBasedDiscountStrategy.calculateDiscount(product, -1) shouldBe BigDecimal.ZERO
        }
    }
}
