package mate.academy.springbootstore.mapper;

import mate.academy.springbootstore.config.MapConfig;
import mate.academy.springbootstore.dto.cart.ShoppingCartDto;
import mate.academy.springbootstore.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(config = MapConfig.class)
public interface ShoppingCartMapper {

    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
