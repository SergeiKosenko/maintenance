package ru.service.maintenance.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtRequest {
    private String username;
    private String password;
    private String passwordConfirm;
    private String email;

    public JwtRequest(String username, String password) {
    }
}
