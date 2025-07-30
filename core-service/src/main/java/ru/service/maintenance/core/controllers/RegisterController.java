package ru.service.maintenance.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.service.maintenance.core.dtos.UsersDto;
import ru.service.maintenance.core.services.UserService;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUser(@RequestBody UsersDto usersDto) {
        userService.createNewUser(usersDto);
    }
}
