package org.example.eshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class EshopApplication

fun main(args: Array<String>) {
    runApplication<EshopApplication>(*args)
}
