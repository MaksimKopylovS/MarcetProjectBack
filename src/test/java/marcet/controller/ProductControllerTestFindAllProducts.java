package marcet.controller;

import marcet.dto.ProductDTO;
import marcet.specifications.ProductSpecifications;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = ProductController.class)
@RunWith(SpringRunner.class)
public class ProductControllerTestFindAllProducts {

    @MockBean
    private ProductController productControllerMock;

    private ProductDTO productDTO = new ProductDTO();
    private List<ProductDTO> productDTOList = new ArrayList<>();

    public ProductControllerTestFindAllProducts() {
        productDTO.setId(1L);
        productDTO.setTitle("Product");
        productDTO.setQuantity(0);
        productDTO.setFullDescription("FullDescription");
        productDTO.setShortDescription("ShortDescription");
        productDTOList.add(productDTO);
    }

    @Test
    public void findAllProductsTest() {

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("Hello", "World");
        Page<ProductDTO> productDTOS = new PageImpl(productDTOList);

        Mockito
                .doReturn(productDTOS)
                .when(productControllerMock)
                .findAllProducts(params, 1);

        Assertions.assertEquals("Product", productControllerMock.findAllProducts(params, 1).iterator().next().getTitle());

    }
}
