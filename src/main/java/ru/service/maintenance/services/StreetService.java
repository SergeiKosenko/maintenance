package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.maintenance.entyties.Street;
import ru.service.maintenance.repositories.RegionesRepository;
import ru.service.maintenance.repositories.StreetRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreetService {
    private final StreetRepository streetRepository;

    //public Collection<Street> FindAll() {return streetRepository.findAll();}

    public Optional<Street> FindById(Long id) {
        return streetRepository.findById(id);
    }

    public void deleteById(Long id) { streetRepository.deleteById(id); }

    public Collection<Street> findAll() {
        return streetRepository.findAll();
    }
}