package mate.academy.springbootstore.controller;

import jakarta.validation.*;
import lombok.*;
import mate.academy.springbootstore.dto.*;
import mate.academy.springbootstore.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto request) {
        return userService.register(request);
    }
}

