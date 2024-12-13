package ru.mareanexx.carsharing.domain.model.dto

import java.math.BigDecimal

data class RentalRate(
    val idRate: Int,
    val pricePerHour: BigDecimal,
    val pricePerMinute: BigDecimal,
    val pricePerDay: BigDecimal
)