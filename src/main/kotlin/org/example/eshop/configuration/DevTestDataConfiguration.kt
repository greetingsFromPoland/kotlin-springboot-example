package org.example.eshop.configuration

import mu.KotlinLogging
import org.example.eshop.model.entity.Product
import org.example.eshop.model.repository.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.math.BigDecimal

@Configuration
@Profile(AppProfile.DEV)
class DevTestDataConfiguration(
    private val productRepository: ProductRepository,
) {
    private val logger = KotlinLogging.logger {}

    @Bean
    fun createTestData(): Boolean {
        logger.info { "Creating test data" }

        listOf(
            Product(id = "7a7c5aa2-54cb-42e6-bc35-c424c3b56ec1", name = "IPhone", price = BigDecimal("10.50")),
            Product(id = "a78eecc1-98dd-4c57-82ce-7a2d09b2ca80", name = "Samsung", price = BigDecimal("20.50")),
            Product(id = "5faea262-17e7-4043-ab77-3386ee893abf", name = "Xiaomi", price = BigDecimal("30.50")),
            Product(id = "e21e7cf1-254f-409b-88cb-ce656cd5233d", name = "Huawei", price = BigDecimal("40.50")),
        ).forEach {
            productRepository.save(it)
            logger.info { "Product created: $it" }
        }

        return true
    }
}
