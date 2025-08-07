package ru.service.maintenance.api;

import java.util.List;

public class AuthResponse {
    private String token;
    private List<String> roles;
    private Long regionId; // Добавляем ID региона пользователя

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public AuthResponse() {
    }

    public AuthResponse(String token, List<String> roles, Long regionId) {
        this.token = token;
        this.roles = roles;
        this.regionId = regionId;
    }
}
