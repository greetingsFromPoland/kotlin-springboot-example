package org.example.eshop.application.product

import mu.KotlinLogging
import org.example.eshop.model.api.ProductResponse
import org.example.eshop.model.entity.Product
import org.example.eshop.model.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    private val logger = KotlinLogging.logger {}

    /**
     * Get product by ID from database and map it to product response data class.
     *
     * @param id Product ID.
     * @return Product response.
     * @throws IllegalArgumentException If product ID is blank.
     */
    fun getProduct(id: String): ProductResponse? {
        logger.trace { "Getting product by ID: $id" }

        require(id.isNotBlank()) { "Product ID must not be blank" }

        return productRepository.findById(id)?.let {
            logger.debug { "Found product: $it" }
            it.toProductResponse()
        }
    }

    private fun Product.toProductResponse(): ProductResponse =
        ProductResponse(
            id = id,
            name = name,
            price = price.toDouble(),
        )
}
