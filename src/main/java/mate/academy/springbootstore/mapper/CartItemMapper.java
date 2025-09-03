package mate.academy.springbootstore.mapper;

import mate.academy.springbootstore.config.MapConfig;
import mate.academy.springbootstore.dto.cart.CartItemDto;
import mate.academy.springbootstore.dto.cart.CreateCartItemRequestDto;
import mate.academy.springbootstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapConfig.class)
public interface CartItemMapper {

    CartItemDto toDto(CartItem cartItem);

    CartItem toModel(CreateCartItemRequestDto createCartItemRequestDto);

    void updateCartItemFromDb(CreateCartItemRequestDto requestDto, @MappingTarget CartItem entity);
}
