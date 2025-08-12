package mate.academy.springbootstore.mapper;

import mate.academy.springbootstore.dto.*;
import mate.academy.springbootstore.model.*;
import org.mapstruct.Mapper;

@Mapper(config = Mapper.class)
public interface UserMapper {
    User toEntity(UserRegistrationRequestDto dto);

    UserResponseDto toDto(User user);
}
