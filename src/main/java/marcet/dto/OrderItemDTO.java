package marcet.dto;

import marcet.model.Order;
import marcet.model.Product;

import java.math.BigDecimal;

public class OrderItemDTO {

    private Long orderItemId;
    private Order order;
    private Product product;
    private int quantity;
    private BigDecimal price;
    private BigDecimal cost;
}
