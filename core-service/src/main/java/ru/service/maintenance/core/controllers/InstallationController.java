package ru.service.maintenance.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.maintenance.api.InstallationDto;
import ru.service.maintenance.core.converters.InstallationConverter;
import ru.service.maintenance.core.services.InstallationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/installations")
@RequiredArgsConstructor
public class InstallationController {
    private final InstallationService installationService;
    private final InstallationConverter installationConverter;

    @GetMapping("/all")
    public List<InstallationDto> getAllManufacture() {
        return installationService.findAll().stream().map(installationConverter::entityToDto).collect(Collectors.toList());
    }
}
