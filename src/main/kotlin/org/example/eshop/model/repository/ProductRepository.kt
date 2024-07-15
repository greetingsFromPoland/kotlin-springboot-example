package org.example.eshop.model.repository

import org.example.eshop.model.entity.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

interface ProductJpaRepository : CrudRepository<Product, String>

@Repository
class ProductRepository(
    private val repository: ProductJpaRepository,
) {
    fun findById(id: String): Product? = repository.findById(id).getOrNull()

    fun deleteAll(products: List<Product>) = repository.deleteAll(products)

    fun save(product: Product) = repository.save(product)
}
