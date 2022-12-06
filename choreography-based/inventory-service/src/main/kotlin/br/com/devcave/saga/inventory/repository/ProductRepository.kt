package br.com.devcave.saga.inventory.repository

import br.com.devcave.saga.inventory.domain.entity.Product
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface ProductRepository : CrudRepository<Product, Long> {
    fun findBySku(sku: String): Optional<Product>
}
