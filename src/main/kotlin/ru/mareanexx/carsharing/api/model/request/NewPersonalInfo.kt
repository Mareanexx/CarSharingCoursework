package ru.mareanexx.carsharing.api.model.request

import java.time.LocalDate

data class NewPersonalInfo(
    val firstName: String,
    val lastName: String,
    val passportNumber: Int,
    val passportIssueDate: LocalDate,
    val passportIssuedBy: String,
    val dlNumber: String, // Driver’s License Number
    val dlIssueDate: LocalDate,
    val dlExpiryDate: LocalDate,
    val idUser: Int // Foreign Key to user
)