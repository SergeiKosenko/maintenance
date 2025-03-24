package ru.service.maintenance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.maintenance.dtos.StreetDto;

import java.util.List;

@RestController
@RequestMapping("api/v1/sensor")
@RequiredArgsConstructor
public class SensorController {
    @GetMapping("/all")
    public List<StreetDto> getAllStreets() {
        return null;
//                streetService.findAll().stream().map(streetConverter::entityToDto).collect(Collectors.toList());
    }
}
