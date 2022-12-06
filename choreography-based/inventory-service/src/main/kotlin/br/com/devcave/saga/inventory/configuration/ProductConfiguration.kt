package br.com.devcave.saga.inventory.configuration

import br.com.devcave.saga.inventory.domain.entity.Product
import br.com.devcave.saga.inventory.repository.ProductRepository
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrElse

@Configuration
class ProductConfiguration(private val productRepository: ProductRepository) {
    @OptIn(ExperimentalStdlibApi::class)
    @Transactional
    @PostConstruct
    fun create() {
        skuList.forEach {
            productRepository.findBySku(it).getOrElse {
                productRepository.save(Product(sku = it, quantity = 10))
            }
        }
    }

    companion object {
        val skuList = listOf("SKU1", "SKU2", "SKU3")
    }
}