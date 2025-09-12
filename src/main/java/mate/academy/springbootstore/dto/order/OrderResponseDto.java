package mate.academy.springbootstore.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private String status;
    private Set<OrderItemResponseDto> orderItems;
}
