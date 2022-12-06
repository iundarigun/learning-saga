package br.com.devcave.saga.payment.messaging

import br.com.devcave.saga.configuration.Queue
import br.com.devcave.saga.domain.OrderSagaMessage
import br.com.devcave.saga.payment.service.PaymentService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class OrderConsumer(
    private val paymentService: PaymentService,
    private val producer: Producer) {

    private val logger = LoggerFactory.getLogger(javaClass)


    @RabbitListener(queues = [Queue.PAYMENT_ORDER_QUEUE], concurrency = "1")
    fun consumer(message: OrderSagaMessage) {
        logger.info("OrderConsumer#consumer, message=$message")
        runCatching {
            paymentService.processPayment(message)
        }.onFailure {
            logger.warn("OrderConsumer#consumer, error processing payment. Message=${it.message}")
            producer.sendCompensation(message)
        }
    }
}