package org.example.eshop.application.discount

import io.kotest.assertions.any
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.example.eshop.model.ProductFixture
import org.example.eshop.model.api.discount.CalculateDiscountRequest
import org.example.eshop.model.repository.ProductRepository
import org.example.eshop.properties.DiscountProperties
import java.math.BigDecimal

class DiscountServiceTest : StringSpec() {
    private val productRepository: ProductRepository = mockk()

    private val discountProperties: DiscountProperties =
        DiscountProperties(
            countBased =
                mutableMapOf(
                    2 to 0.1,
                    3 to 0.2,
                    4 to 0.3,
                ),
            percentageBased = 0.05,
        )

    private val countBasedDiscountStrategy: CountBasedDiscountStrategy = CountBasedDiscountStrategy(discountProperties)

    private val percentageDiscountStrategy: PercentageBasedDiscountStrategy =
        PercentageBasedDiscountStrategy(discountProperties)

    override suspend fun afterEach(
        testCase: TestCase,
        result: TestResult,
    ) {
        super.afterEach(testCase, result)
        clearAllMocks()
    }

    init {
        "should correctly calculate quantity discount" {
            val product = ProductFixture.createProduct(price = BigDecimal(100))
            val discountService =
                DiscountService(productRepository, listOf(countBasedDiscountStrategy))

            every { productRepository.findById(any()) } returns product

            val response = discountService.calculateFinalPrice(product.id, CalculateDiscountRequest(quantity = 2))

            response?.totalPrice shouldBe 180.0 // Assuming 100 * 2 - 10% discount
        }

        "should correctly calculate percentage discount" {
            val product = ProductFixture.createProduct(price = BigDecimal(100))
            val discountService =
                DiscountService(productRepository, listOf(percentageDiscountStrategy))

            every { productRepository.findById(any()) } returns product
            val response = discountService.calculateFinalPrice(product.id, CalculateDiscountRequest(quantity = 1))

            response?.totalPrice shouldBe 95.0 // Assuming 100 - 5% discount
        }

        "should correctly apply both discounts" {
            val product = ProductFixture.createProduct(price = BigDecimal(100))
            val discountService =
                DiscountService(productRepository, listOf(countBasedDiscountStrategy, percentageDiscountStrategy))

            every { productRepository.findById(any()) } returns product

            val response = discountService.calculateFinalPrice(product.id, CalculateDiscountRequest(quantity = 3))

            response?.totalPrice shouldBe 228.0 // Assuming 100 * 3 - 20% - 5% discount
        }

        "should return null when product does not exist" {
            every { productRepository.findById(any()) } returns null
            val discountService =
                DiscountService(productRepository, listOf(countBasedDiscountStrategy, percentageDiscountStrategy))

            val response = discountService.calculateFinalPrice("0", CalculateDiscountRequest(quantity = 1))
            response shouldBe null
        }

        "should correctly respond when no discount strategy exists" {
            val discountService =
                DiscountService(productRepository, emptyList())

            val product = ProductFixture.createProduct(price = BigDecimal(100))
            every { productRepository.findById(any()) } returns product
            val response =
                discountService.calculateFinalPrice(product.id, CalculateDiscountRequest(quantity = 1))
            response?.totalPrice shouldBe 100.0 // No discount applied
        }

        "should throw exception when quantity is negative" {
            val discountService =
                DiscountService(productRepository, listOf(countBasedDiscountStrategy, percentageDiscountStrategy))

            val product = ProductFixture.createProduct(price = BigDecimal(100))
            every { productRepository.findById(any()) } returns product
            shouldThrow<IllegalArgumentException> {
                discountService.calculateFinalPrice(product.id, CalculateDiscountRequest(quantity = -1))
            }
        }
    }
}
