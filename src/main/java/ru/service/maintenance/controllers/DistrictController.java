package ru.service.maintenance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.service.maintenance.converters.DistrictConverter;
import ru.service.maintenance.dtos.DistrictDto;
import ru.service.maintenance.entyties.District;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.services.DistrictService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/districts")
@RequiredArgsConstructor
public class DistrictController {
    private final DistrictService districtService;
    private final DistrictConverter districtConverter;

    @GetMapping("/all")
    public List<DistrictDto> getAllDistricts() {
        return districtService.findAll().stream().map(districtConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/regionid/{regionId}")
    public List<DistrictDto> getDistrictByRegionId(@PathVariable List<Long> regionId){
        List<DistrictDto> list = new ArrayList<>();
        for (District district : districtService.findAllByRegionesId(regionId)) {
            DistrictDto districtDto = districtConverter.entityToDto(district);
            list.add(districtDto);
        }
        return list;
    }

    @GetMapping("/{id}")
    public DistrictDto getDistrictById(@PathVariable Long id){
        return districtConverter.entityToDto(districtService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Район с ID: " +id+ " не найден")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewDistrict(@RequestBody DistrictDto districtDto) {
        districtService.createNewDistrict(districtDto);
    }

    @PatchMapping("/{id}")
    public void changeDistrict(@RequestParam String title, @PathVariable Long id) {
        districtService.changeDistrict(title, id);
    }

    @DeleteMapping("/{id}")
    public void deleteDistrictById(@PathVariable Long id) {
        districtService.deleteById(id);
    }
}

