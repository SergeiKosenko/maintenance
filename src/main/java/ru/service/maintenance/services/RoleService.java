package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Long getUserRole() {
        return roleRepository.findByName("ROLE_USER").getId();
    }

    public List<String> findAllRoleNames() {
        try {
            return roleRepository.findAllNames();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Роли не найдены");
        }
    }
}