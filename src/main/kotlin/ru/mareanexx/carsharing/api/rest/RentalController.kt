package ru.mareanexx.carsharing.api.rest

import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.mareanexx.carsharing.domain.model.dto.*
import ru.mareanexx.carsharing.domain.service.RentalService
import ru.mareanexx.carsharing.support.exceptions.NoActiveRental

@RestController
@RequestMapping("/api/rental")
class RentalController(private val rentalService: RentalService) {

    @GetMapping("/active")
    fun getUserActiveRental(@RequestParam idUser: Int) : ResponseEntity<Rental?> {
        return try {
            ResponseEntity(rentalService.getActiveRental(idUser), HttpStatus.OK)
        } catch (e: NoActiveRental) {
            println(e.message)
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/history")
    fun getRentalHistory(@RequestParam idUser: Int) : ResponseEntity<List<RentalHistory>> {
        val rentalHistory = rentalService.getRentalHistory(idUser)
        println("rental History: $rentalHistory")
        if (rentalHistory.isNotEmpty())
            println("First car brand: ${rentalHistory[0].brand}")
        return ResponseEntity(rentalHistory, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun createNewRentalWithCar(@RequestBody newRental : NewRentalRequest): ResponseEntity<NewRentalResponse?> {
        return try {
            ResponseEntity(rentalService.createNewRentalWithCar(newRental), HttpStatus.CREATED)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("/complete")
    fun completeActiveRentalByUser(
        @RequestBody completedRental: CompletedRentalRequest
    ) : ResponseEntity<Map<String, String>> {
        return try {
            rentalService.completeActiveRentalByUser(completedRental)
            ResponseEntity(mapOf("success" to "Successfully completed active rental!"), HttpStatus.OK)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity(mapOf("bad" to "${e.message}"), HttpStatus.BAD_REQUEST)
        }
    }
}