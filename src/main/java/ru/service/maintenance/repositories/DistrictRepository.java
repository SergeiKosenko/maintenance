package ru.service.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.maintenance.entyties.District;

    @Repository
    public interface DistrictRepository extends JpaRepository<District, Long> {
    }

