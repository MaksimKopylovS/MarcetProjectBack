package marcet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marcet.dto.BasketItemDTO;
import marcet.dto.ProductDTO;
import marcet.service.BasketService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/add")
    public List<BasketItemDTO> addProductToBasket(@RequestBody ProductDTO productDTO, Principal principal) {
        log.info("Product {},  Principal {}", productDTO.getTitle(), principal.getName());
        return basketService.addProductToBasket(productDTO, principal.getName());
    }

    @GetMapping("/get-basket")
    public List<BasketItemDTO> getBasket(Principal principal) {
        return basketService.getBasket(principal.getName());
    }

    @PostMapping("/del")
    public List<BasketItemDTO> delProductFromBasket(@RequestBody ProductDTO productDTO, Principal principal) {
        log.info("Product {},  Principal {}", productDTO.getTitle(), principal.getName());
        return basketService.delProductFromBasket(productDTO, principal.getName());
    }

    @PostMapping("/decriment")
    public List<BasketItemDTO> decrementProduct(@RequestBody ProductDTO productDTO, Principal principal) {
        log.info("Уменьшить количество продукта: {} ", productDTO.getTitle());
        return basketService.decrementProduct(productDTO, principal.getName());
    }
}
