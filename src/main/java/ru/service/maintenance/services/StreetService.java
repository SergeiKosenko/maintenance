package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.maintenance.entyties.Street;
import ru.service.maintenance.repositories.StreetRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreetService {
    private final StreetRepository streetRepository;

    public Optional<Street> FindById(Long id) {
        return streetRepository.findById(id);
    }

    public void deleteById(Long id) { streetRepository.deleteById(id); }

    public Collection<Street> findAll() {
        return streetRepository.findAll();
    }
}