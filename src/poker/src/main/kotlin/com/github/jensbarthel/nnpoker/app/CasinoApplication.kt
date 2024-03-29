package com.github.jensbarthel.nnpoker.app

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.github.jensbarthel.nnpoker")
class CasinoApplication  : CommandLineRunner {
    override fun run(vararg args: String?) {}
}

fun main() {
    SpringApplicationBuilder().initializers(
    ).sources(CasinoApplication::class.java).run().registerShutdownHook()
}