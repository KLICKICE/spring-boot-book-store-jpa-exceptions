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
import mate.academy.springbootstore.exception.OrderProcessingException;
import mate.academy.springbootstore.mapper.OrderItemMapper;
import mate.academy.springbootstore.mapper.OrderMapper;
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
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto, Long userId) {
        User user = getUser(userId);
        ShoppingCart cart = getCart(userId);
        validateCartNotEmpty(cart);

        Order order = buildOrder(orderRequestDto, user, cart);
        orderRepository.save(order);

        clearCart(cart);

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
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find order with id: " + orderId
                                + " for user id: " + userId));

        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId,
                                             Long itemId, Long userId) {
        OrderItem item = orderItemRepository.findByIdAndOrderIdAndOrderUserId(itemId,
                        orderId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find order item with id: " + itemId
                                + " for order id: " + orderId + " and user id: " + userId));

        return orderItemMapper.toDto(item);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, Status status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find order with id: " + orderId));

        order.setStatus(status);
        orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user with id: " + userId));
    }

    private ShoppingCart getCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find shopping cart for user id: "
                                + userId));
    }

    private void validateCartNotEmpty(ShoppingCart cart) {
        if (cart.getCartItems().isEmpty()) {
            throw new OrderProcessingException("Cart is empty for user id: "
                    + cart.getUser().getId());
        }
    }

    private Order buildOrder(OrderRequestDto dto, User user, ShoppingCart cart) {
        Order order = orderMapper.toModel(dto);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);

        BigDecimal total = calculateTotal(cart);
        order.setTotal(total);

        Set<OrderItem> orderItems = buildOrderItems(order, cart);
        order.setOrderItems(orderItems);

        return order;
    }

    private BigDecimal calculateTotal(ShoppingCart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> cartItem.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<OrderItem> buildOrderItems(Order order, ShoppingCart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getBook().getPrice());
                    return orderItem;
                }).collect(Collectors.toSet());
    }

    private void clearCart(ShoppingCart cart) {
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
