package ru.mareanexx.carsharing.domain.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mareanexx.carsharing.domain.model.entity.RentalRatesEntity

@Repository
interface RentalRatesRepository : CrudRepository<RentalRatesEntity, Int> {

    @Query("""
        SELECT * FROM rental_rates
    """)
    fun findAllRentalRates() : List<RentalRatesEntity>?
}