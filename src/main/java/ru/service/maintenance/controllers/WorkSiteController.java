package ru.service.maintenance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.maintenance.entyties.WorkSite;
import ru.service.maintenance.services.WorkSiteService;

import java.util.List;

@RestController
@RequestMapping("api/v1/worksite")
@RequiredArgsConstructor
public class WorkSiteController {
    private final WorkSiteService workSiteService;

    @GetMapping
    public List<WorkSite> getAllStreets() {
        return workSiteService.FindAll();
    }
}
