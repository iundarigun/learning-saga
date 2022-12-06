package br.com.devcave.saga.order.service

import br.com.devcave.saga.domain.ItemSagaMessage
import br.com.devcave.saga.domain.OrderSagaMessage
import br.com.devcave.saga.order.domain.ItemRequest
import br.com.devcave.saga.order.domain.OrderRequest
import br.com.devcave.saga.order.domain.entity.Order
import br.com.devcave.saga.order.domain.entity.OrderItem
import br.com.devcave.saga.order.domain.entity.OrderStatus
import br.com.devcave.saga.order.messaging.Producer
import br.com.devcave.saga.order.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val producer: Producer,
    private val orderRepository: OrderRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun create(request: OrderRequest) {
        logger.info("OrderService#create, request=$request")
        val order = orderRepository.save(request.toEntity())
        producer.send(order.toMessage())
    }

    @Transactional
    fun processCompensation(request: OrderSagaMessage) {
        logger.info("OrderService#processCompensation, request=$request")
        orderRepository
            .findById(request.id)
            .ifPresent {
                it.status = OrderStatus.ERROR
                orderRepository.save(it)
            }
    }
}

private fun Order.toMessage(): OrderSagaMessage =
    OrderSagaMessage(
        id = this.id,
        value = this.value,
        items = this.items.map {
            ItemSagaMessage(it.sku, it.quantity)
        })


private fun OrderRequest.toEntity(): Order =
    Order(
        userId = "ME",
        value = this.value,
        status = OrderStatus.PENDING).also {
        this.items.forEach { ir ->
            it.items.add(ir.toOrderItem(it))
        }
    }

private fun ItemRequest.toOrderItem(order: Order): OrderItem =
    OrderItem(
        sku = this.sku,
        quantity = this.quantity,
        order = order
    )
