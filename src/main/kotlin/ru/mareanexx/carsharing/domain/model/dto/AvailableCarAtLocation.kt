package ru.mareanexx.carsharing.domain.model.dto

import ru.mareanexx.carsharing.domain.model.type.DriveType
import ru.mareanexx.carsharing.domain.model.type.TransmissionType
import java.math.BigDecimal

data class AvailableCarAtLocation(
    val idCar: Int,
    val brand: String,
    val model: String,
    val licencePlate: String,
    val imagePath: String,
    val fuelLevel: Int,
    val fuelTankCapacity: Int,
    val transmission: TransmissionType, // transmission ENUM
    val driveType : DriveType, // driveType ENUM
    val engineVolume: BigDecimal,
    val enginePower: Int,
    val heatedSeats: Boolean,
    val heatedSteeringWheel: Boolean,
    val parkingSensors: Boolean,
    val touchScreen: Boolean,

    val pricePerHour: BigDecimal,
    val pricePerMinute: BigDecimal,
    val pricePerDay: BigDecimal
)