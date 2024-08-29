package ru.service.maintenance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.service.maintenance.converters.RegionesConverter;
import ru.service.maintenance.dtos.RegionesDto;
import ru.service.maintenance.entyties.Regiones;
import ru.service.maintenance.services.RegionesService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/regiones")
@RequiredArgsConstructor
public class RegionesController {
    private final RegionesService regionesService;
    private final RegionesConverter regionesConverter;

    @GetMapping
    public List<RegionesDto> getAllRegiones() {
        return regionesService.findAll().stream().map(regionesConverter::entityToDto).collect(Collectors.toList());
 }

    @GetMapping("/{id}")
    public RegionesDto getDistrictById(@PathVariable Long id) {
        Regiones p = regionesService.FindById(id).get();
        return regionesConverter.entityToDto(p);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewRegiones(@RequestBody RegionesDto regionesDto) {
        regionesService.createNewRegiones(regionesDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRegionesById(@PathVariable Long id) {
        regionesService.deleteById(id);
    }
}
