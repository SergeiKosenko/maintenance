package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.maintenance.dtos.RegionesDto;
import ru.service.maintenance.entyties.District;
import ru.service.maintenance.entyties.Regiones;
import ru.service.maintenance.repositories.RegionesRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionesService {
    private final RegionesRepository regionesRepository;

    public List<Regiones> FindAll() {
        return regionesRepository.findAll();
    }

    public Optional<Regiones> FindById(Long id) {return regionesRepository.findById(id);}

    public Optional<Regiones> FindByTitle(String title) {return regionesRepository.findByTitle(title);}

    public void deleteById(Long id) { regionesRepository.deleteById(id); }

    public void createNewRegiones(RegionesDto regionesDto) {
        Regiones regiones = new Regiones();
        regiones.setTitle(regionesDto.getTitle());
        regionesRepository.save(regiones);
    }

    public Collection<Regiones> findAll() {
        return regionesRepository.findAll();
    }


}
