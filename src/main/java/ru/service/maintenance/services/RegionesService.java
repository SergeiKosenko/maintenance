package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.maintenance.entyties.Regiones;
import ru.service.maintenance.repositories.RegionesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionesService {
    private final RegionesRepository regionesRepository;
    public List<Regiones> FindAll() {
        return regionesRepository.findAll();
    }
}
