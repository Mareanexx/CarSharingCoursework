package ru.mareanexx.carsharing.api.rest

import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.mareanexx.carsharing.api.model.request.NewCarRequest
import ru.mareanexx.carsharing.domain.model.dto.AvailableCarAtLocation
import ru.mareanexx.carsharing.domain.model.dto.UpdateCarInfoDTO
import ru.mareanexx.carsharing.domain.model.entity.CarEntity
import ru.mareanexx.carsharing.domain.service.CarService
import ru.mareanexx.carsharing.support.exceptions.WrongIDArgument

@RestController
@RequestMapping("/api/car")
class CarController(private val carService: CarService) {

    @GetMapping("/loc-list")
    fun getAllAvailableCarsAtLocation(@RequestParam idLocation: Int) : ResponseEntity<List<AvailableCarAtLocation>?> {
        return try {
            ResponseEntity(carService.getAllAvailableCarsAtLocation(idLocation), HttpStatus.OK)
        } catch (e: WrongIDArgument) {
            println(e.message)
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/admin/create")
    fun addNewCarWithLocation(@RequestBody newCar: NewCarRequest) : ResponseEntity<CarEntity?> {
        return try {
            ResponseEntity(carService.addNewCarWithLocation(newCar), HttpStatus.OK)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/admin/delete")
    fun deleteCarById(@RequestParam idCar: Int) : ResponseEntity<CarEntity?> {
        return try {
            ResponseEntity(carService.deleteCarByID(idCar), HttpStatus.OK)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping("/admin/change")
    fun updateCarInformation(
        @RequestParam idCar: Int,
        @RequestBody updatedCar: UpdateCarInfoDTO
    ) : ResponseEntity<CarEntity?> {
        return try {
            ResponseEntity(carService.updateCarInformation(idCar, updatedCar), HttpStatus.OK)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }
}