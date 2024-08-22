package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.maintenance.entyties.WorkSite;
import ru.service.maintenance.repositories.WorkSiteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkSiteService {
    private final WorkSiteRepository workSiteRepository;

    public List<WorkSite> FindAll() {
        return workSiteRepository.findAll();
    }
}
