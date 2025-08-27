package mate.academy.springbootstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.user.UserLoginRequestDto;
import mate.academy.springbootstore.dto.user.UserLoginResponseDto;
import mate.academy.springbootstore.dto.user.UserRegistrationRequestDto;
import mate.academy.springbootstore.dto.user.UserResponseDto;
import mate.academy.springbootstore.security.AuthenticationService;
import mate.academy.springbootstore.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User management", description = "Operations related to users")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @Operation(summary = "Register a new user", description = "Register a new user")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Log in to an account", description = "Log in to an account")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}

