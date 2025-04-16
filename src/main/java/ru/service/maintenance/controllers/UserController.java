package ru.service.maintenance.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.service.maintenance.converters.UserConverter;
import ru.service.maintenance.dtos.UsersDto;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.repositories.RegionesRepository;
import ru.service.maintenance.services.RoleService;
import ru.service.maintenance.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;
    private final RegionesRepository regionesRepository;
    private final RoleService roleService;

    public UserController(UserService userService, UserConverter userConverter, RegionesRepository regionesRepository, RoleService roleService) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.regionesRepository = regionesRepository;
        this.roleService = roleService;
    }

    @GetMapping()
    public List<UsersDto> getAllUsers() {
        return userService.findAll().stream().map(userConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UsersDto getUsersById(@PathVariable Long id) {
        return userConverter.entityToDto(userService.FindById(id).orElseThrow(() -> new ResourceNotFoundException("Пользователь с ID: " +id+ " не найден")));
    }

    @GetMapping("/roles")
    public List<String> getAllRoleNames() {
        return roleService.findAllRoleNames();
    }

    @PatchMapping("/roles/{id}")
    public void changeRole(@RequestParam String roleName, @PathVariable Long id) {
        userService.changeRole(roleName, id);
    }

    @PatchMapping("/active/{id}")
    public void changeActive(@RequestParam String active, @PathVariable Long id) {
        userService.changeActive(active, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PatchMapping("/{username}/telegram")
    public ResponseEntity<?> updateTelegramChatId(
            @PathVariable String username,
            @RequestBody Map<String, String> request,
            Principal principal) {

        if (!principal.getName().equals(username) && !userService.isAdmin(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.updateTelegramChatId(username, request.get("telegramChatId"));
        return ResponseEntity.ok().build();
    }
}

