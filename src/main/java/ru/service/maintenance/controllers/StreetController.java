package ru.service.maintenance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.maintenance.converters.StreetConverter;
import ru.service.maintenance.dtos.DistrictDto;
import ru.service.maintenance.dtos.StreetDto;
import ru.service.maintenance.entyties.District;
import ru.service.maintenance.entyties.Street;
import ru.service.maintenance.services.StreetService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/streets")
@RequiredArgsConstructor
public class StreetController {
    private final StreetService streetService;
    private final StreetConverter streetConverter;
    @GetMapping
    public List<StreetDto> getAllStreets() {
        return streetService.findAll().stream().map(streetConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StreetDto getStreetById(@PathVariable Long id){
        Street p = streetService.FindById(id).get();
        return streetConverter.entityToDto(p);
    }
}
