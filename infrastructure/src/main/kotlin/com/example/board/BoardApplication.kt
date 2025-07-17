package com.example.board

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["com.example.board", "config", "adapter", "service"])
@EnableJpaRepositories(basePackages = ["adapter.outbound.persistence"])
@EntityScan(basePackages = ["adapter.outbound.persistence"])
class BoardApplication

fun main(args: Array<String>) {
    runApplication<BoardApplication>(*args)
}
