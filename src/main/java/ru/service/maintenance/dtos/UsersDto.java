package ru.service.maintenance.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.service.maintenance.entyties.Role;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UsersDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String regionesTitle;
    private Set role;
    private boolean active;

}