package br.com.devcave.saga.order.configuration

import br.com.devcave.saga.configuration.Queue.EXCHANGE
import br.com.devcave.saga.configuration.Queue.QUEUE_LIST
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueueConfiguration(private val connectionFactory: ConnectionFactory) {

    @Bean
    fun messageConverter(objectMapper: ObjectMapper): MessageConverter {
        return Jackson2JsonMessageConverter(objectMapper)
    }

    @PostConstruct
    fun createObjects() {
        val rabbitAdmin = RabbitAdmin(connectionFactory)
        createExchange(rabbitAdmin)
        createQueues(rabbitAdmin)
    }

    private fun createExchange(rabbitAdmin: RabbitAdmin) {
        val exchange = ExchangeBuilder
            .directExchange(EXCHANGE)
            .durable(true)
            .build<Exchange>()
        rabbitAdmin.declareExchange(exchange)
    }

    private fun createQueues(rabbitAdmin: RabbitAdmin) {
        QUEUE_LIST.forEach {
            val queue = QueueBuilder.durable(it)
                .build()
            val binding = Binding(
                it,
                Binding.DestinationType.QUEUE,
                EXCHANGE,
                it,
                null
            )
            rabbitAdmin.declareQueue(queue)
            rabbitAdmin.declareBinding(binding)
        }
    }
}