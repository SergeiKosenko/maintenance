package ru.service.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.maintenance.entyties.WorkSite;

@Repository
public interface WorkSiteRepository extends JpaRepository<WorkSite, Long> {
}
