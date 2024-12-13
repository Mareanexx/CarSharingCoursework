package ru.mareanexx.carsharing

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@EnableJdbcRepositories
@SpringBootApplication(scanBasePackages = ["ru.mareanexx.carsharing"])
class CarSharingApplication

fun main(args: Array<String>) {
	val context = SpringApplication.run(CarSharingApplication::class.java, *args)
	println("Application started successfully!")
}
