package ru.mareanexx.carsharing.domain.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.mareanexx.carsharing.domain.model.dto.Rental
import ru.mareanexx.carsharing.domain.model.dto.RentalHistory
import ru.mareanexx.carsharing.domain.model.entity.RentalEntity
import ru.mareanexx.carsharing.domain.model.type.RentalStatus

@Repository
interface RentalRepository : CrudRepository<RentalEntity, Int> {

    @Query("""
        SELECT r.id_rental, r.rental_type, r.price_per_smth, r.start_time, r.id_car,
            c.brand, c.model, c.licence_plate, c.image_path,
            c.fuel_level, c.fuel_tank_capacity
        FROM rental r
        JOIN car c ON r.id_car = c.id_car
        WHERE r.id_user = :idUser AND r.status = :status;
    """)
    fun findActiveRentalAndCar(
        @Param("idUser") idUser: Int,
        @Param("status") status: String
    ) : Rental?


    @Query("""
        SELECT r.start_time, r.total_price,
            c.brand, c.model
        FROM rental r
        JOIN car c ON r.id_car = c.id_car
        WHERE r.id_user = :idUser AND r.status = :status;
    """)
    fun findAllCompletedRentalAndCar(
        @Param("idUser") idUser: Int,
        @Param("status") status: String
    ) : List<RentalHistory>?

    @Query("""
        SELECT * FROM rental
        WHERE rental.id_rental = :idRental
    """)
    fun findActiveRentalToCompleteIt(@Param("idRental") idRental: Int) : RentalEntity?
}