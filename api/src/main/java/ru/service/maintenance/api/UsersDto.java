package ru.service.maintenance.api;

import java.util.Set;

public class UsersDto {
    private Long id;
    private Long regionesId;
    private String username;
    private String password;
    private String email;
    private String telegram;
    private String firstName;
    private String lastName;
    private String phone;
    private String regionesTitle;
    private Set role;
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegionesId() {
        return regionesId;
    }

    public void setRegionesId(Long regionesId) {
        this.regionesId = regionesId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegionesTitle() {
        return regionesTitle;
    }

    public void setRegionesTitle(String regionesTitle) {
        this.regionesTitle = regionesTitle;
    }

    public Set getRole() {
        return role;
    }

    public void setRole(Set role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UsersDto() {
    }

    public UsersDto(Long id, Long regionesId, String username, String password, String email, String telegram, String firstName, String lastName, String phone, String regionesTitle, Set role, boolean active) {
        this.id = id;
        this.regionesId = regionesId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telegram = telegram;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.regionesTitle = regionesTitle;
        this.role = role;
        this.active = active;
    }
}