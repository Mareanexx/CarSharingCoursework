package ru.mareanexx.carsharing.domain.service

import org.springframework.stereotype.Service
import ru.mareanexx.carsharing.api.model.request.RentalRatesPatchRequest
import ru.mareanexx.carsharing.api.model.request.RentalRatesRequest
import ru.mareanexx.carsharing.domain.model.dto.RentalRate
import ru.mareanexx.carsharing.domain.model.entity.RentalRatesEntity
import ru.mareanexx.carsharing.domain.repository.RentalRatesRepository
import ru.mareanexx.carsharing.support.exceptions.WrongIDArgument
import ru.mareanexx.carsharing.support.mapper.toDto
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class RentalRatesService(
    private val rentalRatesRepository: RentalRatesRepository
) {
    /*
     * Получение тарифного плана по ID
     * Пока с DTO
     */
//    fun getRentalRatesById(idRentalRate: Int): RentalRate? {
//        return rentalRatesRepository.findById(idRentalRate).getOrNull()?.toDto()
//    }

    /*
     Получение всех тарифных планов (для администратора).
     Без DTO
     */
    fun getAllRentalRates(): List<RentalRatesEntity> {
        return rentalRatesRepository.findAllRentalRates().orEmpty()
    }

    /*
     Добавление нового тарифа
     Для админ-панели
     */
    fun addRentalRate(newRentalRates: RentalRatesRequest): RentalRatesEntity {
        val newRentalRatesEntity = RentalRatesEntity(
            pricePerMinute = newRentalRates.pricePerMinute,
            pricePerDay = newRentalRates.pricePerDay,
            pricePerHour = newRentalRates.pricePerHour
        )
        return rentalRatesRepository.save(newRentalRatesEntity)
    }

    /*
     Изменение существующего тарифа.
     Для администратора.
     */
    fun updateRentalRate(idRate: Int, updatedRate: RentalRatesPatchRequest): RentalRatesEntity {
        val existingRate = rentalRatesRepository.findById(idRate)
            .orElseThrow { WrongIDArgument("Rental rate with ID $idRate not found") }

        // обновляются только не null
        updatedRate.pricePerHour?.let { existingRate.pricePerHour = it }
        updatedRate.pricePerMinute?.let { existingRate.pricePerMinute = it }
        updatedRate.pricePerDay?.let { existingRate.pricePerDay = it }

        return rentalRatesRepository.save(existingRate)
    }
}