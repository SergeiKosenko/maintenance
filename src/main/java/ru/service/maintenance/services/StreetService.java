package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.maintenance.entyties.Street;
import ru.service.maintenance.repositories.StreetRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StreetService {
    private final StreetRepository streetRepository;

    public List<Street> FindAll() {
        return streetRepository.findAll();
    }
}