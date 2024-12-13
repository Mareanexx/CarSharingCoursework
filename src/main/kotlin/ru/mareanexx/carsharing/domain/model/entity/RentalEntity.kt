package ru.mareanexx.carsharing.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.mareanexx.carsharing.domain.model.type.RentalStatus
import ru.mareanexx.carsharing.domain.model.type.RentalType
import java.math.BigDecimal
import java.time.LocalDateTime


@Table(name = "rental")
data class RentalEntity(
    @Id
    val idRental: Int? = null,
    val rentalType: String, // MINUTE, HOUR, DAY
    val pricePerSmth: BigDecimal,
    val startTime: LocalDateTime,

    var endTime: LocalDateTime?,
    var totalPrice: BigDecimal?,
    var distance: Int?,
    var duration: Int?,

    val idCar: Int, // Foreign Key to car
    val idUser: Int, // Foreign Key to user
    var status: String = RentalStatus.ACTIVE.name // Enum: ACTIVE, COMPLETED
)