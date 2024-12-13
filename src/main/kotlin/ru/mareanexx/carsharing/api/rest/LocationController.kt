package ru.mareanexx.carsharing.api.rest

import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.mareanexx.carsharing.api.model.request.LocationRequest
import ru.mareanexx.carsharing.api.model.request.UpdateLocationRequest
import ru.mareanexx.carsharing.domain.model.dto.Location
import ru.mareanexx.carsharing.domain.model.entity.LocationEntity
import ru.mareanexx.carsharing.domain.service.LocationService
import ru.mareanexx.carsharing.support.exceptions.WrongIDArgument

@RestController
@RequestMapping("/api/location")
class LocationController(private val locationService: LocationService) {

    @GetMapping("/list")
    fun getAllLocations() : List<Location> = locationService.getAllLocations()

    @GetMapping("/admin/list")
    fun getAllLocationsAdmin() : List<LocationEntity> = locationService.getAllLocationsForAdmin()

    @PostMapping("/admin/new")
    fun addNewLocation(@RequestBody newLocation: LocationRequest) : ResponseEntity<LocationEntity?> {
        return try {
            ResponseEntity(locationService.addNewLocation(newLocation), HttpStatus.CREATED)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping("/admin/change")
    fun updateLocationInformation(
        @RequestParam idLocation: Int,
        @RequestBody updatedLocation: UpdateLocationRequest
    ) : ResponseEntity<LocationEntity?> {
        return try {
            ResponseEntity(locationService.updateLocation(idLocation, updatedLocation), HttpStatus.OK)
        } catch (e: WrongIDArgument) {
            println(e.message)
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }
}