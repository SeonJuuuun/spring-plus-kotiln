package org.example.expert

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExpertKotlinApplication

fun main(args: Array<String>) {
    runApplication<ExpertKotlinApplication>(*args)
}
