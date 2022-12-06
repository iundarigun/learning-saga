package br.com.devcave.saga.order.repository

import br.com.devcave.saga.order.domain.entity.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepository: CrudRepository<Order, Long>