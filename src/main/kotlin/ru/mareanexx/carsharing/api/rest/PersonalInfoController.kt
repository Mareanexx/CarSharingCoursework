package ru.mareanexx.carsharing.api.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.mareanexx.carsharing.api.model.request.NewPersonalInfo
import ru.mareanexx.carsharing.api.model.request.UpdatePersonalInfo
import ru.mareanexx.carsharing.domain.model.dto.DocumentInfo
import ru.mareanexx.carsharing.domain.model.dto.PersonalInfo
import ru.mareanexx.carsharing.domain.service.PersonalInfoService
import ru.mareanexx.carsharing.support.exceptions.WrongIDArgument

@RestController
@RequestMapping("/api/pers-info")
class PersonalInfoController(private val personalInfoService: PersonalInfoService) {

    @GetMapping
    fun getPersonalInformation(@RequestParam idUser: Int) : ResponseEntity<PersonalInfo?> {
        return try {
            ResponseEntity(personalInfoService.getPersonalInfo(idUser), HttpStatus.OK)
        } catch (e: WrongIDArgument) {
            println(e.message)
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/docs")
    fun getDocumentInformation(@RequestParam idUser: Int) : ResponseEntity<DocumentInfo?> {
        return try {
            ResponseEntity(personalInfoService.getDocumentInfo(idUser), HttpStatus.OK)
        } catch (e: WrongIDArgument) {
            println(e.message)
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/set")
    fun setNewUserPersonalInfo(@RequestBody newPersonalInfo: NewPersonalInfo) : ResponseEntity<Map<String, String>> {
        return try {
            ResponseEntity(personalInfoService.setToNewUserPersonalInfo(newPersonalInfo), HttpStatus.CREATED)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity(mapOf("bad" to "${e.message}"), HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping("/change")
    fun changePersonalInformation(
        @RequestParam idUser: Int,
        @RequestBody updatedInfo: UpdatePersonalInfo
    ) : ResponseEntity<Map<String, String>> {
        return try {
            personalInfoService.changePersonalInformation(idUser, updatedInfo)
            ResponseEntity(mapOf("success" to "Personal Info successfully changed!"), HttpStatus.OK)
        } catch (e: WrongIDArgument) {
            println(e.message)
            ResponseEntity(mapOf("bad" to "Personal Info cant be changed!"), HttpStatus.BAD_REQUEST)
        }
    }
}