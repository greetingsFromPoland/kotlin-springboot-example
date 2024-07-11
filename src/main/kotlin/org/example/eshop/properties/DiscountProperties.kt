package org.example.eshop.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

typealias DiscountConfiguration = Map<Int, Double>

@Configuration
@ConfigurationProperties(prefix = "discount")
data class DiscountProperties(
    val countBased: DiscountConfiguration = emptyMap(),
    val percentageBased: Double = 0.0,
)
