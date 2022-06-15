package marcet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marcet.dto.BasketItemDTO;
import marcet.dto.ProductDTO;
import marcet.service.BasketService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*Контроллер для создания корзины*/
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    /*Добавление данных в корзину пользователя*/
    @PostMapping("/add")
    public List<BasketItemDTO> addProductToBasket(@RequestBody ProductDTO productDTO, Principal principal) {
        log.info("Product {},  Principal {}", productDTO.getTitle(), principal.getName());
        return basketService.addProductToBasket(productDTO, principal.getName());
    }

    /*Получение корзины по п имени пользователя*/
    @GetMapping("/get-basket")
    public List<BasketItemDTO> getBasket(Principal principal) {
        return basketService.getBasket(principal.getName());
    }

    /*Удаление карзины по имени пользователя*/
    @PostMapping("/del")
    public List<BasketItemDTO> delProductFromBasket(@RequestBody ProductDTO productDTO, Principal principal) {
        log.info("Product {},  Principal {}", productDTO.getTitle(), principal.getName());
        return basketService.delProductFromBasket(productDTO, principal.getName());
    }

    /*Уменьшение количества продукта в корзине на 1*/
    @PostMapping("/decriment")
    public List<BasketItemDTO> decrementProduct(@RequestBody ProductDTO productDTO, Principal principal) {
        log.info("Уменьшить количество продукта: {} ", productDTO.getTitle());
        return basketService.decrementProduct(productDTO, principal.getName());
    }
}
