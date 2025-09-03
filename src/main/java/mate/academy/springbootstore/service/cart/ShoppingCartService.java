package mate.academy.springbootstore.service.cart;

import mate.academy.springbootstore.dto.cart.CartItemDto;
import mate.academy.springbootstore.dto.cart.CreateCartItemRequestDto;
import mate.academy.springbootstore.dto.cart.ShoppingCartDto;
import mate.academy.springbootstore.dto.cart.UpdateCartItemDto;

public interface ShoppingCartService {

    ShoppingCartDto getCartByUserId(Long userId);

    CartItemDto addBookToCart(Long userId, CreateCartItemRequestDto requestDto);

    CartItemDto updateCartItemQuantity(Long cartItemId, UpdateCartItemDto requestDto);

    void removeCartItem(Long cartItemId);
}

