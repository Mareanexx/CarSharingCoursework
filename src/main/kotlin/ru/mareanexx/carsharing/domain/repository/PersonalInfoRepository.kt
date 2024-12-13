package ru.mareanexx.carsharing.domain.repository
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.mareanexx.carsharing.domain.model.dto.DocumentInfo
import ru.mareanexx.carsharing.domain.model.dto.PersonalInfo
import ru.mareanexx.carsharing.domain.model.entity.PersonalInfoEntity

@Repository
interface PersonalInfoRepository : CrudRepository<PersonalInfoEntity, Int> {

    @Query("""
    SELECT username, email, phone_number
    FROM "user"
    WHERE id_user = :idUser
    """)
    fun getUserInformation(@Param("idUser") idUser: Int) : PersonalInfo?

    @Query("""
    SELECT pi.passport_number, 
           pi.passport_issue_date, 
           pi.passport_issued_by,
           pi.dl_number, 
           pi.dl_issue_date, 
           pi.dl_expiry_date, 
           pi.updated_at
    FROM personal_info pi
    WHERE pi.id_user = :idUser
    """)
    fun getUserDocumentInformation(@Param("idUser") idUser: Int) : DocumentInfo?

    @Query("""
        SELECT * FROM personal_info
        WHERE personal_info.id_user = :idUser;
    """)
    fun findByUserId(@Param("idUser") idUser: Int) : PersonalInfoEntity?
}
