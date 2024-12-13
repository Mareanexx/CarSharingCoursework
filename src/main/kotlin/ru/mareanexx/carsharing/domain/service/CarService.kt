package ru.mareanexx.carsharing.domain.service

import org.springframework.stereotype.Service
import ru.mareanexx.carsharing.api.model.request.NewCarRequest
import ru.mareanexx.carsharing.domain.model.dto.AvailableCarAtLocation
import ru.mareanexx.carsharing.domain.model.dto.UpdateCarInfoDTO
import ru.mareanexx.carsharing.domain.model.entity.CarEntity
import ru.mareanexx.carsharing.domain.model.type.CarStatus
import ru.mareanexx.carsharing.domain.model.type.DriveType
import ru.mareanexx.carsharing.domain.model.type.TransmissionType
import ru.mareanexx.carsharing.domain.repository.CarRepository
import ru.mareanexx.carsharing.domain.repository.LocationRepository
import ru.mareanexx.carsharing.domain.repository.RentalRatesRepository
import ru.mareanexx.carsharing.support.exceptions.NoCarsAvailableAtLocation
import ru.mareanexx.carsharing.support.exceptions.WrongIDArgument
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class CarService(
    private val carRepository: CarRepository,
    private val rentalRatesRepository: RentalRatesRepository,
    private val locationRepository: LocationRepository
) {
    /*
        Получение всех авто по выбранной локации.
        Для пользователя.
        На главном экране.
    */
    fun getAllAvailableCarsAtLocation(idLocation: Int) : List<AvailableCarAtLocation> {
        require(locationRepository.findById(idLocation).getOrNull() != null) {
            throw WrongIDArgument("Location with `$idLocation` not found")
        }
        return carRepository.findAllAvailableCarsAtLocation(idLocation, CarStatus.AVAILABLE.name).orEmpty()
    }

    /*
        Получение ВСЕЙ информации об одной машине по ее ID
    */


    /*
        Добавление нового авто с привязкой к Локации и Тарифу
        Для админ панели.
     */
    fun addNewCarWithLocation(newCar: NewCarRequest) : CarEntity {
        require(locationRepository.findById(newCar.idLocation).getOrNull() != null) {
            throw WrongIDArgument("Wrong id_location was found `${newCar.idLocation}`")
        }
        require(rentalRatesRepository.findById(newCar.idRentalRates).getOrNull() != null) {
            throw WrongIDArgument("Wrong id_rental_rates was found `${newCar.idRentalRates}`")
        }
        val newCarEntity = CarEntity(
            brand = newCar.brand,
            model = newCar.model,
            licencePlate = newCar.licencePlate,
            imagePath = newCar.imagePath,
            fuelLevel = newCar.fuelLevel,
            fuelTankCapacity = newCar.fuelTankCapacity,
            transmission = newCar.transmission,
            driveType = newCar.driveType,
            engineVolume = newCar.engineVolume,
            enginePower = newCar.enginePower,
            heatedSeats = newCar.heatedSeats,
            heatedSteeringWheel = newCar.heatedSteeringWheel,
            parkingSensors = newCar.parkingSensors,
            touchScreen = newCar.touchScreen,
            idLocation = newCar.idLocation,
            idRentalRates = newCar.idRentalRates,
            createdAt = LocalDateTime.now()
        )
        return carRepository.save(newCarEntity)
    }

    /*
        Удаление автомобиля по его ID
        Для админ панели.
     */
    fun deleteCarByID(idCar: Int) : CarEntity {
        if (!carRepository.existsById(idCar)) {
            throw WrongIDArgument("Car with ID $idCar not found")
        }
        val foundCar = carRepository.findById(idCar).get()
        carRepository.deleteById(idCar)
        return foundCar
    }

    /*
        Обновление данных об автомобиле по его ID
        Для админ панели.
    */
    fun updateCarInformation(idCar: Int, updateCarInfoDTO: UpdateCarInfoDTO): CarEntity {
        val car = carRepository.findById(idCar).orElseThrow {
            throw WrongIDArgument("Car with ID $idCar not found")
        }

        // Обновляем только те поля, которые переданы в DTO
        updateCarInfoDTO.brand?.let { car.brand = it }
        updateCarInfoDTO.model?.let { car.model = it }
        updateCarInfoDTO.licencePlate?.let { car.licencePlate = it }
        updateCarInfoDTO.fuelLevel?.let { car.fuelLevel = it }
        updateCarInfoDTO.fuelTankCapacity?.let { car.fuelTankCapacity = it }
        updateCarInfoDTO.transmission?.let { car.transmission = it }
        updateCarInfoDTO.driveType?.let { car.driveType = it }
        updateCarInfoDTO.engineVolume?.let { car.engineVolume = it }
        updateCarInfoDTO.enginePower?.let { car.enginePower = it }
        updateCarInfoDTO.heatedSeats?.let { car.heatedSeats = it }
        updateCarInfoDTO.heatedSteeringWheel?.let { car.heatedSteeringWheel = it }
        updateCarInfoDTO.parkingSensors?.let { car.parkingSensors = it }
        updateCarInfoDTO.touchScreen?.let { car.touchScreen = it }
        updateCarInfoDTO.status?.let { car.status = it }
        updateCarInfoDTO.idLocation?.let { car.idLocation = it }
        updateCarInfoDTO.idRentalRates?.let { car.idRentalRates = it }

        return carRepository.save(car)
    }
}