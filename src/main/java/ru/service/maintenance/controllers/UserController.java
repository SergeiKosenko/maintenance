package ru.service.maintenance.controllers;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.service.maintenance.converters.UserConverter;
import ru.service.maintenance.dtos.UsersDto;
import ru.service.maintenance.entyties.User;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.repositories.RegionesRepository;
import ru.service.maintenance.services.RoleService;
import ru.service.maintenance.services.UserService;

import java.util.List;
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

//    @GetMapping
//    public Page<User> searchUsers(@RequestParam(name = "p", defaultValue = "1") Integer page,
//                                  @RequestParam(name = "page_size", defaultValue = "9") Integer pageSize) {
//        if (page < 1) {
//            page = 1;
//        }
//        return userService.searchUsers(page, pageSize);
//    }

    @GetMapping()
    public List<UsersDto> getAllUsers() {
        return userService.findAll().stream().map(userConverter::entityToDto).collect(Collectors.toList());
    }

//    @GetMapping("/all/{idRegiones}")
//    public List<UsersDto> getAllUsersIdRegiones(@PathVariable Long idRegiones) {
//        return userService.findAll().stream().filter(p -> p.equals(idRegiones)).map(userConverter::entityToDto).collect(Collectors.toList());
//    }

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
}
