package mate.academy.springbootstore.service.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.order.OrderItemResponseDto;
import mate.academy.springbootstore.dto.order.OrderRequestDto;
import mate.academy.springbootstore.dto.order.OrderResponseDto;
import mate.academy.springbootstore.exception.EntityNotFoundException;
import mate.academy.springbootstore.mapper.OrderMapper;
import mate.academy.springbootstore.model.CartItem;
import mate.academy.springbootstore.model.Order;
import mate.academy.springbootstore.model.OrderItem;
import mate.academy.springbootstore.model.ShoppingCart;
import mate.academy.springbootstore.model.Status;
import mate.academy.springbootstore.model.User;
import mate.academy.springbootstore.repository.OrderItemRepository;
import mate.academy.springbootstore.repository.OrderRepository;
import mate.academy.springbootstore.repository.ShoppingCartRepository;
import mate.academy.springbootstore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository cartRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Set<CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = orderMapper.toModel(orderRequestDto);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);
        BigDecimal total = cartItems.stream()
                .map(ci -> ci.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
        Set<OrderItem> orderItems = cartItems.stream()
                .map(ci -> {
                    OrderItem oi = new OrderItem();
                    oi.setOrder(order);
                    oi.setBook(ci.getBook());
                    oi.setQuantity(ci.getQuantity());
                    oi.setPrice(ci.getBook().getPrice());
                    return oi;
                }).collect(Collectors.toSet());

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
        Order order = orderRepository.findWithOrderItemsById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        return orderMapper.toDtoSet(order.getOrderItems()).stream().toList();
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long itemId, Long userId) {
        Order order = orderRepository.findWithOrderItemsById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        OrderItem item = order.getOrderItems().stream()
                .filter(oi -> oi.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Order item not found"));

        return orderMapper.toDto(item);
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, Status status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        order.setStatus(status);
        orderRepository.save(order);

        return orderMapper.toDto(order);
    }
}
