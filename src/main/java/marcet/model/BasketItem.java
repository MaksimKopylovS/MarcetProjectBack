package marcet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "basket_items_tbl")
@Data
@NoArgsConstructor
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_items_id")
    private Long basketItemId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity_fld")
    private int quantity;

    public BasketItem(User user, Product product) {
        this.user = user;
        this.product = product;
        this.quantity = 1;
    }

    public void incQuantity() {
        this.quantity++;
    }

    public void decQuantity() {
        if (quantity > 1) {
            this.quantity--;
        }
    }
}
