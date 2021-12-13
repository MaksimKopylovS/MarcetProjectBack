package marcet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import marcet.model.OrderItem;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderShowDTO {
    private UserDTO userDTO;
    private AddressDTO addressDTO;
    private List<ProductDTO> productDTO;
    private List<OrderItem> orderItem;
}
