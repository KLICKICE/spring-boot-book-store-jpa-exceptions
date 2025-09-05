package mate.academy.springbootstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.cart.CartItemDto;
import mate.academy.springbootstore.dto.cart.CreateCartItemRequestDto;
import mate.academy.springbootstore.dto.cart.ShoppingCartDto;
import mate.academy.springbootstore.dto.cart.UpdateCartItemDto;
import mate.academy.springbootstore.model.User;
import mate.academy.springbootstore.service.cart.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping Cart management", description = "Operations related to user's shopping cart")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get user's shopping cart",
            description = "Retrieve the shopping cart of the authenticated user")
    public ResponseEntity<ShoppingCartDto> getCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(shoppingCartService.getCartByUserId(user.getId()));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Add book to cart",
            description = "Add a book to the authenticated user's shopping cart")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShoppingCartDto> addBookToCart(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateCartItemRequestDto requestDto) {
        return ResponseEntity.ok(shoppingCartService.addBookToCart(user.getId(), requestDto));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "Update cart item quantity",
            description = "Update quantity of a book in the shopping cart")
    public ResponseEntity<ShoppingCartDto> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateCartItemDto requestDto) {
        return ResponseEntity.ok(shoppingCartService
                .updateCartItemQuantity(cartItemId, requestDto));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "Remove book from cart",
            description = "Remove a book from the shopping cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCartItem(@PathVariable Long cartItemId) {
        shoppingCartService.removeCartItem(cartItemId);
    }
}
