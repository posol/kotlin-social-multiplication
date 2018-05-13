package ru.posol.microservice.gamification.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer
import org.springframework.context.annotation.Configuration
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.amqp.core.BindingBuilder
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory

/**
 * Configures RabbitMQ to use events in our application.
 */
@Configuration
class RabbitMQConfiguration : RabbitListenerConfigurer {

    @Bean
    fun multiplicationExchange(@Value("\${multiplication.exchange}") exchangeName: String): TopicExchange {
        return TopicExchange(exchangeName)
    }

    @Bean
    fun gamificationMultiplicationQueue(@Value("\${multiplication.queue}") queueName: String): Queue {
        return Queue(queueName, true)
    }

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange,
                @Value("\${multiplication.anything.routingkey}")
                routingKey: String): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey)
    }

    /**
     * configure JSON deserialization in the subscriber
     */
    @Bean
    fun consumerJackson2MessageConverter(): MappingJackson2MessageConverter {
        return MappingJackson2MessageConverter()
    }

    /**
     * configure JSON deserialization in the subscriber
     */
    @Bean
    fun messageHandlerMethodFactory(): DefaultMessageHandlerMethodFactory {
        val factory = DefaultMessageHandlerMethodFactory()
        factory.setMessageConverter(consumerJackson2MessageConverter())
        return factory
    }

    /**
     * configure JSON deserialization in the subscriber
     */
    override fun configureRabbitListeners(registrar: RabbitListenerEndpointRegistrar) {
        registrar.messageHandlerMethodFactory = messageHandlerMethodFactory()
    }
}