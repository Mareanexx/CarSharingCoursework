package ru.mareanexx.carsharing.domain.service

import org.springframework.stereotype.Service
import ru.mareanexx.carsharing.domain.model.dto.*
import ru.mareanexx.carsharing.domain.model.entity.RentalEntity
import ru.mareanexx.carsharing.domain.model.type.CarStatus
import ru.mareanexx.carsharing.domain.model.type.RentalStatus
import ru.mareanexx.carsharing.domain.repository.CarRepository
import ru.mareanexx.carsharing.domain.repository.RentalRepository
import ru.mareanexx.carsharing.support.exceptions.NoActiveRental
import ru.mareanexx.carsharing.support.exceptions.WrongIDArgument
import ru.mareanexx.carsharing.support.mapper.toDto
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class RentalService(
    private val rentalRepository: RentalRepository,
    private val carRepository: CarRepository
) {
    /*
        Получить текущую аренду по ID-user и status = ACTIVE.
        Для пользователя.
    */
    fun getActiveRental(idUser: Int) : Rental {
        return rentalRepository.findActiveRentalAndCar(idUser, RentalStatus.ACTIVE.name) ?: throw NoActiveRental("User $idUser has no active rental")
    }

    /*
        Получение всех своих бронирований по id_user и status = COMPLETED.
        Для пользователей.
        DTO отправляется или пустой лист.
    */
    fun getRentalHistory(idUser: Int) : List<RentalHistory> {
        return rentalRepository.findAllCompletedRentalAndCar(idUser, RentalStatus.COMPLETED.name).orEmpty()
    }

    /*
        Создать новое бронирование авто по выбранным параметрам пользователя.
        Для пользователей.
     */
    fun createNewRentalWithCar(newRentalRequest: NewRentalRequest) : NewRentalResponse {
        val car = carRepository.findById(newRentalRequest.idCar).getOrNull()
        if (car == null) {
            throw WrongIDArgument("Car by ID = ${newRentalRequest.idCar} not found")
        }
        else {
            car.status = CarStatus.RENTED.name
            carRepository.save(car)
        }

        // idRental не указывается, оно будет сгенерировано
        val newRentalByUser = RentalEntity(
            rentalType = newRentalRequest.rentalType,
            pricePerSmth = newRentalRequest.pricePerSmth,
            startTime = LocalDateTime.now(),
            endTime = null,
            totalPrice = null,
            distance = null,
            duration = null,
            idCar = newRentalRequest.idCar,
            idUser = newRentalRequest.idUser,
            status = RentalStatus.ACTIVE.name
        )

        return rentalRepository.save(newRentalByUser).toDto(car)
    }


    /*
        Завершение текущей аренды для пользователя (по Id аренды).
        Для пользователя.
        Ничего не будет возвращать, в контроллере будет try блок,
        если хоть один exception, то отправляем на фронт, что не удалось завершить аренду
    */
    fun completeActiveRentalByUser(completedRental: CompletedRentalRequest) {
        val existedRental = rentalRepository.findActiveRentalToCompleteIt(completedRental.idRental) ?: throw WrongIDArgument("Rental with ID ${completedRental.idRental} not found")

        existedRental.apply {
            endTime = LocalDateTime.now()
            totalPrice = completedRental.totalPrice
            distance = completedRental.distance
            duration = completedRental.duration
            status = RentalStatus.COMPLETED.name // завершаю аренду
        }
        val car = carRepository.findById(existedRental.idCar).orElseThrow {
            throw WrongIDArgument("Car with ID ${existedRental.idCar} not found") }
        car.apply {
            status = CarStatus.AVAILABLE.name // машина становится доступной
        }
        carRepository.save(car)
        rentalRepository.save(existedRental)
    }
}