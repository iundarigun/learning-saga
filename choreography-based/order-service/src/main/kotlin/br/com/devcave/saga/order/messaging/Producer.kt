package br.com.devcave.saga.order.messaging

import br.com.devcave.saga.configuration.Queue
import br.com.devcave.saga.domain.OrderSagaMessage
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class Producer(private val rabbitTemplate: RabbitTemplate) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun send(message: OrderSagaMessage) {
        logger.info("Producer#send, message=$message")
        rabbitTemplate.convertAndSend(Queue.EXCHANGE, Queue.PAYMENT_ORDER_QUEUE, message)
    }
}