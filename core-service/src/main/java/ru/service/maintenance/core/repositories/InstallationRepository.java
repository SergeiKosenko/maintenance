package ru.service.maintenance.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.maintenance.core.entyties.Installation;

@Repository
public interface InstallationRepository extends JpaRepository<Installation, Long> {
}
