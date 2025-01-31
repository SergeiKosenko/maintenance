package ru.service.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.service.maintenance.entyties.District;
import ru.service.maintenance.entyties.Regiones;
import ru.service.maintenance.entyties.Street;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    Optional<District> findByTitle(String title);

    @Query("select ba from District ba where ba.regiones.id in :idRegiones")
    List<District> findAllByRegionesId(@Param("idRegiones") List<Long> idRegiones);

    @Modifying
    @Query(value = "update districts set title = ?1 where id = ?2", nativeQuery = true)
    void changeDistrict(String title, Long id);

    @Modifying
    @Query("update District u set u.updatedAt = CURRENT_TIMESTAMP where u.id = ?1")
    void changeUpdateAt(Long id);

}

