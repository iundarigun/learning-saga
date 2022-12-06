package br.com.devcave.saga.order.controller

import br.com.devcave.saga.order.domain.OrderRequest
import br.com.devcave.saga.order.service.OrderService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("orders")
class OrderController(
    private val orderService: OrderService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun create(@Valid @RequestBody request: OrderRequest) {
        logger.info("orderController#create, request=$request")
        orderService.create(request)
    }
}