package com.example.board.infrastructure.jpa.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.io.ClassPathResource




@Configuration
class JpaEnvironmentPostProcessor : EnvironmentPostProcessor {

    var yamlLoader: YamlPropertySourceLoader = YamlPropertySourceLoader()
    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {

        val activeProfile = environment.getProperty("spring.profiles.active")

        val resourcePath = when (activeProfile) {
            "real" -> "real"
            else -> "dev"
        }

        val path = ClassPathResource("jpa/${resourcePath}.yaml")
        val propertySources = yamlLoader.load("jpa-config", path)

        for (propertySource in propertySources) {
            environment.propertySources.addLast(propertySource)
        }
    }
}