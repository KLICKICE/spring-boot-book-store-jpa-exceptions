package mate.academy.springbootstore.service.cart;

import mate.academy.springbootstore.dto.cart.CreateCartItemRequestDto;
import mate.academy.springbootstore.dto.cart.ShoppingCartDto;
import mate.academy.springbootstore.dto.cart.UpdateCartItemDto;
import mate.academy.springbootstore.model.User;

public interface ShoppingCartService {

    ShoppingCartDto getCartByUserId(Long userId);

    ShoppingCartDto addBookToCart(Long userId,
                                  CreateCartItemRequestDto requestDto);

    ShoppingCartDto updateCartItemQuantity(Long userId,
                                           Long cartItemId,
                                           UpdateCartItemDto requestDto);

    void removeCartItem(Long userId, Long cartItemId);

    void createCartForUser(User user);
}

