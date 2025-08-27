package mate.academy.springbootstore.service.user;

import mate.academy.springbootstore.dto.user.UserRegistrationRequestDto;
import mate.academy.springbootstore.dto.user.UserResponseDto;
import mate.academy.springbootstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
