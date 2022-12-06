package br.com.devcave.saga.domain

data class OrderSagaMessage(
    val id: Long,
    val value: Int,
    val items: List<ItemSagaMessage>
)

data class ItemSagaMessage(
    val sku: String,
    val quantity: Int
)