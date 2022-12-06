package br.com.devcave.saga.payment.messaging

import br.com.devcave.saga.configuration.Queue
import br.com.devcave.saga.domain.OrderSagaMessage
import br.com.devcave.saga.payment.service.PaymentService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class CompensationConsumer(
    private val paymentService: PaymentService) {

    private val logger = LoggerFactory.getLogger(javaClass)


    @RabbitListener(queues = [Queue.COMPENSATION_PAYMENT_QUEUE], concurrency = "1")
    fun consumer(message: OrderSagaMessage) {
        logger.info("CompensationConsumer#consumer, message=$message")
        runCatching {
            paymentService.processCompensation(message)
        }.onFailure {
            logger.warn("CompensationConsumer#consumer, error processing compensation. Message=${it.message}")
        }
    }
}