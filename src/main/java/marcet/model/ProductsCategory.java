package marcet.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "products_categories_tbl")
public class ProductsCategory {

    @Id
    @Column(name = "id")
    private Long productId;

    @Column(name = "category_id")
    private Long categoryId;
}
