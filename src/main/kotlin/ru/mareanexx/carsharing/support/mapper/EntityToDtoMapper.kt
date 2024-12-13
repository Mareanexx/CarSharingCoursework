package ru.mareanexx.carsharing.support.mapper

import ru.mareanexx.carsharing.domain.model.dto.*
import ru.mareanexx.carsharing.domain.model.entity.*

fun LocationEntity.toDto() : Location = Location(
    idLocation = this.idLocation!!,
    name = this.name,
    address = this.address,
    latitude = this.latitude,
    longitude = this.longitude
)

fun RentalEntity.toDto(car: CarEntity) : NewRentalResponse = NewRentalResponse(
    idRental = idRental,
    rentalType = rentalType,
    pricePerSmth = pricePerSmth,
    startTime = startTime,
    // машина
    idCar = idCar,
    brand = car.brand,
    model = car.model,
    licencePlate = car.licencePlate,
    imagePath = car.imagePath
)