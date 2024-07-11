package org.example.eshop.application.product

import org.example.eshop.model.api.ProductResponse
import org.example.eshop.model.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun getProduct(id: String): ProductResponse? {
        TODO("Not yet implemented")
    }
}
