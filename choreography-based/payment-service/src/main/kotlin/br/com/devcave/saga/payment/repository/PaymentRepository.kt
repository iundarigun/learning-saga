package br.com.devcave.saga.payment.repository

import br.com.devcave.saga.payment.domain.entity.Payment
import br.com.devcave.saga.payment.domain.entity.PaymentStatus
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface PaymentRepository:CrudRepository<Payment, Long> {
    fun findByOrderIdAndStatus(orderId: Long, status: PaymentStatus): Optional<Payment>
    fun existsByOrderId(orderId: Long): Boolean
}
