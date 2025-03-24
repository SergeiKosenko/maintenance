package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.service.maintenance.dtos.DistrictDto;
import ru.service.maintenance.entyties.District;
import ru.service.maintenance.entyties.Regiones;
import ru.service.maintenance.exceptions.InvalidParamsException;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.repositories.DistrictRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final RegionesService regionesService;

    public List<District> findAllByRegionesId(Long idRegiones) {
        if (idRegiones == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        return districtRepository.findAllByRegionesId(idRegiones);
    }

    public Optional<District> FindById(Long id) {return districtRepository.findById(id);}
    public Optional<District> FindByTitle(String title) {return districtRepository.findByTitle(title);}

    public void deleteById(Long id) { districtRepository.deleteById(id); }

    public Collection<District> findAll() {
        return districtRepository.findAll();
    }


    public void createNewDistrict(DistrictDto districtDto) {
        District district = new District();
        district.setRegiones(regionesService.FindByTitle(districtDto.getRegionesTitle()).get());
        district.setTitle(districtDto.getTitle());
        districtRepository.save(district);
    }

    @Transactional
    public void changeDistrict(String title, Long id) {
        if (title == null || id == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        try {
            districtRepository.changeDistrict(title, id);
            districtRepository.changeUpdateAt(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Ошибка изменения района. Район " + id + "не существует");
        }

    }

}
