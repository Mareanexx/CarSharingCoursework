package ru.mareanexx.carsharing.domain.model.dto

import java.math.BigDecimal

data class UpdateCarInfoDTO(
    val brand: String? = null,
    val model: String? = null,
    val licencePlate: String? = null,
    val fuelLevel: Int? = null,
    val fuelTankCapacity: Int? = null,
    val transmission: String? = null,
    val driveType: String? = null,
    val engineVolume: BigDecimal? = null,
    val enginePower: Int? = null,
    val heatedSeats: Boolean? = null,
    val heatedSteeringWheel: Boolean? = null,
    val parkingSensors: Boolean? = null,
    val touchScreen: Boolean? = null,
    val status: String? = null,
    val idLocation: Int? = null,
    val idRentalRates: Int? = null
)