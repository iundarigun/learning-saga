package br.com.devcave.saga.configuration

object Queue {
    const val EXCHANGE = "SAGA_EXCHANGE"
    const val PAYMENT_ORDER_QUEUE = "PAYMENT_ORDER"
    const val INVENTORY_ORDER_QUEUE = "INVENTORY_ORDER"
    const val COMPENSATION_ORDER_QUEUE = "COMPENSATION_ORDER_QUEUE"
    const val COMPENSATION_PAYMENT_QUEUE = "COMPENSATION_PAYMENT_QUEUE"

    val QUEUE_LIST = listOf(PAYMENT_ORDER_QUEUE, INVENTORY_ORDER_QUEUE, COMPENSATION_ORDER_QUEUE, COMPENSATION_PAYMENT_QUEUE)
}