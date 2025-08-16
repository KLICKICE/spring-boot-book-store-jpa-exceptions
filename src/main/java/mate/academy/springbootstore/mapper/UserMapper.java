package mate.academy.springbootstore.mapper;

import mate.academy.springbootstore.config.MapConfig;
import mate.academy.springbootstore.dto.UserRegistrationRequestDto;
import mate.academy.springbootstore.dto.UserResponseDto;
import mate.academy.springbootstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapConfig.class)
public interface UserMapper {
    User toEntity(UserRegistrationRequestDto dto);

    UserResponseDto toDto(User user);
}
