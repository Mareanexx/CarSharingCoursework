package ru.mareanexx.carsharing.api.model.request

import java.math.BigDecimal

data class RentalRatesRequest(
    var pricePerHour: BigDecimal,
    var pricePerMinute: BigDecimal,
    var pricePerDay: BigDecimal
)