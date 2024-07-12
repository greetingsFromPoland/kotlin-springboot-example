package org.example.eshop.application.discount

import io.kotest.core.spec.style.StringSpec
import io.mockk.mockk

class DiscountServiceTest : StringSpec() {
    private val discountService: DiscountService = mockk()

    init {

        "should correctly calculate quantity discount" {
//            TODO("Not yet implemented")
        }

        "should correctly calculate percentage discount" {
//            TODO("Not yet implemented")
        }

        "should correctly apply both discounts" {
//            TODO("Not yet implemented")
        }

        "should return null when product does not exist" {
//            TODO("Not yet implemented")
        }

        "should correctly respond when no discount strategy exists" {
//            TODO("Not yet implemented")
        }
    }
}
