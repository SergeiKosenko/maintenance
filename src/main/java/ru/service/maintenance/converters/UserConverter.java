package ru.service.maintenance.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.dtos.UsersDto;
import ru.service.maintenance.entyties.Role;
import ru.service.maintenance.entyties.User;
import ru.service.maintenance.repositories.RoleRepository;

import java.util.List;
import java.util.Set;

@Component
public class UserConverter {
    public UsersDto entityToDto(User p) {
        UsersDto usersDto = new UsersDto();

        usersDto.setId(p.getId());
        usersDto.setUsername(p.getUsername());
        usersDto.setFirstName(p.getFirstName());
        usersDto.setLastName(p.getLastName());
        usersDto.setPhone(p.getPhone());
        usersDto.setEmail(p.getEmail());
        usersDto.setTelegram(p.getTelegram());
        usersDto.setRegionesTitle(p.getRegiones().getTitle());
        usersDto.setRegionesId(p.getRegiones().getId());
        usersDto.setActive(p.isActive());
        usersDto.setPassword(p.getPassword());
        usersDto.setRole(p.getRoles());
        return usersDto;
    }
}