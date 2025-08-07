package ru.service.maintenance.core.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.api.UsersDto;
import ru.service.maintenance.core.entyties.User;

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