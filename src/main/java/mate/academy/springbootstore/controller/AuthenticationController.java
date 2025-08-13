package mate.academy.springbootstore.controller;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.UserRegistrationRequestDto;
import mate.academy.springbootstore.dto.UserResponseDto;
import mate.academy.springbootstore.service.UserService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User management", description = "Operations related to users")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    @Operation(summary = "Register a new user", description = "Register a new user")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto request) {
        return userService.register(request);
    }
}

