package marcet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import marcet.model.Order;
import marcet.model.Product;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemDTO {

    private Long orderItemId;
    private Order order;
    private Product product;
    private int quantity;
    private BigDecimal price;
    private BigDecimal cost;
}
