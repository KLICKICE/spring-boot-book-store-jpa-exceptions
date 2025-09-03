package mate.academy.springbootstore.service.cart;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.cart.CartItemDto;
import mate.academy.springbootstore.exception.EntityNotFoundException;
import mate.academy.springbootstore.mapper.CartItemMapper;
import mate.academy.springbootstore.model.Book;
import mate.academy.springbootstore.model.CartItem;
import mate.academy.springbootstore.model.ShoppingCart;
import mate.academy.springbootstore.repository.CartItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemDto getCartItemById(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Failed to get cart item: cart item with id " + cartItemId + " not found"));
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public CartItemDto addCartItem(ShoppingCart cart, Book book, int quantity) {
        Optional<CartItem> existingItem = cartItemRepository
                .findByShoppingCartIdAndBookId(cart.getId(), book.getId());

        CartItem cartItem;
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setShoppingCart(cart);
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);
        }

        cartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public CartItemDto updateCartItemQuantity(CartItem cartItem, int quantity) {
        cartItem.setQuantity(quantity);
        cartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public void deleteCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
}
