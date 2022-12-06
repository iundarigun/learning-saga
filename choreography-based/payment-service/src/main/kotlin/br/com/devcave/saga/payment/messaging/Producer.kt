package br.com.devcave.saga.payment.messaging

import br.com.devcave.saga.configuration.Queue
import br.com.devcave.saga.configuration.Queue.COMPENSATION_ORDER_QUEUE
import br.com.devcave.saga.configuration.Queue.EXCHANGE
import br.com.devcave.saga.configuration.Queue.INVENTORY_ORDER_QUEUE
import br.com.devcave.saga.domain.OrderSagaMessage
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class Producer(private val rabbitTemplate: RabbitTemplate) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun sendInventory(message: OrderSagaMessage) {
        logger.info("Producer#sendInventory, message=$message")
        rabbitTemplate.convertAndSend(EXCHANGE, INVENTORY_ORDER_QUEUE, message)
    }

    fun sendCompensation(message: OrderSagaMessage) {
        logger.info("Producer#sendCompensation, message=$message")
        rabbitTemplate.convertAndSend(EXCHANGE, COMPENSATION_ORDER_QUEUE, message)
    }
}