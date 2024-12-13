package ru.mareanexx.carsharing.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.mareanexx.carsharing.domain.model.type.CarStatus
import ru.mareanexx.carsharing.domain.model.type.DriveType
import ru.mareanexx.carsharing.domain.model.type.TransmissionType
import java.math.BigDecimal
import java.time.LocalDateTime

@Table(name = "car")
data class CarEntity(
    @Id
    val idCar: Int? = null,
    var brand: String,
    var model: String,
    var licencePlate: String,
    val createdAt: LocalDateTime,
    var imagePath: String,
    var fuelLevel: Int,
    var fuelTankCapacity: Int,
    var transmission: String, // transmission, MANUAL AUTOMATIC
    var driveType : String, // driveType FWD, RWD, AWD
    var engineVolume: BigDecimal,
    var enginePower: Int,
    var heatedSeats: Boolean,
    var heatedSteeringWheel: Boolean,
    var parkingSensors: Boolean,
    var touchScreen: Boolean,
    var idLocation: Int, // Foreign Key to location
    var idRentalRates: Int // Foreign Key to rental_rates
) {
    var status : String = CarStatus.AVAILABLE.name  // status ENUM
}