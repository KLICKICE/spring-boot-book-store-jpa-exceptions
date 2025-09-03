package mate.academy.springbootstore.service.cart;

import mate.academy.springbootstore.dto.cart.CartItemDto;
import mate.academy.springbootstore.model.Book;
import mate.academy.springbootstore.model.CartItem;
import mate.academy.springbootstore.model.ShoppingCart;

public interface CartItemService {

    CartItemDto getCartItemById(Long cartItemId);

    CartItemDto addCartItem(ShoppingCart cart, Book book, int quantity);

    CartItemDto updateCartItemQuantity(CartItem cartItem, int quantity);

    void deleteCartItem(CartItem cartItem);
}

