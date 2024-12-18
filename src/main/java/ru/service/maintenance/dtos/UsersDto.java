package ru.service.maintenance.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UsersDto {
    private Long id;
    private Long regionesId;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String regionesTitle;
    private Set role;
    private boolean active;

//    public void setRegionesId(Long id) {
//    }
}