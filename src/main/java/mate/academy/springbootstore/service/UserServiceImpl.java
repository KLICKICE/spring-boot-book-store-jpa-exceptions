package mate.academy.springbootstore.service;

import lombok.*;
import mate.academy.springbootstore.dto.UserRegistrationRequestDto;
import mate.academy.springbootstore.dto.UserResponseDto;
import mate.academy.springbootstore.exception.*;
import mate.academy.springbootstore.mapper.*;
import mate.academy.springbootstore.model.*;
import mate.academy.springbootstore.repository.*;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException(
                    "User with email '" + request.getEmail() + "' already exists"
            );
        }
        User user = userMapper.toEntity(request);
        userRepository.save(user);
        return userMapper.toDto(user);
    }


    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Failed to delete user: user with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
