package br.com.devcave.saga.payment.client

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class PaymentGatewayClient {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val random = Random(System.currentTimeMillis())

    fun doPayment(): Boolean {
        logger.info("PaymentGatewayClient#doPayment, start")

        return when (random.nextInt(0, 100)) {
            in 0..60 -> {
                logger.info("PaymentGatewayClient#doPayment, payment success!")
                true
            }

            in 61..80 -> {
                logger.warn("PaymentGatewayClient#doPayment, payment random wrong")
                false
            }

            else -> {
                throw RuntimeException("PaymentGatewayClient#doPayment, gateway exception")
            }
        }
    }

    fun doRefund() {
        logger.info("PaymentGatewayClient#doRefund, start")
    }
}