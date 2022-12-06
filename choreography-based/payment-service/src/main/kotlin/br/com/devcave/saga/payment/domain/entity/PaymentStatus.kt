package br.com.devcave.saga.payment.domain.entity

enum class PaymentStatus {
    INITIATED,
    PROCESSED,
    ERROR,
    REFUND;
}
