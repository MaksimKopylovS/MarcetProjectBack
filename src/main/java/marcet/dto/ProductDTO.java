package marcet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marcet.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private int quantity = 1;
    private String shortDescription;
    private String fullDescription;
    private List<MultipartFile> multipartFiles;
    private String phoroUrl;

    public ProductDTO(Product product) {
        this.id = product.getProductId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.shortDescription = product.getShortDescription();
        this.fullDescription = product.getFullDescription();
        this.phoroUrl = product.getPhoroUrl();
    }
}
