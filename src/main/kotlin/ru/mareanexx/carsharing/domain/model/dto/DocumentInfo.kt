package ru.mareanexx.carsharing.domain.model.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class DocumentInfo(
    val passportNumber: Int,
    val passportIssueDate: LocalDate,
    val passportIssuedBy: String,
    val dlNumber: String, // Driver’s License Number
    val dlIssueDate: LocalDate,
    val dlExpiryDate: LocalDate,
    val updatedAt: LocalDateTime
)