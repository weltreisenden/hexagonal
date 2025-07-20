package com.example.board.server.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(
    basePackages =
    [
        "com.example.board"
    ]
)

@EntityScan(basePackages = ["adapter.outbound.persistence"])
class BoardApplication

fun main(args: Array<String>) {
    runApplication<BoardApplication>(*args)
}
