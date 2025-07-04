package ru.service.maintenance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.service.maintenance.converters.RegionesConverter;
import ru.service.maintenance.dtos.RegionesDto;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.services.RegionesService;
import ru.service.maintenance.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/regiones")
@RequiredArgsConstructor
public class RegionesController {
    private final RegionesService regionesService;
    private final RegionesConverter regionesConverter;
    private final UserService userService;

    @GetMapping("/all")
    public List<RegionesDto> getAllRegiones() {
        return regionesService.findAll().stream().map(regionesConverter::entityToDto).collect(Collectors.toList());
 }

    @GetMapping
    public RegionesDto getUserRegiones(Principal principal) {
        return regionesConverter.entityToDto(regionesService.FindById(userService.findByUsername(principal.getName()).get().getRegiones().getId()).orElseThrow(() -> new ResourceNotFoundException("Регион с ID не найден")));
    }

    @GetMapping("/{id}")
    public RegionesDto getRegionesById(@PathVariable Long id) {
          return regionesConverter.entityToDto(regionesService.FindById(id).orElseThrow(() -> new ResourceNotFoundException("Регион с ID: " +id+ " не найден")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewRegiones(@RequestBody RegionesDto regionesDto) {
        regionesService.createNewRegiones(regionesDto);
    }

    @PatchMapping("/{id}")
    public void changeRegiones(@RequestParam String title, @PathVariable Long id) {
        regionesService.changeRegion(title, id);
    }

    @DeleteMapping("/{id}")
    public void deleteRegionesById(@PathVariable Long id) {
        regionesService.deleteById(id);
    }
}
