package marcet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import marcet.model.BasketItem;

@Data
@NoArgsConstructor
public class BasketItemDTO {
    private Long basketItemId;
    private Long userId;
    private ProductDTO productDTO;
    private int quantity;

    public BasketItemDTO(BasketItem basketItem) {
        this.basketItemId = basketItem.getBasketItemId();
        this.userId = basketItem.getUser().getUserId();
        this.productDTO = new ProductDTO(basketItem.getProduct());
        this.quantity = basketItem.getQuantity();
    }
}
