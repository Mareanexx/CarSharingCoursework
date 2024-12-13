package ru.mareanexx.carsharing.domain.model.dto

import ru.mareanexx.carsharing.domain.model.type.RentalType
import java.math.BigDecimal
import java.time.LocalDateTime

data class NewRentalRequest(
    val rentalType: String,
    val pricePerSmth: BigDecimal,
    val idCar: Int, // Foreign Key to car
    val idUser: Int // Foreign Key to user
)