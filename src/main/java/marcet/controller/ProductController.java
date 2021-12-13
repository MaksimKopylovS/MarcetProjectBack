package marcet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marcet.dto.ImageDTO;
import marcet.dto.ProductDTO;
import marcet.model.Category;
import marcet.model.Product;
import marcet.service.ProductService;
import marcet.specifications.ProductSpecifications;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get-products")
    public List<ProductDTO> getProducts() {
        List<ProductDTO> productDTOS = productService.getProduct();
        for (ProductDTO p : productDTOS){
            log.info("{} {} {} {} {} {} ", p.getTitle(), p.getPrice(), p.getShortDescription());
        }
        System.out.println("HEllll");
        return productService.getProduct();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createNewProduct(@RequestBody ProductDTO productDTO){
        return productService.saveNewProduct(productDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO editProducts(@RequestBody ProductDTO productDTO){
        return productService.updateProduct(productDTO);
    }

    @DeleteMapping("/{product_id}")
    public void deleteProductById(@PathVariable Long product_id) {
        productService.deleteProductById(product_id);
    }

    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    public Page<ProductDTO> findAllProducts(@RequestParam MultiValueMap<String, String> params,
                                            @RequestParam(name = "page", defaultValue = "1") Integer page) {
        System.out.println(page + " Пришло в ProductController");
        Iterator<String> iteratot = params.keySet().iterator();
        log.info("params size - {}", params.size());
        while(iteratot.hasNext()) {
            String theKey = (String)iteratot.next();
            log.info("Key  {} Params {}", theKey,params.getFirst(theKey));
        }
        log.info("ПРИШЛО " + params.size());
        return productService.findAllProducts(ProductSpecifications.build(params), page, 12);
    }

    @GetMapping("/{product_id}")
    public ProductDTO findProductDtoById(@PathVariable Long product_id) {
        return productService.findProductDtoById(product_id);
    }

    @GetMapping("/getimage/{id}")
    public ImageDTO getImageById(@PathVariable Long id) throws IOException {
        return productService.getProductImage(id);
    }
}
