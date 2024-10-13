package ru.service.maintenance.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.dtos.UsersDto;
import ru.service.maintenance.entyties.User;

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
        usersDto.setRegionesTitle(p.getRegiones().getTitle());
        usersDto.setActive(p.isActive());
        return usersDto;
    }
}