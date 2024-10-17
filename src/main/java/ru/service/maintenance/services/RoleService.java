package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.maintenance.entyties.Role;
import ru.service.maintenance.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER");
    }
}