package mate.academy.springbootstore.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.springbootstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderId(Long orderId);

    Optional<OrderItem> findByIdAndOrderId(Long itemId, Long orderId);

    List<OrderItem> findAllByOrderIdIn(List<Long> orderIds);

    Optional<OrderItem> findByIdAndOrderIdAndOrderUserId(Long itemId, Long orderId, Long userId);
}
