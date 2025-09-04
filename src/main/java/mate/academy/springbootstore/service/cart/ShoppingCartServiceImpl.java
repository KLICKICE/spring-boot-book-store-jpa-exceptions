package mate.academy.springbootstore.service.cart;

import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.cart.CartItemDto;
import mate.academy.springbootstore.dto.cart.CreateCartItemRequestDto;
import mate.academy.springbootstore.dto.cart.ShoppingCartDto;
import mate.academy.springbootstore.dto.cart.UpdateCartItemDto;
import mate.academy.springbootstore.exception.EntityNotFoundException;
import mate.academy.springbootstore.mapper.CartItemMapper;
import mate.academy.springbootstore.mapper.ShoppingCartMapper;
import mate.academy.springbootstore.model.Book;
import mate.academy.springbootstore.model.CartItem;
import mate.academy.springbootstore.model.ShoppingCart;
import mate.academy.springbootstore.repository.BookRepository;
import mate.academy.springbootstore.repository.CartItemRepository;
import mate.academy.springbootstore.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartDto getCartByUserId(Long userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart for user id " + userId + " not found"));
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    public ShoppingCartDto addBookToCart(Long userId, CreateCartItemRequestDto requestDto) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart for user id " + userId + " not found"));

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book with id " + requestDto.getBookId() + " not found"));

        CartItem cartItem = cartItemRepository
                .findByShoppingCartIdAndBookId(cart.getId(), book.getId())
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setShoppingCart(cart);
                    newItem.setBook(book);
                    newItem.setQuantity(0);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toDto(cart);
    }

    @Override
    public ShoppingCartDto updateCartItemQuantity(Long cartItemId, UpdateCartItemDto requestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart item with id " + cartItemId + " not found"));

        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        ShoppingCart cart = cartItem.getShoppingCart();
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart item with id " + cartItemId + " not found"));

        cartItemRepository.delete(cartItem);
    }

    @Override
    public CartItemDto getCartItemById(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart item with id " + cartItemId + " not found"));
        return cartItemMapper.toDto(cartItem);
    }
}
