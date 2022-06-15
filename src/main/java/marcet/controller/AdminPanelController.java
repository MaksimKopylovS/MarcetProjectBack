package marcet.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marcet.dto.ProductDTO;
import marcet.service.ProductService;
import marcet.specifications.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/*Класс контроллер для панели администратора*/
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminPanelController {

    private final ProductService productService;

    /*Добавление нового продукта*/
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createNewProduct(@RequestBody ProductDTO productDTO) {
        log.info("создание нового продукта {} в БД ", productDTO.getTitle());
        return productService.saveNewProduct(productDTO);
    }

    /*Добавление картинки для нового продукта*/
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO saveImage(@RequestParam("files") List<MultipartFile> multipartFiles,
                                @RequestParam("product") List<String> listProductDTO) throws IOException {
        productService.createNewProduct(multipartFiles, listProductDTO);
        return new ProductDTO();
    }

    /*Редактирование продукта*/
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO editProduct(@RequestParam("files") List<MultipartFile> multipartFiles,
                                  @RequestParam("product") List<String> listProductDTO) throws IOException {
        productService.createNewProduct(multipartFiles, listProductDTO);
        return new ProductDTO();
    }

    /*Удаление продукта по ID*/
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        log.info("Удаление продукта ID - {} ", id);
        productService.deleteProductById(id);
    }

    /*Показать все продукты*/
    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    public Page<ProductDTO> findAllProductsAdm(@RequestParam MultiValueMap<String, String> params,
                                               @RequestParam(name = "page", defaultValue = "1") Integer page) {
        return productService.findAllProducts(ProductSpecifications.build(params), page, 12);
    }
}
