package ru.mareanexx.carsharing.domain.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.mareanexx.carsharing.api.model.request.LocationRequest
import ru.mareanexx.carsharing.api.model.request.UpdateLocationRequest
import ru.mareanexx.carsharing.domain.model.dto.Location
import ru.mareanexx.carsharing.domain.model.entity.LocationEntity
import ru.mareanexx.carsharing.domain.repository.LocationRepository
import ru.mareanexx.carsharing.support.exceptions.SameCoordinatesException
import ru.mareanexx.carsharing.support.exceptions.WrongIDArgument
import ru.mareanexx.carsharing.support.mapper.toDto
import java.time.LocalDateTime
import java.util.*

@Service
class LocationService(
    private val locationRepository: LocationRepository
) {

    /*
     * Получение всех точек для отображения на карте.
     * Для пользовательского UI.
     * Отправляется Location DTO.
     */
    fun getAllLocations() : List<Location> = locationRepository.findAll().map { it.toDto() }

    /*
     * Получение всех точек (полный список данных, без DTO).
     * Для админ-панели.
     */
    fun getAllLocationsForAdmin() : List<LocationEntity> = locationRepository.findAll().toList()

    /**
     * Добавление новой точки на карту.
     * Для админ-панели.
     */
    fun addNewLocation(location: LocationRequest): LocationEntity {
        // Проверка на уникальность координат
        val allLocations = getAllLocationsForAdmin()
        if (location.latitude in allLocations.map { it.latitude } &&
            location.longitude in allLocations.map { it.longitude }) {
            throw SameCoordinatesException("Trying to add new Location, but coordinates are the same:" +
                    "newLoc.latitude = ${location.latitude}, newLoc.longitude = ${location.longitude}")
        }
        val newLocationEntity = LocationEntity(
            name = location.name,
            address = location.address,
            longitude = location.longitude,
            latitude = location.latitude,
            createdAt = LocalDateTime.now()
        )

        return locationRepository.save(newLocationEntity)
    }

    /**
     * Изменение данных выбранной точки (по id_location).
     * Для админ панели.
     */
    fun updateLocation(idLocation: Int, updatedLocation: UpdateLocationRequest): LocationEntity {
        val existingLocation = locationRepository.findById(idLocation)
            .orElseThrow { WrongIDArgument("Location with ID $idLocation not found") }

        updatedLocation.name?.let { existingLocation.name = it }
        updatedLocation.address?.let { existingLocation.address = it }
        updatedLocation.latitude?.let { existingLocation.latitude = it }
        updatedLocation.longitude?.let { existingLocation.longitude = it }

        return locationRepository.save(existingLocation)
    }

    /**
     * Получение точки по ID.
     * Пока без применения.
     */
    fun getLocationById(idLocation: Int): Optional<LocationEntity> {
        return locationRepository.findById(idLocation)
    }

    /**
     * Удаление точки (например, если админ хочет удалить локацию)
     * Пока без применения.
     */
    fun deleteLocation(idLocation: Int) {
        if (!locationRepository.existsById(idLocation)) {
            throw IllegalArgumentException("Location with ID $idLocation not found")
        }
        locationRepository.deleteById(idLocation)
    }
}