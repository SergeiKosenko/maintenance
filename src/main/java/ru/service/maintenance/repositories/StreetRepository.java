package ru.service.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.service.maintenance.entyties.Street;

import java.util.List;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {

    @Query("select ba from Street ba where ba.district.id in :idDistrict")
    List<Street> findAllByDistrictId(@Param("idDistrict") List <Long> idDistrict);

    @Modifying
    @Query(value = "update streets set title = ?1 where id = ?2", nativeQuery = true)
    void changeStreet(String title, Long id);

    @Modifying
    @Query("update Street u set u.updatedAt = CURRENT_TIMESTAMP where u.id = ?1")
    void changeUpdateAt(Long id);
}
