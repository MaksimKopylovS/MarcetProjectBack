package marcet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "order_items_tbl")
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @JsonIgnore
    @ManyToOne // LSS добавил связь и изменил тип данных
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne // LSS добавил связь и изменил тип данных
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Column(name = "quantity_fld")
    private int quantity;

    @Column(name = "price_fld")
    private BigDecimal price;

    @Column(name = "cost_fld")
    private BigDecimal cost;

    public OrderItem(Order order, Product product, int quantity, BigDecimal price, BigDecimal cost) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.cost = cost;
    }
}
