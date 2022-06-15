package marcet.specifications;

import marcet.model.Category;
import marcet.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
/*Клас спецификации, для сортировки продуктов по идентификаторам*/
public class ProductSpecifications {

    /*Сортировка по ID продукта*/
    private static Specification<Product> idFilter(Long lon) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("productId"), lon);
    }

    /*Сортировка по ID категории*/
    private static Specification<Product> FilterByCategory(Long id) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product, Category> productCategoryJoin = root.join("categories");
            return criteriaBuilder.equal(productCategoryJoin.get("categoryId"), id);
        };
    }

    /*Сортирофка по минимальной цене*/
    private static Specification<Product> priceGreaterOrEqualsThan(BigDecimal minPrice) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }
    /*Сортировка по максимальной цене*/
    private static Specification<Product> priceLesserOrEqualsThan(BigDecimal maxPrice) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    /*Сортировка по названию*/
    private static Specification<Product> titleLike(String titlePart) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%%s%%", titlePart));
    }

    /*Сборка спецификации продуктов по заданным сортировкам*/
    public static Specification<Product> build(MultiValueMap<String, String> params) {
        Specification<Product> spec = Specification.where(null);

        if (params.containsKey("id") && !params.getFirst("id").isBlank()) {
            System.out.println("Приход B  params.containsKey(id)");
            spec = spec.and(ProductSpecifications.idFilter(Long.parseLong(params.getFirst("id"))));
        }

        if (params.containsKey("id_category") && !params.getFirst("id_category").isBlank()) {
            spec = spec.and(ProductSpecifications.FilterByCategory(Long.parseLong(params.getFirst("id_category"))));
        }

        if (params.containsKey("min_cost") && !params.getFirst("min_cost").isBlank()) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(BigDecimal.valueOf(Long.parseLong(params.getFirst("min_cost")))));
        }

        if (params.containsKey("max_cost") && !params.getFirst("max_cost").isBlank()) {
            spec = spec.and(ProductSpecifications.priceLesserOrEqualsThan(BigDecimal.valueOf(Long.parseLong(params.getFirst("max_cost")))));
        }

        if (params.containsKey("title") && !params.getFirst("title").isBlank()) {
            spec = spec.and(ProductSpecifications.titleLike(params.getFirst("title")));
        }
        return spec;
    }
}

