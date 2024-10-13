package ru.service.maintenance.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.maintenance.converters.UserConverter;
import ru.service.maintenance.dtos.UsersDto;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.repositories.RegionesRepository;
import ru.service.maintenance.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;
    private final RegionesRepository regionesRepository;

    public UserController(UserService userService, UserConverter userConverter, RegionesRepository regionesRepository) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.regionesRepository = regionesRepository;
    }

    @GetMapping()
    public List<UsersDto> getAllUsers() {
        return userService.FindAll().stream().map(userConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/all/{idRegiones}")
    public List<UsersDto> getAllUsersIdRegiones(@PathVariable Long idRegiones) {
        return userService.FindAll().stream().filter(p -> p.equals(idRegiones)).map(userConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UsersDto getUsersById(@PathVariable Long id) {
//        Optional<Regiones> p = regionesService.FindById(id);
//        if (p.isPresent()){
//            return new ResponseEntity<>(regionesConverter.entityToDto(p.get()), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new AppError("123","Регион с ID: + " +id+ "не найден"), HttpStatus.NOT_FOUND);
        return userConverter.entityToDto(userService.FindById(id).orElseThrow(() -> new ResourceNotFoundException("Пользователь с ID: " +id+ " не найден")));
    }
}
