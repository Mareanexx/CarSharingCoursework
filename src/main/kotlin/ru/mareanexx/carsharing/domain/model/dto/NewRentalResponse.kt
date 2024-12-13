package ru.mareanexx.carsharing.domain.model.dto

import ru.mareanexx.carsharing.domain.model.type.RentalType
import java.math.BigDecimal
import java.time.LocalDateTime

data class NewRentalResponse(
    val idRental: Int?,
    val rentalType: String,
    val pricePerSmth: BigDecimal,
    val startTime: LocalDateTime,
    // машина
    val idCar: Int,
    val brand: String,
    val model: String,
    val licencePlate: String,
    val imagePath: String
)