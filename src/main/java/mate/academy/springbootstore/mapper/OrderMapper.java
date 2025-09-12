package mate.academy.springbootstore.mapper;

import java.util.List;
import java.util.Set;
import mate.academy.springbootstore.config.MapConfig;
import mate.academy.springbootstore.dto.order.OrderItemResponseDto;
import mate.academy.springbootstore.dto.order.OrderRequestDto;
import mate.academy.springbootstore.dto.order.OrderResponseDto;
import mate.academy.springbootstore.model.Order;
import mate.academy.springbootstore.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(config = MapConfig.class)
public interface OrderMapper {
    Order toModel(OrderRequestDto dto);

    OrderResponseDto toDto(Order order);

    OrderItemResponseDto toDto(OrderItem orderItem);

    List<OrderResponseDto> toDtoList(List<Order> orders);

    Set<OrderItemResponseDto> toDtoSet(Set<OrderItem> orderItems);
}

