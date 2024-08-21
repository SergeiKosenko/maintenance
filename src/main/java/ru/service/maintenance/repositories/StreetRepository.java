package ru.service.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.maintenance.entyties.Street;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {
}
