package ru.service.maintenance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.maintenance.entyties.Street;
import ru.service.maintenance.services.StreetService;

import java.util.List;

@RestController
@RequestMapping("api/v1/streets")
@RequiredArgsConstructor
public class StreetController {
    private final StreetService streetService;

    @GetMapping
    public List<Street> getAllStreets() {
        return streetService.FindAll();
    }
}
