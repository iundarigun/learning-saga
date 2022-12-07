package br.com.devcave.saga.inventory.service

import br.com.devcave.saga.domain.OrderSagaMessage
import br.com.devcave.saga.inventory.messaging.Producer
import br.com.devcave.saga.inventory.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val processedOrderService: ProcessedOrderService,
    private val productRepository: ProductRepository,
    private val producer: Producer) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun processInventory(request: OrderSagaMessage) {
        logger.info("ProductService#processInventory, request=$request")
        runCatching {
            processedOrderService.save(request.id)
        }.onFailure {
            logger.warn("ProductService#processInventory, the message was already processed")
        }.onSuccess {
            request.items.forEach {
                val product = productRepository.findBySku(it.sku).orElseThrow()
                product.quantity -= it.quantity
                if (product.quantity < 0) {
                    logger.warn("ProductService#processInventory, no stock for $it")
                    throw RuntimeException("Product sku=$it has not enough stock")
                }
                productRepository.save(product)
                producer.sendConfirmation(request)
            }
        }
    }

}
