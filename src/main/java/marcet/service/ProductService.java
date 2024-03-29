package marcet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import marcet.dto.ImageDTO;
import marcet.dto.ProductDTO;
import marcet.model.Category;
import marcet.model.Product;
import marcet.repository.CategoryRepository;
import marcet.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final String ROOT_PATH = "Image";

    public ProductDTO convertToDto(Product product){
        return modelMapper.map(product, ProductDTO.class);
    }
    public Product convertToEntity(ProductDTO productDTO){
        return modelMapper.map(productDTO, Product.class);
    }

    public ImageDTO getProductImage(Long productID) throws IOException {
        Product product = productRepository.getOne(productID);
        byte[] array = Files.readAllBytes(Paths.get(product.getPhoroUrl()));
        return new ImageDTO(product.getTitle(), "image/jpeg", array);
    }

    public List<ProductDTO> getProduct() {
        List<Product> productList = productRepository.findAll();
        Type listType = new TypeToken<List<ProductDTO>>(){}.getType();
        return modelMapper.map(productList,listType);
    }

    public Page<ProductDTO> findAllProducts(Specification<Product> spec, int page, int pageSize) {
        Page<ProductDTO> products = productRepository.findAll(spec, PageRequest.of(page - 1, pageSize)).map(ProductDTO::new);
        products.stream().forEach(productDTO -> System.out.println("Q  = " + productDTO.toString()));
        return productRepository.findAll(spec, PageRequest.of(page - 1, pageSize)).map(ProductDTO::new);
    }

    public List<ProductDTO> findAllByCategory(Long categoryId) {
        String category = categoryRepository.findById(categoryId).get().getTitle();
        List<Product> list = productRepository.findDistinctProductsByCategories_TitleContaining(category);
        Type listType = new TypeToken<List<ProductDTO>>(){}.getType();
        List<ProductDTO> productDTOList = modelMapper.map(list,listType);
        return productDTOList;
    }

    public BigDecimal getPriceProduct(int quantity, BigDecimal cost){
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal itemCost;
        itemCost = cost.multiply(new BigDecimal(quantity));
        totalCost = totalCost.add(itemCost);
        return totalCost;
    }

    public ProductDTO saveNewProduct(ProductDTO productDTO) {
        Product newProduct;
        productDTO.setId(null);
        newProduct = convertToEntity(productDTO);
        productRepository.save(newProduct);
        productDTO.setId(newProduct.getProductId());
        return productDTO;
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        product.setProductId(productDTO.getId());
        productRepository.save(product);
        return convertToDto(product);
    }

    public void deleteProductById(Long product_id) {
        productRepository.deleteById(product_id);
    }

    public ProductDTO findProductDtoById(Long product_id) {
        return convertToDto(productRepository.findById(product_id).get());
    }

    public Product findProductById(Long product_id) {
        return productRepository.findById(product_id).get();
    }

    public ProductDTO createNewProduct(List<MultipartFile> multipartFiles, List<String> listProductDTO) throws IOException {
        Product product = productRepository.save(convertToEntity(createProductDTO(listProductDTO)));
        if (!Files.exists(Paths.get(ROOT_PATH))) {
                Files.createDirectory(Paths.get(ROOT_PATH));
        }
        product.setPhoroUrl(multipartFileToFile(multipartFiles.get(0), Paths.get(ROOT_PATH), product.getProductId().toString()));
        productRepository.save(product);
        return new ProductDTO();
    }

    public String multipartFileToFile(MultipartFile multipart, Path dir, String ID) throws IOException {
        Path filepath = Paths.get(dir.toString(), ID +"_"+multipart.getOriginalFilename());
        multipart.transferTo(filepath);
        return filepath.toString();
    }

    public ProductDTO createProductDTO(List<String> listProductDTO) throws IOException {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        for (String s : listProductDTO){
            json = json + s +",";
        }
        return mapper.readValue(removeLastChar(json), ProductDTO.class);
    }

    public static String removeLastChar(String s){
        return (s == null || s.length() == 0) ? null : (s.substring(0, s.length() - 1));
    }
}
