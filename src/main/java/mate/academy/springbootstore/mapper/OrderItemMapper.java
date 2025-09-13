package mate.academy.springbootstore.mapper;

import mate.academy.springbootstore.config.MapConfig;
import mate.academy.springbootstore.dto.order.OrderItemResponseDto;
import mate.academy.springbootstore.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(config = MapConfig.class)
public interface OrderItemMapper {
    OrderItemResponseDto toDto(OrderItem orderItem);
}

