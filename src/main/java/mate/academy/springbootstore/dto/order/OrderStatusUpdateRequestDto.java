package mate.academy.springbootstore.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mate.academy.springbootstore.model.Status;

@Getter
@Setter
public class OrderStatusUpdateRequestDto {
    @NotNull
    private Status status;
}
