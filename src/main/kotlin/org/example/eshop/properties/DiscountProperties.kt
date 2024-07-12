package org.example.eshop.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

typealias DiscountConfiguration = MutableMap<Int, Double>

@Configuration
@ConfigurationProperties(prefix = "discount")
data class DiscountProperties(
    @Value("count-based")
    var countBased: DiscountConfiguration = mutableMapOf(),
    @Value("percentage-based")
    var percentageBased: Double? = null,
)
