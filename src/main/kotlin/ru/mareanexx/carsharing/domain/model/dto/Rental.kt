package ru.mareanexx.carsharing.domain.model.dto

import ru.mareanexx.carsharing.domain.model.type.RentalType
import java.math.BigDecimal
import java.time.LocalDateTime

data class Rental(
    // данные об аренде
    val idRental: Int,
    val rentalType: RentalType,
    val pricePerSmth: BigDecimal,
    val startTime: LocalDateTime,
    // данные об авто
    val idCar: Int,
    val brand: String,
    val model: String,
    val licencePlate: String,
    val imagePath: String,
    val fuelLevel: Int,
    val fuelTankCapacity: Int,
)