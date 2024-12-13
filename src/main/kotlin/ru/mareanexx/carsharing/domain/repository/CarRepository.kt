package ru.mareanexx.carsharing.domain.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.mareanexx.carsharing.domain.model.dto.AvailableCarAtLocation
import ru.mareanexx.carsharing.domain.model.entity.CarEntity
import ru.mareanexx.carsharing.domain.model.type.CarStatus

@Repository
interface CarRepository : CrudRepository<CarEntity, Int> {

    @Query("""
        SELECT c.id_car, c.brand, c.model, c.licence_plate, c.image_path,
               c.fuel_level, c.fuel_tank_capacity, c.transmission, c.drive_type, 
               c.engine_volume, c.engine_power, c.heated_seats, c.heated_steering_wheel, 
               c.parking_sensors, c.touch_screen,
               rr.price_per_hour, rr.price_per_minute, rr.price_per_day
        FROM "car" c
        JOIN rental_rates rr ON c.id_rental_rates = rr.id_rate
        WHERE c.id_location = :idLocation AND c.status = :status
    """)
    fun findAllAvailableCarsAtLocation(
        @Param("idLocation") idLocation: Int,
        @Param("status") status: String
    ) : List<AvailableCarAtLocation>?
}