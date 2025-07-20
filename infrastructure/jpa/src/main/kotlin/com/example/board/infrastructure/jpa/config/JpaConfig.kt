package com.example.board.infrastructure.jpa.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["com.example.board.infrastructure.jpa"])
@EnableJpaRepositories(basePackages = ["com.example.board.infrastructure.jpa"])
class JpaConfig {

}