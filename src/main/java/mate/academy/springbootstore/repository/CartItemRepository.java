package mate.academy.springbootstore.repository;

import java.util.Optional;
import mate.academy.springbootstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByIdAndShoppingCartId(Long cartItemId, Long cartId);

    Optional<CartItem> findByShoppingCartIdAndBookId(Long cartId, Long bookId);
}
