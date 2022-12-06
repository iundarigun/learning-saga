package br.com.devcave.saga.payment.service

import br.com.devcave.saga.domain.OrderSagaMessage
import br.com.devcave.saga.payment.client.PaymentGatewayClient
import br.com.devcave.saga.payment.domain.entity.Payment
import br.com.devcave.saga.payment.domain.entity.PaymentStatus
import br.com.devcave.saga.payment.messaging.Producer
import br.com.devcave.saga.payment.repository.PaymentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentService(
    private val paymentGatewayClient: PaymentGatewayClient,
    private val producer: Producer,
    private val paymentRepository: PaymentRepository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun processPayment(request: OrderSagaMessage) {
        logger.info("PaymentService#processPayment, request=$request")
        if (paymentRepository.existsByOrderId(request.id)) {
            logger.warn("PaymentService#processPayment, payment for order=${request.id} already exists")
        } else {
            val payment = paymentRepository.save(request.toPayment())

            val result = paymentGatewayClient.doPayment()
            if (result) {
                payment.status = PaymentStatus.PROCESSED
                producer.sendInventory(request)
            } else {
                payment.status = PaymentStatus.ERROR
                producer.sendCompensation(request)
            }
        }
    }

    @Transactional
    fun processCompensation(request: OrderSagaMessage) {
        logger.info("PaymentService#processCompensation, request=$request")
        paymentRepository.findByOrderIdAndStatus(request.id, PaymentStatus.PROCESSED).ifPresent {
            paymentGatewayClient.doRefund()
            it.status = PaymentStatus.REFUND
            paymentRepository.save(it)
        }
        producer.sendCompensation(request)
    }
}

private fun OrderSagaMessage.toPayment() =
    Payment(
        orderId = this.id,
        status = PaymentStatus.INITIATED,
        value = this.value
    )
