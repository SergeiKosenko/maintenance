package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.service.maintenance.dtos.RegionesDto;
import ru.service.maintenance.entyties.District;
import ru.service.maintenance.entyties.Regiones;
import ru.service.maintenance.exceptions.InvalidParamsException;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
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

    @Transactional
    @Cascade(CascadeType.ALL)
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
