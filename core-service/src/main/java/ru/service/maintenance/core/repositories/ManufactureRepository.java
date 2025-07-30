package ru.service.maintenance.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.maintenance.core.entyties.Manufacture;

@Repository
public interface ManufactureRepository extends JpaRepository<Manufacture, Long> {

}
