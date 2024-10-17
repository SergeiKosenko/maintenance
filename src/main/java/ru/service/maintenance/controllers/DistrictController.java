package ru.service.maintenance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.maintenance.converters.DistrictConverter;
import ru.service.maintenance.dtos.DistrictDto;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.services.DistrictService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/districts")
@RequiredArgsConstructor
public class DistrictController {
    private final DistrictService districtService;
    private final DistrictConverter districtConverter;

    @GetMapping
    public List<DistrictDto> getAllDistricts() {
        return districtService.findAll().stream().map(districtConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DistrictDto getDistrictById(@PathVariable Long id){
        return districtConverter.entityToDto(districtService.FindById(id).orElseThrow(() -> new ResourceNotFoundException("Район с ID: " +id+ " не найден")));
    }
}
