package mate.academy.springbootstore.service.user;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.user.UserRegistrationRequestDto;
import mate.academy.springbootstore.dto.user.UserResponseDto;
import mate.academy.springbootstore.exception.RegistrationException;
import mate.academy.springbootstore.mapper.UserMapper;
import mate.academy.springbootstore.model.Role;
import mate.academy.springbootstore.model.ShoppingCart;
import mate.academy.springbootstore.model.User;
import mate.academy.springbootstore.repository.RoleRepository;
import mate.academy.springbootstore.repository.ShoppingCartRepository;
import mate.academy.springbootstore.repository.UserRepository;
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

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException(
                    "User with email '" + request.getEmail() + "' already exists"
            );
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role.RoleName defaultRole = Role.RoleName.USER;
        Role userRole = roleRepository.findByName(defaultRole)
                .orElseThrow(() ->
                        new RuntimeException("Role " + defaultRole + " not found")
                );

        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
        return userMapper.toDto(user);
    }
}
