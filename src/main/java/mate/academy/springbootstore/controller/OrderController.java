package mate.academy.springbootstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.order.OrderItemResponseDto;
import mate.academy.springbootstore.dto.order.OrderRequestDto;
import mate.academy.springbootstore.dto.order.OrderResponseDto;
import mate.academy.springbootstore.dto.order.OrderStatusUpdateRequestDto;
import mate.academy.springbootstore.model.User;
import mate.academy.springbootstore.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Operations related to orders")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Place an order",
            description = "Place an order with the authenticated user's shopping cart")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponseDto> placeOrder(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid OrderRequestDto orderRequestDto) {

        return ResponseEntity.ok(orderService.placeOrder(orderRequestDto, user.getId()));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get user's orders",
            description = "Retrieve all orders of the authenticated user")
    public ResponseEntity<List<OrderResponseDto>> getUserOrders(
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(orderService.getUserOrders(user.getId()));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get items in an order",
            description = "Retrieve all items of a specific order for the authenticated user")
    public ResponseEntity<List<OrderItemResponseDto>> getOrderItems(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.getOrderItems(orderId, user.getId()));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get specific order item",
            description = "Retrieve a specific item from an order for the authenticated user")
    public ResponseEntity<OrderItemResponseDto> getOrderItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId,
            @PathVariable Long itemId) {

        return ResponseEntity.ok(orderService.getOrderItem(orderId, itemId, user.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{orderId}")
    @Operation(summary = "Update order status",
            description = "Update the status of an order (ADMIN only)")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderStatusUpdateRequestDto requestDto) {

        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, requestDto.getStatus()));
    }
}
