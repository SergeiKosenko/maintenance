package ru.service.maintenance.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private List<String> roles;
    private Long regionId; // Добавляем ID региона пользователя
}
