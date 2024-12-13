package ru.mareanexx.carsharing.api.model.request

import java.math.BigDecimal

data class NewCarRequest(
    val brand: String,
    val model: String,
    val licencePlate: String,
    val imagePath: String,
    val fuelLevel: Int,
    val fuelTankCapacity: Int,
    val transmission: String, // transmission, MANUAL AUTOMATIC
    val driveType : String, // driveType FWD, RWD, AWD
    val engineVolume: BigDecimal,
    val enginePower: Int,
    val heatedSeats: Boolean,
    val heatedSteeringWheel: Boolean,
    val parkingSensors: Boolean,
    val touchScreen: Boolean,
    val idLocation: Int, // Foreign Key to location
    val idRentalRates: Int // Foreign Key to rental_rates
)