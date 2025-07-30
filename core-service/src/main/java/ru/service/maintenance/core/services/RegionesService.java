package ru.service.maintenance.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.service.maintenance.core.dtos.RegionesDto;
import ru.service.maintenance.core.entyties.Regiones;
import ru.service.maintenance.core.exceptions.InvalidParamsException;
import ru.service.maintenance.core.exceptions.ResourceNotFoundException;
import ru.service.maintenance.core.repositories.RegionesRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionesService {
    private final RegionesRepository regionesRepository;

    public Optional<Regiones> FindById(Long id) {return regionesRepository.findById(id);}

    public Optional<Regiones> FindByTitle(String title) {return regionesRepository.findByTitle(title);}

    @Transactional
    public void deleteById(Long id) { regionesRepository.deleteById(id); }

    public void createNewRegiones(RegionesDto regionesDto) {
        Regiones regiones = new Regiones();
        regiones.setTitle(regionesDto.getTitle());
        regionesRepository.save(regiones);
    }

    public Collection<Regiones> findAll() {
        return regionesRepository.findAll();
    }

    @Transactional
    public void changeRegion(String title, Long id) {
        if (title == null || id == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        try {
            regionesRepository.changeRegiones(title, id);
            regionesRepository.changeUpdateAt(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Ошибка изменения региона. Регион " + id + "не существует");
        }

    }


}
