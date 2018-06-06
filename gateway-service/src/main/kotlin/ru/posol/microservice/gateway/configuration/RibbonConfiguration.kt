package ru.posol.microservice.gateway.configuration

import com.netflix.loadbalancer.AvailabilityFilteringRule
import com.netflix.client.config.IClientConfig
import com.netflix.loadbalancer.IRule
import com.netflix.loadbalancer.PingUrl
import com.netflix.loadbalancer.IPing
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

// not annotated with @Configuration,  reference it from a annotation added to the main application class called @RibbonClients
class RibbonConfiguration {

    @Bean
    fun ribbonPing(config: IClientConfig): IPing {
        return PingUrl(false, " /application/health")
    }

    @Bean
    fun ribbonRule(config: IClientConfig): IRule {
        return AvailabilityFilteringRule()
    }

}