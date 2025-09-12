package mate.academy.springbootstore.service.order;

import java.util.List;
import mate.academy.springbootstore.dto.order.OrderItemResponseDto;
import mate.academy.springbootstore.dto.order.OrderRequestDto;
import mate.academy.springbootstore.dto.order.OrderResponseDto;
import mate.academy.springbootstore.model.Status;

public interface OrderService {

    OrderResponseDto placeOrder(OrderRequestDto orderRequestDto, Long userId);

    List<OrderResponseDto> getUserOrders(Long userId);

    List<OrderItemResponseDto> getOrderItems(Long orderId, Long userId);

    OrderItemResponseDto getOrderItem(Long orderId, Long itemId, Long userId);

    OrderResponseDto updateOrderStatus(Long orderId, Status status);
}
