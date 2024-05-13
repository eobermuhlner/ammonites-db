package ch.obermuhlner.ammonites

import ch.obermuhlner.ammonites.ammonite.AmmoniteService
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext

fun main(args: Array<String>) {
    val context: ConfigurableApplicationContext = SpringApplication.run(AmmonitesApplication::class.java, *args)

    val ammoniteService = context.getBean(AmmoniteService::class.java)

    val sortedMatches = ammoniteService.findMatchingAmmonitesWithMeasurements(diameterSide = 2.7, diameterCross = 0.1 , proportion_n = 39.0, proportion_h = 36.0, proportion_q = 1.0, count_primary_ribs = 40.0)
    sortedMatches.forEach { match ->
        println("Match: ${match.distance} ${match.ammonite.taxonomySpecies}")
        println(match.measurement)
        println()
    }

    System.exit(0)
}

