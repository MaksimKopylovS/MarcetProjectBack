package marcet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marcet.dto.CreateOrderDTO;
import marcet.dto.OrderDTO;
import marcet.exceptions_handling.MarketError;
import marcet.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*Контролер для формирования заказа*/
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    /*Создание заказа*/
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        log.info("Username - {}, address - {}", createOrderDTO.getUserName(), createOrderDTO.getAddressId());
        if (createOrderDTO.getUserName().equals(null)) {
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(orderService.createOrder(createOrderDTO.getUserName(), createOrderDTO.getAddressId()));
    }

    /*Отображение заказа по его ID*/
    @PostMapping("/show")
    public ResponseEntity<?> showOrderOnNumber(@RequestBody Long orderNumber) {
        log.info("OrderNumber {}", orderNumber);
        return ResponseEntity.ok(orderService.showOrderOnNumber(orderNumber));
    }

    /*Отображение всех заказов по имени пользователя*/
    @GetMapping
    public List<OrderDTO> findAllOrderByUsername(Principal principal) {
        String username = principal.getName();
        List<OrderDTO> orderList = orderService.getAllByUsername(username);
        return orderList;
    }
}