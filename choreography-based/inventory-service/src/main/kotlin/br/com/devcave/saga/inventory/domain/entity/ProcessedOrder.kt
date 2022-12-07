package br.com.devcave.saga.inventory.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
data class ProcessedOrder(
    @Id
    val id: Long,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)
