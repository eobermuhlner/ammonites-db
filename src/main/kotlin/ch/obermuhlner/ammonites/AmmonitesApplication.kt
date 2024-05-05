package ch.obermuhlner.ammonites

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class AmmonitesApplication

fun main(args: Array<String>) {
    runApplication<AmmonitesApplication>(*args)
}