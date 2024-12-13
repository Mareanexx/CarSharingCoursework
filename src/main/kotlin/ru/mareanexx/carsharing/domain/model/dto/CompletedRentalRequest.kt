package ru.mareanexx.carsharing.domain.model.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class CompletedRentalRequest(
    val idRental: Int,
    // обновить поля
    val totalPrice: BigDecimal,
    val distance: Int,
    val duration: Int,
)