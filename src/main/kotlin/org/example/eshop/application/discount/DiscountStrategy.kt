package org.example.eshop.application.discount

import org.example.eshop.model.entity.Product
import java.math.BigDecimal

interface DiscountStrategy {
    /**
     * Calculates discount for given product and quantity.
     *
     * @param product product for which discount should be calculated
     * @param quantity quantity of product for which discount should be calculated
     * @return discount value
     */
    fun calculateDiscount(
        product: Product,
        quantity: Int,
    ): BigDecimal

    /**
     * Checks if discount is applicable for given product quantity.
     *
     * @param productQuantity quantity of product for which discount should be checked
     * @return true if discount is applicable, false otherwise
     */
    fun isApplicable(productQuantity: Int? = null): Boolean
}
