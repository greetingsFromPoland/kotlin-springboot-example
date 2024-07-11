package org.example.eshop.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
@ConfigurationProperties(prefix = "discount")
data class DiscountProperties(
    @Value("countBased")
    val countBased: Map<String, BigDecimal> = emptyMap(),
    val percentageBased: Map<String, BigDecimal> = emptyMap(),
)
