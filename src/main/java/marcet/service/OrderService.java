package marcet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marcet.dto.*;
import marcet.model.*;
import marcet.repository.*;
import org.aspectj.weaver.patterns.ParserException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

/*Класс для работы с заказами*/
@Slf4j
@RequiredArgsConstructor
@Service
@Scope(scopeName = "prototype")
public class OrderService {

    private final OrderRepository orderRepository;
    private final BasketService basketService;
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final ProductService productService;
    private final OrderItemsRepository orderItemsRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    /*ФОрмирование заказа*/
    public Order createOrder(String username, Long addressId) {
        User user = userRepository.findByUsername(username).get();
        Address address = addressService.findAddressById(addressId);
        int totalQuantity = basketService.getTotalQuantity(username);
        BigDecimal totalCost = basketService.getTotalCost(username);
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setAddress(address);
        newOrder.setTotalQuantity(totalQuantity);
        newOrder.setTotalCost(totalCost);
        newOrder = orderRepository.save(newOrder);
        List<BasketItemDTO> basketlist = basketService.getBasket(username);
        for (BasketItemDTO bi: basketlist) {
            Product product = productService.findProductById(bi.getProductDTO().getId());
            int quantity = bi.getQuantity();
            BigDecimal price = bi.getProductDTO().getPrice();
            BigDecimal cost = BigDecimal.ZERO;
            cost = cost.add(basketService.calculateCost(quantity, price));
            orderItemsRepository.save(new OrderItem(newOrder, product, quantity, price, cost));
        }
        basketService.clearBasket(username);
        return newOrder;
    }

    /*Получение списка заказов по имени*/
    public List<OrderDTO> getAllByUsername (String username) {
        User user = userRepository.findByUsername(username).get();
        return orderRepository.findAllByUser(user).stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    /*Получение заказа по ID*/
    public OrderShowDTO showOrderOnNumber(Long orderNumber){
        log.info("OrderNumber - {}", orderNumber);
        Order order = orderRepository.getOne(orderNumber);
        log.info("Order {}", order.getOrderId());
        User user = order.getUser();
        log.info("User {}", user.getUsername());
        List<OrderItem> orderItemList = order.getOrderItems();
        log.info("Liset Size {}", orderItemList.size());
        List<ProductDTO> productList = getProductFromOrderItem(orderItemList);

        return new OrderShowDTO(
                userService.convertToDto(user),
                addressService.convertToDto(order.getAddress()),
                setQuantityProductDTOList(productList, orderItemList),
                orderItemList
        );
    }

    /*Получение списка продуктов из заказа*/
    private List<ProductDTO> getProductFromOrderItem(List<OrderItem> orderItemList){
        List<ProductDTO> productList = new ArrayList<>();
        for(OrderItem o : orderItemList){
            productList.add(productService.convertToDto(o.getProduct()));
        }
        return productList;
    }

    public OrderItem convertyToEntity(OrderItemDTO orderItemDTO) throws ParserException {
        return modelMapper.map(orderItemDTO, OrderItem.class);
    }

    /*Установить количество одних и техже продуктов*/
    private List<ProductDTO> setQuantityProductDTOList(List<ProductDTO> productList, List<OrderItem> orderItemList){
        for (int i = 0; i < orderItemList.size(); i++){
            productList.get(i).setQuantity(orderItemList.get(i).getQuantity());
        }
        return productList;
    }
}
