package ru.service.maintenance.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.maintenance.api.ManufactureDto;
import ru.service.maintenance.core.converters.ManufactureConverter;
import ru.service.maintenance.core.services.ManufactureService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/manufactures")
@RequiredArgsConstructor
public class ManufactureController {
    private final ManufactureService manufactureService;
    private final ManufactureConverter manufactureConverter;

    @GetMapping("/all")
    public List<ManufactureDto> getAllManufactures() {
        return manufactureService.findAll().stream().map(manufactureConverter::entityToDto).collect(Collectors.toList());
    }
}
