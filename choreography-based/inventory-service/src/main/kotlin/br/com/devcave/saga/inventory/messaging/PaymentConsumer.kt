package br.com.devcave.saga.inventory.messaging

import br.com.devcave.saga.configuration.Queue
import br.com.devcave.saga.domain.OrderSagaMessage
import br.com.devcave.saga.inventory.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class PaymentConsumer(
    private val productService: ProductService,
    private val producer: Producer) {

    private val logger = LoggerFactory.getLogger(javaClass)


    @RabbitListener(queues = [Queue.INVENTORY_ORDER_QUEUE], concurrency = "1")
    fun consumer(message: OrderSagaMessage) {
        logger.info("PaymentConsumer#consumer, message=$message")
        runCatching {
            productService.processInventory(message)
        }.onFailure {
            logger.warn("PaymentConsumer#consumer, error processing inventory. Message=${it.message}")
            producer.sendCompensation(message)
        }
    }
}