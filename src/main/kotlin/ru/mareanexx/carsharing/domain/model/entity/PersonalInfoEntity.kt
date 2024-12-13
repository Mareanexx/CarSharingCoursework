package ru.mareanexx.carsharing.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.LocalDateTime


@Table(name = "personal_info")
data class PersonalInfoEntity(
    @Id
    val idInfo: Int? = null,
    val firstName: String,
    val lastName: String,
    var passportNumber: Int,
    var passportIssueDate: LocalDate,
    var passportIssuedBy: String,
    var dlNumber: String, // Driver’s License Number
    var dlIssueDate: LocalDate,
    var dlExpiryDate: LocalDate,
    var updatedAt: LocalDateTime,
    val idUser: Int // Foreign Key to user
)