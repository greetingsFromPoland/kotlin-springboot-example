package org.example.eshop.application.discount

import mu.KotlinLogging
import org.example.eshop.model.api.discount.CalculateDiscountRequest
import org.example.eshop.model.api.discount.CalculateDiscountResponse
import org.example.eshop.model.repository.ProductRepository
import org.springframework.stereotype.Service
import java.math.RoundingMode

@Service
class DiscountService(
    private val productRepository: ProductRepository,
    private val discountStrategies: List<DiscountStrategy>,
) {
    private val logger = KotlinLogging.logger {}

    fun calculateFinalPrice(
        uuid: String,
        request: CalculateDiscountRequest,
    ): CalculateDiscountResponse? {
        logger.trace { "Calculating final price for request: $request" }

        require(uuid.isNotBlank()) { "Product ID must not be blank" }

        val product =
            productRepository.findById(uuid)
                ?: return null

        var intermediatePrice = product.price.multiply(request.quantity.toBigDecimal())

        discountStrategies
            .filter { it.isApplicable(request.quantity) }
            .forEach { strategy ->
                val discount = strategy.calculateDiscount(intermediatePrice, request.quantity)
                intermediatePrice = intermediatePrice.minus(discount)
            }

        return CalculateDiscountResponse(
            uuid = uuid,
            quantity = request.quantity,
            totalPrice = intermediatePrice.setScale(2, RoundingMode.DOWN).toDouble(),
        ).also {
            logger.debug { "Calculated final price: $it for product: $product and quantity: ${request.quantity}" }
        }
    }
}
