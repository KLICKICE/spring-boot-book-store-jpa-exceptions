package mate.academy.springbootstore.service.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.order.OrderItemResponseDto;
import mate.academy.springbootstore.dto.order.OrderRequestDto;
import mate.academy.springbootstore.dto.order.OrderResponseDto;
import mate.academy.springbootstore.exception.DataProcessingException;
import mate.academy.springbootstore.exception.EntityNotFoundException;
import mate.academy.springbootstore.mapper.OrderItemMapper;
import mate.academy.springbootstore.mapper.OrderMapper;
import mate.academy.springbootstore.model.CartItem;
import mate.academy.springbootstore.model.Order;
import mate.academy.springbootstore.model.OrderItem;
import mate.academy.springbootstore.model.ShoppingCart;
import mate.academy.springbootstore.model.Status;
import mate.academy.springbootstore.model.User;
import mate.academy.springbootstore.repository.OrderRepository;
import mate.academy.springbootstore.repository.ShoppingCartRepository;
import mate.academy.springbootstore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User: " + userId));

        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("ShoppingCart: " + userId));

        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new DataProcessingException("Cart is empty for user: " + userId);
        }

        Order order = orderMapper.toModel(orderRequestDto);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);

        BigDecimal total = cartItems.stream()
                .map(cartItem -> cartItem.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);

        Set<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getBook().getPrice());
                    return orderItem;
                }).collect(java.util.stream.Collectors.toSet());

        order.setOrderItems(orderItems);
        orderRepository.save(order);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orderMapper.toDtoList(orders);
    }

    @Override
    public List<OrderItemResponseDto> getOrderItems(Long orderId, Long userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Order: " + orderId));

        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long itemId, Long userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Order: " + orderId));

        OrderItem item = order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("OrderItem: " + itemId));

        return orderItemMapper.toDto(item);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, Status status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order: " + orderId));

        order.setStatus(status);
        orderRepository.save(order);

        return orderMapper.toDto(order);
    }
}
