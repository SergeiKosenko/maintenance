package ru.service.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.service.maintenance.entyties.Regiones;

import java.util.Optional;

@Repository
public interface RegionesRepository extends JpaRepository<Regiones, Long> {
    Optional<Regiones> findByTitle(String title);

    @Modifying
    @Query(value = "update regiones set title = ?1 where id = ?2", nativeQuery = true)
    void changeRegiones(String title, Long id);

    @Modifying
    @Query("update Regiones u set u.updatedAt = CURRENT_TIMESTAMP where u.id = ?1")
    void changeUpdateAt(Long id);
}
