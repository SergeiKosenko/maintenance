package ru.service.maintenance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.service.maintenance.converters.StreetConverter;
import ru.service.maintenance.dtos.StreetDto;
import ru.service.maintenance.entyties.Street;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.services.StreetService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/streets")
@RequiredArgsConstructor
public class StreetController {
    private final StreetService streetService;
    private final StreetConverter streetConverter;

    @GetMapping("/all")
    public List<StreetDto> getAllStreets() {
        return streetService.findAll().stream().map(streetConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/districtid/{districtId}")
    public List<StreetDto> getStreetByDistrictId(@PathVariable List<Long> districtId){
        List<StreetDto> list = new ArrayList<>();
        for (Street street : streetService.findAllByDistrictId(districtId)) {
            StreetDto streetDto = streetConverter.entityToDto(street);
            list.add(streetDto);
        }
        return list;
    }

    @GetMapping("/{id}")
    public StreetDto getStreetById(@PathVariable Long id){
        return streetConverter.entityToDto(streetService.FindById(id).orElseThrow(() -> new ResourceNotFoundException("Улица с ID: " +id+ " не найдена")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewStreet(@RequestBody StreetDto streetDto) {
        streetService.createNewStreet(streetDto);
    }

    @PatchMapping("/{id}")
    public void changeStreet(@RequestParam String title, @PathVariable Long id) {
        streetService.changeStreet(title, id);
    }

    @DeleteMapping("/{id}")
    public void deleteStreetById(@PathVariable Long id) {
        streetService.deleteById(id);
    }
}

