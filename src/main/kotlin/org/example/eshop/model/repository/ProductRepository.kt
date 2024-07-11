package org.example.eshop.model.repository

import org.example.eshop.model.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

interface ProductJpaRepository : JpaRepository<Product, String>

@Repository
class ProductRepository(
    private val repository: ProductJpaRepository,
) {
    fun findById(id: String): Product? = repository.findById(id).getOrNull()
}
