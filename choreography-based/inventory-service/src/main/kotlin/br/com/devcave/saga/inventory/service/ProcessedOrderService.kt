package br.com.devcave.saga.inventory.service

import br.com.devcave.saga.inventory.domain.entity.ProcessedOrder
import br.com.devcave.saga.inventory.repository.ProcessedOrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class ProcessedOrderService(private val processedOrderRepository: ProcessedOrderRepository) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun save(id: Long) {
        processedOrderRepository.save(ProcessedOrder(id = id))
    }
}