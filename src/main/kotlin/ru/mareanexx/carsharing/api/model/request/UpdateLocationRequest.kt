package ru.mareanexx.carsharing.api.model.request

import java.math.BigDecimal

data class UpdateLocationRequest(
    val name: String? = null,
    val address: String? = null,
    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null
)