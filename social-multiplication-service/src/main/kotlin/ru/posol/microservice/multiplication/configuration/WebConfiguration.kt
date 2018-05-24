package ru.posol.microservice.multiplication.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@EnableWebMvc
class WebConfiguration : WebMvcConfigurerAdapter() {

    /**
     * Enables Cross-Origin Resource Sharing (CORS)
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }
}