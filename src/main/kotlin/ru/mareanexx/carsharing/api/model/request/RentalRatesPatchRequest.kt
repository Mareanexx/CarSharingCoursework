package ru.mareanexx.carsharing.api.model.request

import java.math.BigDecimal

data class RentalRatesPatchRequest(
    var pricePerHour: BigDecimal? = null,
    var pricePerMinute: BigDecimal? = null,
    var pricePerDay: BigDecimal? = null
)