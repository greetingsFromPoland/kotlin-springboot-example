package org.example.eshop.application.discount

import java.math.BigDecimal

interface DiscountStrategy {
    /**
     * Calculates discount for given quantity.
     *
     * @param intermediatePrice price of product before this discount
     * @param quantity quantity of product for which discount should be calculated
     * @return discount value
     */
    fun calculateDiscount(
        intermediatePrice: BigDecimal,
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
