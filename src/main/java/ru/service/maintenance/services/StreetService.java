package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.service.maintenance.entyties.District;
import ru.service.maintenance.entyties.Street;
import ru.service.maintenance.exceptions.InvalidParamsException;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.repositories.StreetRepository;

import java.util.Collection;
import java.util.List;
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

    public List<Street> findAllByDistrictId(List<Long> idDistrict) {
        if (idDistrict == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        return streetRepository.findAllByDistrictId(idDistrict);
    }

    @Transactional
    public void changeStreet(String title, Long id) {
        if (title == null || id == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        try {
            streetRepository.changeStreet(title, id);
            streetRepository.changeUpdateAt(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Ошибка изменения улицы. Улица " + id + "не существует");
        }

    }
}