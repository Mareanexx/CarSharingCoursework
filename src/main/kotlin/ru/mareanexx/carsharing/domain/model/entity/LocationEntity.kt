package ru.mareanexx.carsharing.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Table(name = "location")
data class LocationEntity(
    @Id
    val idLocation: Int? = null,
    var name: String,
    var address: String,
    var latitude: BigDecimal,
    var longitude: BigDecimal,
    val createdAt: LocalDateTime
)