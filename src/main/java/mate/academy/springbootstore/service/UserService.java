package mate.academy.springbootstore.service;

import mate.academy.springbootstore.dto.*;
import mate.academy.springbootstore.exception.*;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;

    void deleteById(Long id);
}
