package ru.mareanexx.carsharing.domain.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mareanexx.carsharing.domain.model.entity.LocationEntity

@Repository
interface LocationRepository : CrudRepository<LocationEntity, Int> {
}