package org.example.eshop.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import org.example.eshop.model.entity.Product
import org.example.eshop.model.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@DataJpaTest(
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            classes = [Repository::class],
        ),
    ],
)
class ProductRepositoryTest : StringSpec() {
    private val products = mutableListOf<Product>()

    @Autowired
    private lateinit var productRepository: ProductRepository

    override suspend fun beforeEach(testCase: TestCase) {
        super.beforeEach(testCase)

        Product(id = "1", name = "Product 1", price = BigDecimal("100.00")).let {
            products.add(it)
            productRepository.save(it)
        }
    }

    override suspend fun afterEach(
        testCase: TestCase,
        result: TestResult,
    ) {
        super.afterEach(testCase, result)

        productRepository.deleteAll(products)
    }

    init {
        "should return product by id" {
            val product = productRepository.findById("1")
            product?.id shouldBe "1"
        }
    }
}
