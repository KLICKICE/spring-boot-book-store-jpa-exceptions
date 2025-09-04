package mate.academy.springbootstore.service.cart;

import mate.academy.springbootstore.dto.cart.CartItemDto;
import mate.academy.springbootstore.dto.cart.CreateCartItemRequestDto;
import mate.academy.springbootstore.dto.cart.ShoppingCartDto;
import mate.academy.springbootstore.dto.cart.UpdateCartItemDto;

public interface ShoppingCartService {

    ShoppingCartDto getCartByUserId(Long userId);

    ShoppingCartDto addBookToCart(Long userId,
                                  CreateCartItemRequestDto requestDto);

    ShoppingCartDto updateCartItemQuantity(Long cartItemId,
                                           UpdateCartItemDto requestDto);

    void removeCartItem(Long cartItemId);

    CartItemDto getCartItemById(Long cartItemId);
}

