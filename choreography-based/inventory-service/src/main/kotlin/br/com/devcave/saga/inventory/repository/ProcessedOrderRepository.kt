package br.com.devcave.saga.inventory.repository

import br.com.devcave.saga.inventory.domain.entity.ProcessedOrder
import org.springframework.data.repository.CrudRepository

interface ProcessedOrderRepository : CrudRepository<ProcessedOrder, Long>
