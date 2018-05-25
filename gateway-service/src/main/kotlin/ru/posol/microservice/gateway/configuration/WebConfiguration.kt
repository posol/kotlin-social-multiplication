package ru.posol.microservice.gateway.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.config.annotation.CorsRegistry



@Configuration
@EnableWebMvc
class WebConfiguration : WebMvcConfigurerAdapter() {

    /**
     * Enables Cross-Origin Resource Sharing (CORS)
     * */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }
}