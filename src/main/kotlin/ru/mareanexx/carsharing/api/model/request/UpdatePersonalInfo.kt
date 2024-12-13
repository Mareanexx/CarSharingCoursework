package ru.mareanexx.carsharing.api.model.request

import java.time.LocalDate
import java.time.LocalDateTime

data class UpdatePersonalInfo(
    val firstName: String? = null,
    val lastName: String? = null,
    val passportNumber: Int? = null,
    val passportIssueDate: LocalDate? = null,
    val passportIssuedBy: String? = null,
    val dlNumber: String? = null, // Driver’s License Number
    val dlIssueDate: LocalDate? = null,
    val dlExpiryDate: LocalDate? = null,
)