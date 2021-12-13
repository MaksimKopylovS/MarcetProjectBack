package marcet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import marcet.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {

    private Long order_id;
    private String username;
    private int totalQuantity;
    private BigDecimal totalCost;
    private AddressDTO address;
    private LocalDateTime createAt;

    public OrderDTO(Order order) {
        this.order_id = order.getOrderId();
        this.username = order.getUser().getUsername();
        this.totalQuantity = order.getTotalQuantity();
        this.totalCost = order.getTotalCost();
        this.address = new AddressDTO(order.getAddress());
        this.createAt = order.getCreateAt();
    }
}

