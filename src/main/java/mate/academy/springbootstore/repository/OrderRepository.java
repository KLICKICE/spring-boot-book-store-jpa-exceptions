package mate.academy.springbootstore.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.springbootstore.model.Order;
import mate.academy.springbootstore.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUserId(Long userId, Pageable pageable);

    List<Order> findAllByUserId(Long userId);

    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findWithOrderItemsById(Long id);

    List<Order> findAllByStatus(Status status);

    boolean existsByIdAndUserId(Long orderId, Long userId);

    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
