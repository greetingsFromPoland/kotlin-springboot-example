package org.example.eshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// @EnableConfigurationProperties
@SpringBootApplication
class EshopApplication

fun main(args: Array<String>) {
    runApplication<EshopApplication>(*args)
}
