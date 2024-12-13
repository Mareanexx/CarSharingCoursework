package ru.mareanexx.carsharing.api.model.request

import java.math.BigDecimal

data class LocationRequest(
    val name: String,
    val address: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal
)