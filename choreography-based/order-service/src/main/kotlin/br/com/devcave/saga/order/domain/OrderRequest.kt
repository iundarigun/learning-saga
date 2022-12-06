package br.com.devcave.saga.order.domain

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive

data class OrderRequest(
    @field:Positive
    val value: Int,
    @field:NotEmpty
    @field:Valid
    val items: List<ItemRequest>
)

data class ItemRequest(
    val sku: String,
    @field:Positive
    val quantity: Int
)
