package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.maintenance.entyties.Manufacture;
import ru.service.maintenance.repositories.ManufactureRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManufactureService {

    private final ManufactureRepository manufactureRepository;

    public Collection<Manufacture> findAll() {
        return manufactureRepository.findAll();
    }


    public Optional<Manufacture> FindById(Long id) {
        return manufactureRepository.findById(id);
    }
}
