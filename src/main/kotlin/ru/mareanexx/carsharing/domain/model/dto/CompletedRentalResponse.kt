package ru.mareanexx.carsharing.domain.model.dto

import ru.mareanexx.carsharing.domain.model.type.RentalType
import java.math.BigDecimal
import java.time.LocalDateTime

// пока не используется
data class CompletedRentalResponse(
    val rentalType: RentalType,
    val pricePerSmth: BigDecimal,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val totalPrice: BigDecimal,
    val distance: Int,
    val duration: Int,
    // про машину
    val brand: String,
    val model: String,
    var licencePlate: String,
    var imagePath: String
)