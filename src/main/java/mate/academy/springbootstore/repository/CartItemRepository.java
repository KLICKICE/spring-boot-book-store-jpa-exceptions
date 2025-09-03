package mate.academy.springbootstore.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.springbootstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByShoppingCartIdAndBookId(Long shoppingCartId, Long bookId);

    List<CartItem> findAllByShoppingCartId(Long shoppingCartId);
}
