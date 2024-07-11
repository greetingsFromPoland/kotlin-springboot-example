package org.example.eshop.model

import org.example.eshop.model.entity.Product
import java.math.BigDecimal
import java.util.*

class ProductFixture {
    companion object {
        fun createProduct(
            id: String = UUID.randomUUID().toString(),
            name: String = "Product 1",
            price: BigDecimal = BigDecimal(100.0),
        ) = Product(
            id = id,
            name = name,
            price = price,
        )
    }
}
