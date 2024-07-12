package org.example.eshop.application.product

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.example.eshop.model.ProductFixture
import org.example.eshop.model.repository.ProductRepository

class ProductServiceTest : StringSpec() {
    private val productRepository: ProductRepository = mockk()

    private val productService = ProductService(productRepository)

    override suspend fun afterEach(
        testCase: TestCase,
        result: TestResult,
    ) {
        super.afterEach(testCase, result)
        clearAllMocks()
    }

    init {
        "should get product by ID" {
            val productFixture = ProductFixture.createProduct()
            every { productRepository.findById(any()) } returns productFixture

            val product = productService.getProduct("1")

            assertSoftly {
                product shouldNotBe null
                product?.id shouldBe productFixture.id
                product?.name shouldBe productFixture.name
                product?.price shouldBe productFixture.price.toDouble()
            }
        }

        "should throw exception when product ID is blank" {
            shouldThrow<IllegalArgumentException> {
                productService.getProduct("")
            }
        }

        "should return null when product is not found" {
            every { productRepository.findById(any()) } returns null

            val product = productService.getProduct("1")

            product shouldBe null
        }
    }
}
