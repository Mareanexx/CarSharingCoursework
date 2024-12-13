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
import ru.mareanexx.carsharing.api.model.request.RentalRatesPatchRequest
import ru.mareanexx.carsharing.api.model.request.RentalRatesRequest
import ru.mareanexx.carsharing.domain.model.dto.RentalRate
import ru.mareanexx.carsharing.domain.model.entity.RentalRatesEntity
import ru.mareanexx.carsharing.domain.service.RentalRatesService
import ru.mareanexx.carsharing.support.exceptions.WrongIDArgument

@RestController
@RequestMapping("/api/rates")
class RentalRatesController(private val rentalRatesService: RentalRatesService) {

    @GetMapping("/admin/list")
    fun getAllRentalRates() : ResponseEntity<List<RentalRatesEntity>> = ResponseEntity(rentalRatesService.getAllRentalRates(), HttpStatus.OK)

    @PostMapping("/admin/new")
    fun createNewRentalRates(@RequestBody rentalRatesRequest: RentalRatesRequest) : ResponseEntity<RentalRatesEntity?> {
        return try {
            ResponseEntity(rentalRatesService.addRentalRate(rentalRatesRequest), HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping("/admin/change")
    fun updateExistingRentalRate(
        @RequestParam idRate: Int,
        @RequestBody updatedRentalRate: RentalRatesPatchRequest
    ): ResponseEntity<Pair<String, RentalRatesEntity?>> {
        return try {
            val updatedEntity = rentalRatesService.updateRentalRate(idRate, updatedRentalRate)
            ResponseEntity(Pair("Updated successfully!", updatedEntity), HttpStatus.OK)
        } catch (e: WrongIDArgument) {
            ResponseEntity(Pair("${e.message}", null), HttpStatus.BAD_REQUEST)
        }
    }
}