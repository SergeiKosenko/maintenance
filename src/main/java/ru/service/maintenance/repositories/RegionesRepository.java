package ru.service.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import ru.service.maintenance.entyties.Regiones;


@Repository
public interface RegionesRepository extends JpaRepository<Regiones, Long> {
    Optional<Regiones> findByTitle(String title);
}
