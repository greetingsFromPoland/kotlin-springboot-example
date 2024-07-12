package org.example.eshop

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldNotBe
import org.example.eshop.application.discount.DiscountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EshopApplicationTests : StringSpec() {
    @Autowired
    private lateinit var discountService: DiscountService

    init {
        "context loads" {
            discountService shouldNotBe null
        }
    }
}
