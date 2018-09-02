package com.scooters

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScootersApplication

fun main(args: Array<String>) {
    runApplication<ScootersApplication>(*args)
}
