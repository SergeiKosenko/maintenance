package ru.service.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.service.maintenance.entyties.District;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("select ba from District ba where ba.regiones.id in :idRegiones")
    List<District> findAllByRegionesId(@Param("idRegiones") List<Long> idRegiones);

}

