package mate.academy.springbootstore.service;

import mate.academy.springbootstore.dto.UserRegistrationRequestDto;
import mate.academy.springbootstore.dto.UserResponseDto;
import mate.academy.springbootstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
