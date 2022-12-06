package br.com.devcave.saga.order.messaging

import br.com.devcave.saga.configuration.Queue
import br.com.devcave.saga.domain.OrderSagaMessage
import br.com.devcave.saga.order.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class CompensationConsumer(
    private val orderService: OrderService) {

    private val logger = LoggerFactory.getLogger(javaClass)


    @RabbitListener(queues = [Queue.COMPENSATION_ORDER_QUEUE], concurrency = "1")
    fun consumer(message: OrderSagaMessage) {
        logger.info("CompensationConsumer#consumer, message=$message")
        runCatching {
            orderService.processCompensation(message)
        }.onFailure {
            logger.warn("CompensationConsumer#consumer, error processing compensation. Message=${it.message}")
        }
    }
}