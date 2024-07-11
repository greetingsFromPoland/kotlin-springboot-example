package org.example.eshop.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.util.UUID

@Entity
data class Product(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: BigDecimal,
)
