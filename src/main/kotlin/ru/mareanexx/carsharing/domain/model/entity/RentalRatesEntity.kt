package ru.mareanexx.carsharing.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal


@Table(name = "rental_rates")
data class RentalRatesEntity(
    @Id
    val idRate: Int? = null,
    var pricePerHour: BigDecimal,
    var pricePerMinute: BigDecimal,
    var pricePerDay: BigDecimal
)