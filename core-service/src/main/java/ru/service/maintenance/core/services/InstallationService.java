package ru.service.maintenance.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.maintenance.core.entyties.Installation;
import ru.service.maintenance.core.repositories.InstallationRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstallationService {

    private final InstallationRepository installationRepository;

    public Collection<Installation> findAll() {
        return installationRepository.findAll();
    }

    public Optional<Installation> FindById(Long id) {
        return installationRepository.findById(id);
    }
}

