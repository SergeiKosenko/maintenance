package ru.service.maintenance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.service.maintenance.converters.RegionesConverter;
import ru.service.maintenance.dtos.RegionesDto;
import ru.service.maintenance.exceptions.AppError;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
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
    public RegionesDto getRegionesById(@PathVariable Long id) {
//        Optional<Regiones> p = regionesService.FindById(id);
//        if (p.isPresent()){
//            return new ResponseEntity<>(regionesConverter.entityToDto(p.get()), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new AppError("123","Регион с ID: + " +id+ "не найден"), HttpStatus.NOT_FOUND);
          return regionesConverter.entityToDto(regionesService.FindById(id).orElseThrow(() -> new ResourceNotFoundException("Регион с ID: " +id+ " не найден")));
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
