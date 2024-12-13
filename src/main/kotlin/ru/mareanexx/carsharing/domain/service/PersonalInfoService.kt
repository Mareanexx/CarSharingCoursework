package ru.mareanexx.carsharing.domain.service

import org.springframework.stereotype.Service
import ru.mareanexx.carsharing.api.model.request.NewPersonalInfo
import ru.mareanexx.carsharing.api.model.request.UpdatePersonalInfo
import ru.mareanexx.carsharing.domain.model.dto.DocumentInfo
import ru.mareanexx.carsharing.domain.model.dto.PersonalInfo
import ru.mareanexx.carsharing.domain.model.entity.PersonalInfoEntity
import ru.mareanexx.carsharing.domain.repository.PersonalInfoRepository
import ru.mareanexx.carsharing.support.exceptions.WrongIDArgument
import java.time.LocalDateTime

@Service
class PersonalInfoService(
    private val personalInfoRepository: PersonalInfoRepository
) {
    /*
        Получение персональной информации для пользователя:
        Имя + фамилия, email, phone_number.
        Используется в панели "Настройки".
     */
    fun getPersonalInfo(idUser: Int): PersonalInfo {
        val personalInfo = personalInfoRepository.getUserInformation(idUser)
        return personalInfo ?: throw WrongIDArgument("No personal information found for user ID '$idUser'")
    }

    /*
        Получение информации о документах пользователя:
        Паспорт + ВУ.
        Используется в панели "Документы".
    */
    fun getDocumentInfo(idUser: Int): DocumentInfo {
        val documentInfo = personalInfoRepository.getUserDocumentInformation(idUser)
        return documentInfo ?: throw WrongIDArgument("No document information found for user ID '$idUser'")
    }

    /*
        Изменение личной информации пользователя
        Для пользователя.
        Используется в панели настройки и документы.
     */
    fun changePersonalInformation(idUser: Int, updatedPersonalInfo: UpdatePersonalInfo) {
        val existingPersonalInfo = personalInfoRepository.findByUserId(idUser)
        check(existingPersonalInfo != null) {
            throw WrongIDArgument("No personal info on this id_user $idUser")
        }
        println("$updatedPersonalInfo")
        updatedPersonalInfo.passportNumber?.let { existingPersonalInfo.passportNumber = it }
        updatedPersonalInfo.passportIssueDate?.let { existingPersonalInfo.passportIssueDate = it }
        updatedPersonalInfo.passportIssuedBy?.let { existingPersonalInfo.passportIssuedBy = it }
        updatedPersonalInfo.dlExpiryDate?.let { existingPersonalInfo.dlExpiryDate = it }
        updatedPersonalInfo.dlNumber?.let { existingPersonalInfo.dlNumber = it }
        updatedPersonalInfo.dlIssueDate?.let { existingPersonalInfo.dlIssueDate = it }
        existingPersonalInfo.updatedAt = LocalDateTime.now()

        personalInfoRepository.save(existingPersonalInfo)
    }

    /*
        Добавление персональной информации, вызывается только один раз при
        первом добавлении.
        Для пользователя.
        Возврат DTO??
    */
    fun setToNewUserPersonalInfo(newPersonalInfo: NewPersonalInfo) : Map<String, String> {
        val existedUserInfo = personalInfoRepository.findByUserId(newPersonalInfo.idUser)
        val newPersinalInfoEntity = PersonalInfoEntity(
            firstName = newPersonalInfo.firstName,
            lastName = newPersonalInfo.lastName,
            passportNumber = newPersonalInfo.passportNumber,
            passportIssuedBy = newPersonalInfo.passportIssuedBy,
            passportIssueDate = newPersonalInfo.passportIssueDate,
            dlNumber = newPersonalInfo.dlNumber,
            dlIssueDate = newPersonalInfo.dlIssueDate,
            dlExpiryDate = newPersonalInfo.dlExpiryDate,
            updatedAt = LocalDateTime.now(),
            idUser = newPersonalInfo.idUser
        )

        // по факту должно быть всегда ложно
        if (existedUserInfo == null) {
            personalInfoRepository.save(newPersinalInfoEntity)
            return mapOf("success" to "Saved, information with id_user ${newPersonalInfo.idUser}")
        }
        else throw IllegalStateException("For some reasons the PersInfo of user already exists, and WTF??")
    }
}