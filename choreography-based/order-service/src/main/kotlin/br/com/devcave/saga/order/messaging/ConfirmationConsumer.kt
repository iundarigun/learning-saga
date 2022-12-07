package br.com.devcave.saga.order.messaging

import br.com.devcave.saga.configuration.Queue
import br.com.devcave.saga.domain.OrderSagaMessage
import br.com.devcave.saga.order.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class ConfirmationConsumer(
    private val orderService: OrderService) {

    private val logger = LoggerFactory.getLogger(javaClass)


    @RabbitListener(queues = [Queue.CONFIRMATION_ORDER_QUEUE], concurrency = "1")
    fun consumer(message: OrderSagaMessage) {
        logger.info("ConfirmationConsumer#consumer, message=$message")
        runCatching {
            orderService.processConfirmation(message)
        }.onFailure {
            logger.warn("ConfirmationConsumer#consumer, error processing confirmation. Message=${it.message}")
        }
    }
}