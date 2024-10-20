package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.maintenance.entyties.District;
import ru.service.maintenance.repositories.DistrictRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;

    public List<District> FindAll() {
        return districtRepository.findAll();
    }

    public Optional<District> FindById(Long id) {return districtRepository.findById(id);}

    public void deleteById(Long id) { districtRepository.deleteById(id); }


    public Collection<District> findAll() {
        return districtRepository.findAll();
    }
}
