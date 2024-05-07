package ch.obermuhlner.ammonites

import ch.obermuhlner.ammonites.ammonite.AmmoniteService
import ch.obermuhlner.ammonites.jooq.Tables
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext

fun main(args: Array<String>) {
    val context: ConfigurableApplicationContext = SpringApplication.run(AmmonitesApplication::class.java, *args)

    val ammoniteService = context.getBean(AmmoniteService::class.java)

    val sortedRecords = ammoniteService.findMatchingAmmonitesWithMeasurements(diameterSide = 2.7, diameterCross = 0.1 , proportion_n = 39.0, proportion_h = 36.0, proportion_q = 1.0, count_z = 38.0)
    sortedRecords.forEach { entry ->
        println("Match: ${entry.key} ${entry.value.ammonite.taxonomyGenus}")
        println(entry.value)
        println()
    }

    System.exit(0)
}
