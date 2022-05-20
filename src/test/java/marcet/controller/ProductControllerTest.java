package marcet.controller;

import marcet.dto.ProductDTO;
import marcet.service.ProductService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = ProductController.class)
@RunWith(SpringRunner.class)
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    private ProductDTO productDTO = new ProductDTO();
    private List<ProductDTO> productDTOList = new ArrayList<>();

    public ProductControllerTest() {
        productDTO.setId(1L);
        productDTO.setTitle("Product");
        productDTO.setQuantity(0);
        productDTO.setFullDescription("FullDescription");
        productDTO.setShortDescription("ShortDescription");
        productDTOList.add(productDTO);
    }

    @Test
    public void getProductTests() {
        Mockito
                .doReturn(productDTOList)
                .when(productService)
                .getProduct();

        Assertions.assertEquals("Product", productController.getProducts().get(0).getTitle());
    }

    @Test
    public void createNewProductTest() {
        Mockito
                .doReturn(productDTO)
                .when(productService)
                .saveNewProduct(productDTO);
        Assertions.assertEquals("Product", productController.createNewProduct(productDTO).getTitle());
    }

    @Test
    public void editProductsTest() {
        Mockito
                .doReturn(productDTO)
                .when(productService)
                .updateProduct(productDTO);
        Assertions.assertEquals("Product", productController.editProducts(productDTO).getTitle());
    }

    @Test
    public void deleteProductByIdTest() {
        Mockito
                .doNothing()
                .when(productService)
                .deleteProductById(1L);

        productController.deleteProductById(1L);
    }

}