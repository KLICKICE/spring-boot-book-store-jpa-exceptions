package mate.academy.springbootstore.service.user;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.user.UserRegistrationRequestDto;
import mate.academy.springbootstore.dto.user.UserResponseDto;
import mate.academy.springbootstore.exception.EntityNotFoundException;
import mate.academy.springbootstore.exception.RegistrationException;
import mate.academy.springbootstore.mapper.UserMapper;
import mate.academy.springbootstore.model.Role;
import mate.academy.springbootstore.model.User;
import mate.academy.springbootstore.repository.RoleRepository;
import mate.academy.springbootstore.repository.UserRepository;
import mate.academy.springbootstore.service.cart.ShoppingCartService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException(
                    "User with email '" + request.getEmail() + "' already exists"
            );
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findByName(Role.RoleName.USER)
                .orElseThrow(()
                        -> new EntityNotFoundException("Role "
                        + Role.RoleName.USER + " not found"));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        shoppingCartService.createCartForUser(user);
        return userMapper.toDto(user);
    }
}

